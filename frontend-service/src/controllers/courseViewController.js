import { getCourseById } from '../models/courseModel.js'
import { getEnrollmentsByStudentId, enrollInCourse, dropCourse } from '../models/enrollModel.js'
import { getUserById } from '../models/authModel.js'
import { logout } from '../models/authModel.js'

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
            await renderEnrollmentButton()
        }
    } catch (err) {
        alert('Failed to load course: ' + err.message)
        window.location.href = '/public/courses.html'
    }
})

function controlNavVisibility() {
    const user = JSON.parse(localStorage.getItem('user'))
    if (!user) return

    if (user.role === 'faculty') {
        document.getElementById('nav-enroll')?.remove()
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

async function renderEnrollmentButton() {
    try {
        const enrollments = await getEnrollmentsByStudentId(user.token, user.id)

        const enrollment = enrollments.find((e) => e.courseId === courseId)
        const container = document.getElementById('action-button-container')
        container.innerHTML = ''

        const btn = document.createElement('button')
        btn.className = 'enroll-btn'

        if (enrollment) {
            btn.textContent = 'Drop Course'
            btn.onclick = () => drop(enrollment.id)
        } else {
            btn.textContent = 'Enroll'
            btn.onclick = () => enroll(courseId)
        }

        container.appendChild(btn)
    } catch (err) {
        console.error('Enrollment check error:', err)
    }
}

async function enroll(courseId) {
    try {
        const requestData = {
            courseId,
            userId: user.id,
        }
        const res = await enrollInCourse(requestData, user.token)

        if (!res) throw new Error('Enrollment failed')

        alert('Enrolled successfully!')
        window.location.reload()
    } catch (err) {
        alert('Enroll failed: ' + err.message)
    }
}

async function drop(enrollmentId) {
    try {
        const res = await dropCourse(enrollmentId, user.token)

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
