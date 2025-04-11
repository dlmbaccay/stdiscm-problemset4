import { getAllCourses, createCourse } from '../models/courseModel.js'
import { getEnrollmentsByStudentId, enrollInCourse, dropCourse } from '../models/enrollModel.js'
import { getGradesByStudentId } from '../models/gradeModel.js'
import { logout } from '../models/authModel.js'

document.addEventListener('DOMContentLoaded', () => {
    displayUserInfo()
    controlNavVisibility()
    fetchAndDisplayCoursesWithGrades()
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

async function fetchAndDisplayCoursesWithGrades() {
    const user = JSON.parse(localStorage.getItem('user'))
    if (!user) return (window.location.href = '/public/login.html')

    try {
        const courses = await getAllCourses(user.token)
        const enrollments = user.role === 'student' ? await getEnrollmentsByStudentId(user.token, user.id) : []
        const grades = await getGradesByStudentId(user.id, user.token)
        const list = document.getElementById('grades-list')
        list.innerHTML = ''

        let enrolledCourses = []
        enrollments.forEach(async (enrollment) => {
            const course = courses.find((c) => c.id === enrollment.courseId)
            enrolledCourses.push(course)
        })

        const summary = document.getElementById('summary')
        summary.innerHTML = `
            <p><strong>GPA:</strong> ${grades.reduce((sum, grade) => sum + grade.grade, 0) / grades.length || 'N/A'}</p>
            <p><strong>Courses Enrolled:</strong> ${enrolledCourses.length}</p>
        `

        enrolledCourses.forEach((course) => {
            const grade = grades.find((g) => g.courseId === course.id)
            const div = document.createElement('div')
            div.className = 'course'

            const actionContainer = document.createElement('div')
            actionContainer.className = 'enroll-button-container'

            // Build main course info
            const courseInfo = document.createElement('div')
            courseInfo.className = 'course-info'

            if (grade) {
                courseInfo.innerHTML = `
                <strong>${course.courseCode}: ${course.courseName}</strong><br>
                Enrolled: ${course.currentEnrollees}/${course.maxEnrollees}<br>
                Grade: ${grade?.grade ? grade.grade : 'N/A'}
                Updated: ${grade?.updatedAt ? grade.updatedAt.toLocaleDateString() : 'N/A'}
            `
            } else {
                courseInfo.innerHTML = `
                <strong>${course.courseCode}: ${course.courseName}</strong><br>
                Enrolled: ${course.currentEnrollees}/${course.maxEnrollees}<br>
                Grade: N/A
            `
            }

            // Always show the View button
            const viewBtn = document.createElement('button')
            viewBtn.className = 'enroll-btn'
            viewBtn.textContent = 'View Course'
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
