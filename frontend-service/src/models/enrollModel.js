const API_BASE = 'http://localhost:8081'

export async function enrollInCourse(requestData, token) {
    const res = await fetch(`${API_BASE}/enrollment/enroll`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(requestData),
    })
    if (!res.ok) {
        const msg = await res.text()
        throw new Error(msg)
    }
    return true
}

export async function dropCourse(enrollmentId, token) {
    const res = await fetch(`http://localhost:8081/enrollment/${enrollmentId}`, {
        method: 'DELETE',
        headers: {
            Authorization: `Bearer ${token}`,
        },
    })

    if (!res.ok) {
        const msg = await res.text()
        throw new Error(msg)
    }

    return true
}

export async function getEnrollmentsByStudentId(token, studentId) {
    const res = await fetch(`${API_BASE}/enrollment/user/${studentId}`, {
        method: 'GET',
        headers: {
            Authorization: `Bearer ${token}`,
        },
    })

    if (!res.ok) throw new Error('Failed to fetch enrollments')
    const enrollments = await res.json()

    return enrollments
}

export async function getEnrollmentsByCourseId(token, courseId) {
    const res = await fetch(`${API_BASE}/enrollment/course/${courseId}`, {
        method: 'GET',
        headers: {
            Authorization: `Bearer ${token}`,
        },
    })
    if (!res.ok) throw new Error('Failed to fetch enrollments')
    const enrollments = await res.json()
    return enrollments
}
