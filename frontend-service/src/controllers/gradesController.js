import { getAllCourses, createCourse } from '../models/courseModel.js'
import { getEnrollmentsByStudentId, enrollInCourse, dropCourse } from '../models/enrollModel.js'
import { getGradesByStudentId } from '../models/gradeModel.js'
import { logout, refreshToken } from '../models/authModel.js'

document.addEventListener('DOMContentLoaded', () => {
    displayUserInfo()
    controlNavVisibility()
    fetchAndDisplayCoursesWithGrades()
    refreshToken()
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
        document.getElementById('nav-my-grades')?.remove()
    }
}

async function fetchAndDisplayCoursesWithGrades() {
    const user = JSON.parse(localStorage.getItem('user'))
    if (!user) return (window.location.href = '/public/login.html')

    try {
        // const courses = await getAllCourses(user.token)
        const courseGrades = await getGradesByStudentId(user.id, user.token)
        const list = document.getElementById('grades-list')
        list.innerHTML = ''

        const summary = document.getElementById('summary')
        summary.innerHTML = `
            <p><strong>GPA:</strong> ${
                courseGrades.reduce((sum, grade) => sum + grade.grade, 0) / courseGrades.length || 'N/A'
            }</p>
            <p><strong>Courses Enrolled:</strong> ${courseGrades.length}</p>
        `

        courseGrades.forEach((courseGrade) => {
            // const grade = grades.find((g) => g.courseId === course.id)
            const div = document.createElement('div')
            div.className = 'course'

            const actionContainer = document.createElement('div')
            actionContainer.className = 'enroll-button-container'

            // Build main course info
            const courseInfo = document.createElement('div')
            courseInfo.className = 'course-info'

            if (courseGrade) {
                courseInfo.innerHTML = `
                <strong>${courseGrade.courseCode}: ${courseGrade.courseName}</strong><br>
                Enrolled: ${courseGrade.currentEnrollees}/${courseGrade.maxEnrollees}<br>
                Grade: ${courseGrade?.grade ? courseGrade.grade : 'N/A'}
            `
            } else {
                courseInfo.innerHTML = `
                <strong>${courseGrade.courseCode}: ${courseGrade.courseName}</strong><br>
                Enrolled: ${courseGrade.currentEnrollees}/${courseGrade.maxEnrollees}<br>
                Grade: N/A
            `
            }

            // Always show the View button
            const viewBtn = document.createElement('button')
            viewBtn.className = 'enroll-btn'
            viewBtn.textContent = 'View Course'
            viewBtn.onclick = () => viewCourse(courseGrade.courseId)
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
