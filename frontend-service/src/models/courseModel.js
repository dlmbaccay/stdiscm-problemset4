const API_BASE = 'http://localhost:8081'

export async function getAllCourses(token) {
    const res = await fetch(`${API_BASE}/courses`, {
        method: 'GET',
        headers: {
            Authorization: `Bearer ${token}`,
        },
    })
    if (!res.ok) throw new Error('Failed to fetch courses')
    return res.json()
}

export async function getCoursesByFacultyId(facultyId, token) {
    const res = await fetch(`${API_BASE}/courses/faculty/${facultyId}`, {
        method: 'GET',
        headers: {
            Authorization: `Bearer ${token}`,
        },
    })
    if (!res.ok) throw new Error('Failed to fetch courses')
    return await res.json()
}

export async function getCoursesByStudentId(studentId, token) {
    const res = await fetch(`${API_BASE}/courses/student/${studentId}`, {
        method: 'GET',
        headers: {
            Authorization: `Bearer ${token}`,
        },
    })
    if (!res.ok) throw new Error('Failed to fetch courses')
    return await res.json()
}

export async function createCourse(course, token) {
    const res = await fetch(`${API_BASE}/courses/create`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(course),
    })
    if (!res.ok) {
        const msg = await res.text()
        throw new Error(msg)
    }
    return true
}

export async function getCourseById(courseId, token) {
    const res = await fetch(`http://localhost:8081/courses/${courseId}`, {
        method: 'GET',
        headers: {
            Authorization: `Bearer ${token}`,
        },
    })

    if (!res.ok) {
        const msg = await res.text()
        throw new Error(msg)
    }

    return res.json()
}

export async function updateCourse(courseId, course, token) {
    const res = await fetch(`${API_BASE}/courses/${courseId}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(course),
    })
    if (!res.ok) {
        const msg = await res.text()
        throw new Error(msg)
    }
    return await res.json()
}

export async function deleteCourse(courseId, token) {
    const res = await fetch(`${API_BASE}/courses/${courseId}`, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json',
            Authorization: `Bearer ${token}`,
        },
    })
    if (!res.ok) {
        const msg = await res.text()
        throw new Error(msg)
    }
    return true
}
