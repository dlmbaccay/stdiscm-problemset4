import { getCourseById } from '../models/courseModel.js'
import {
    getEnrollmentsByStudentId,
    enrollInCourse,
    dropCourse,
    getEnrollmentsByCourseId,
} from '../models/enrollModel.js'
import { getUserById } from '../models/authModel.js'
import { createGrade, updateGrade, getGradesByCourseIdAndStudentId, getGradesByCourseId } from '../models/gradeModel.js'
import { logout, refreshToken } from '../models/authModel.js'
import { getUserNotifications, createNotification } from '../models/notificationModel.js'

const courseId = new URLSearchParams(window.location.search).get('id')
const user = JSON.parse(localStorage.getItem('user'))

if (!user) {
    window.location.href = '/login.html'
}

document.addEventListener('DOMContentLoaded', async () => {
    displayUserInfo()
    controlNavVisibility()
    if (!courseId) {
        alert('No course ID provided.')
        return (window.location.href = '/public/courses.html')
    }

    try {
        const course = await getCourseById(courseId, user.token)
        const faculty = await getUserById(course.facultyId, user.token)
        document.getElementById('course-code').textContent = course.courseCode
        document.getElementById('course-name').textContent = course.courseName
        document.getElementById('max-enrollees').textContent = course.maxEnrollees
        document.getElementById('current-enrollees').textContent = course.currentEnrollees
        document.getElementById('faculty-name').textContent = faculty.firstName + ' ' + faculty.lastName // Or replace with actual name if available

        // Show enroll/drop button if student
        if (user.role === 'student') {
            await renderEnrollmentButton(course)
        }
        if (user.role === 'faculty' && course.facultyId === user.id) {
            await renderFacultyGradePanel(course)
        }
    } catch (err) {
        alert('Failed to load course: ' + err.message)
        window.location.href = '/public/courses.html'
    }

    refreshToken()
})

function controlNavVisibility() {
    const user = JSON.parse(localStorage.getItem('user'))
    if (!user) return

    if (user.role === 'faculty') {
        document.getElementById('nav-my-grades')?.remove()
    }
}

function displayUserInfo() {
    const user = localStorage.getItem('user')
    if (user) {
        const userData = JSON.parse(user)
        document.getElementById('user-name').textContent = `${userData.firstName} ${userData.lastName}`
        document.getElementById('user-role').textContent = userData.role
        document.getElementById('user-email').textContent = userData.email
    } else {
        document.getElementById('user-sidebar').style.display = 'none'
    }
}

async function renderFacultyGradePanel(course) {
    const gradesSection = document.getElementById('grades-section')
    gradesSection.innerHTML = '<h2>Manage Student Grades</h2>'

    try {
        const grades = await getGradesByCourseId(course.id, user.token)
        if (!grades.length) {
            gradesSection.innerHTML += '<p>No students enrolled.</p>'
            return
        }

        const table = document.createElement('table')
        table.style.width = '100%'
        table.style.borderCollapse = 'collapse'
        table.innerHTML = `
            <thead>
                <tr>
                    <th style="text-align:left; padding: 8px; border-bottom: 1px solid #ccc;">Student ID</th>
                    <th style="text-align:left; padding: 8px; border-bottom: 1px solid #ccc;">Student</th>
                    <th style="text-align:left; padding: 8px; border-bottom: 1px solid #ccc;">Grade</th>
                    <th style="padding: 8px; border-bottom: 1px solid #ccc;"></th>
                </tr>
            </thead>
            <tbody>
                ${grades
                    .map(
                        (e) => `
                    <tr data-enrollment-id="${e.id}">
                        <td style="padding: 8px;" data-student-id="${e.studentId}">${e.studentId}</td>
                        <td style="padding: 8px;" data-student-id="${e.studentId}">${e.firstName} ${e.lastName}</td>
                        <td style="padding: 8px;">
                            <input type="text" value="${e.grade ?? ''}" style="width: 100px;" />
                        </td>
                        <td style="padding: 8px;">
                            <button class="enroll-btn" data-grade-id="${e.id}">Save</button>
                        </td>
                    </tr>
                `,
                    )
                    .join('')}
            </tbody>
        `

        gradesSection.appendChild(table)

        // Add event listeners to buttons
        const buttons = gradesSection.querySelectorAll('.enroll-btn')
        buttons.forEach((button) => {
            button.addEventListener('click', (event) => {
                const row = event.target.closest('tr')
                const gradeId = row.getAttribute('data-enrollment-id')
                const studentId = row.querySelector('td[data-student-id]').textContent
                const gradeInput = row.querySelector('input').value
                const gradeData = {
                    grade: gradeInput.trim(),
                }
                saveGrade(course, studentId, gradeId, gradeData, user.token)
            })
        })
    } catch (err) {
        gradesSection.innerHTML += `<p>Error loading students: ${err.message}</p>`
    }
}

