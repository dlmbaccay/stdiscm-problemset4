import { getAllCourses, createCourse } from '../models/courseModel.js'
import { getEnrollmentsByStudentId, enrollInCourse, dropCourse } from '../models/enrollModel.js'
import { logout } from '../models/authModel.js'

document.addEventListener('DOMContentLoaded', () => {
    displayUserInfo()
    controlNavVisibility()
    checkUserRoleAndAddButton()
    fetchAndDisplayCourses()
})

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

function controlNavVisibility() {
    const user = JSON.parse(localStorage.getItem('user'))
    if (!user) return

    if (user.role === 'faculty') {
        document.getElementById('nav-enroll')?.remove()
        document.getElementById('nav-my-grades')?.remove()
    }
}

function checkUserRoleAndAddButton() {
    const user = localStorage.getItem('user')
    if (user) {
        const userData = JSON.parse(user)
        if (userData.role === 'faculty') {
            const section = document.getElementById('add-course-section')
            const btn = document.createElement('button')
            btn.textContent = 'Add Course'
            btn.className = 'add-course-btn'
            btn.onclick = () => {
                document.getElementById('addCourseModal').style.display = 'block'
            }
            section.appendChild(btn)
        }
    }
}

async function fetchAndDisplayCourses() {
    const user = JSON.parse(localStorage.getItem('user'))
    if (!user) return (window.location.href = '/public/login.html')

    try {
        const courses = await getAllCourses(user.token)
        const enrollments = user.role === 'student' ? await getEnrollmentsByStudentId(user.token, user.id) : []

        const list = document.getElementById('courses-list')
        list.innerHTML = ''

        let enrolledCourses = []
        enrollments.forEach((enrollment) => {
            const course = courses.find((c) => c.id === enrollment.courseId)
            enrolledCourses.push(course)
        })

        enrolledCourses.forEach((course) => {
            const div = document.createElement('div')
            div.className = 'course'

            // Build main course info
            const courseInfo = document.createElement('div')
            courseInfo.className = 'course-info'
            courseInfo.innerHTML = `
        <strong>${course.courseCode}: ${course.courseName}</strong><br>
        Enrolled: ${course.currentEnrollees}/${course.maxEnrollees}
      `

            // Action button container
            const actionContainer = document.createElement('div')
            actionContainer.className = 'enroll-button-container'

            if (user.role === 'student') {
                const isEnrolled = enrollments.some((enrollment) => enrollment.courseId === course.id)
                const actionBtn = document.createElement('button')
                actionBtn.className = 'enroll-btn'

                if (isEnrolled) {
                    const enrollment = enrollments.find((e) => e.courseId === course.id)
                    actionBtn.textContent = 'Drop'
                    actionBtn.onclick = async () => {
                        try {
                            await dropCourse(enrollment.id, user.token)
                            alert('Course dropped!')
                            fetchAndDisplayCourses()
                        } catch (err) {
                            alert(`Failed to drop course: ${err.message}`)
                        }
                    }
                } else {
                    actionBtn.textContent = 'Enroll'
                    actionBtn.onclick = async () => {
                        try {
                            await enrollInCourse({ courseId: course.id, studentId: user.id }, user.token)
                            alert('Enrolled!')
                            fetchAndDisplayCourses()
                        } catch (err) {
                            alert(`Failed to enroll: ${err.message}`)
                        }
                    }
                }

                actionContainer.appendChild(actionBtn)
            }

            // Always show the View button
            const viewBtn = document.createElement('button')
            viewBtn.className = 'enroll-btn'
            viewBtn.textContent = 'View'
            viewBtn.onclick = () => viewCourse(course.id)
            actionContainer.appendChild(viewBtn)

            // Append info + action to course div
            div.appendChild(courseInfo)
            div.appendChild(actionContainer)
            list.appendChild(div)
        })
    } catch (err) {
        console.error('Course loading failed:', err)
    }
}

window.createCourse = async function () {
    const user = JSON.parse(localStorage.getItem('user'))
    if (!user) return (window.location.href = '/public/login.html')

    const courseCode = document.getElementById('courseCode').value
    const courseName = document.getElementById('courseName').value
    const maxEnrollees = document.getElementById('maxEnrollees').value

    if (!courseCode || !courseName || !maxEnrollees) {
        return alert('Please fill all fields.')
    }

    const newCourse = {
        courseCode,
        courseName,
        maxEnrollees: parseInt(maxEnrollees),
        facultyId: user.id,
    }

    try {
        await createCourse(newCourse, user.token)
        alert('Course created!')
        document.getElementById('addCourseModal').style.display = 'none'
        fetchAndDisplayCourses()
    } catch (err) {
        alert(`Failed to create course: ${err.message}`)
    }
}

window.viewCourse = function (courseId) {
    window.location.href = `/public/course-view.html?id=${courseId}`
}

window.logout = function () {
    logout()
}