async function renderEnrollmentButton(course) {
    try {
        const enrollments = await getEnrollmentsByStudentId(user.id, user.token)

        const enrollment = enrollments.find((e) => e.courseId === course.id)
        const container = document.getElementById('action-button-container')
        container.innerHTML = ''

        const btn = document.createElement('button')
        btn.className = 'enroll-btn'

        if (enrollment) {
            btn.textContent = 'Drop Course'
            btn.onclick = () => drop(enrollment, course)
        } else {
            btn.textContent = 'Enroll'
            btn.onclick = () => enroll(course)
        }

        container.appendChild(btn)
    } catch (err) {
        console.error('Enrollment check error:', err)
    }
}

async function enroll(course) {
    try {
        const requestData = {
            courseId: course.id,
            userId: user.id,
        }
        const res = await enrollInCourse(requestData, user.token)
        const notificationData = {
            type: 'ENROLL',
            message: `${user.firstName} ${user.lastName} has enrolled in ${course.courseName}`,
            recipientId: course.facultyId,
            actorId: user.id,
            read: false,
        }
        await createNotification(notificationData, user.token)
        if (!res) throw new Error('Enrollment failed')

        alert('Enrolled successfully!')
        window.location.reload()
    } catch (err) {
        alert('Enroll failed: ' + err.message)
    }
}

async function saveGrade(course, studentId, gradeId, gradeData, token) {
    try {
        if (gradeData.grade < 0 || gradeData.grade > 4) {
            throw new Error('Grade must be between 0 and 4')
        }
        const res = await updateGrade(gradeId, gradeData, token)
        const notificationData = {
            type: 'GRADE',
            message: `${user.firstName} ${user.lastName} has updated your grade for ${course.courseName}`,
            recipientId: studentId,
            actorId: user.id,
            read: false,
        }
        await createNotification(notificationData, user.token)

        if (!res) throw new Error('Failed to update grade')
        alert('Grade updated successfully!')
    } catch (err) {
        alert('Failed to update grade: ' + err.message)
    }
}

async function drop(enrollment, course) {
    try {
        const res = await dropCourse(enrollment.id, user.token)
        const notificationData = {
            type: 'DROP',
            message: `${user.firstName} ${user.lastName} has dropped ${course.courseName}`,
            recipientId: course.facultyId,
            actorId: user.id,
            read: false,
        }
        await createNotification(notificationData, user.token)
        if (!res) throw new Error('Drop failed')
        alert('Dropped successfully!')
        window.location.reload()
    } catch (err) {
        alert('Drop failed: ' + err.message)
    }
}

window.logout = function () {
    logout()
}

window.openNotificationModal = async function () {
    const modal = document.getElementById('notificationModal')
    const container = document.getElementById('notification-list')
    container.innerHTML = '<p>Loading notifications...</p>'
    modal.style.display = 'block'

    const user = JSON.parse(localStorage.getItem('user'))
    if (!user) return (container.innerHTML = '<p>You must be logged in.</p>')

    try {
        const notifications = await getUserNotifications(user.id, user.token)

        if (notifications.length === 0) {
            container.innerHTML = '<p>No notifications yet.</p>'
            return
        }

        container.innerHTML = notifications
            .map(
                (n) => `
            <div style="border-bottom: 1px solid #ccc; padding: 0.5rem 0;">
                <p><strong>${n.type.replace('_', ' ')}</strong> — ${n.message}</p>
                <small>${n.createdAt}</small>
            </div>
        `,
            )
            .join('')
    } catch (err) {
        console.error('Failed to fetch notifications:', err)
        container.innerHTML = '<p>Error loading notifications.</p>'
    }
}
