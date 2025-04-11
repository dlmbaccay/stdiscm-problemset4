const API_BASE = 'http://localhost:8081'

export async function getGradesByStudentId(studentId, token) {
    const res = await fetch(`${API_BASE}/grades/all/student/${studentId}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            Authorization: `Bearer ${token}`,
        },
    })
    if (!res.ok) {
        const msg = await res.text()
        throw new Error(msg)
    }
    return await res.json()
}

export async function getGradesByCourseId(courseId, token) {
    const res = await fetch(`${API_BASE}/grades/all/course/${courseId}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            Authorization: `Bearer ${token}`,
        },
    })
    if (!res.ok) {
        const msg = await res.text()
        throw new Error(msg)
    }
    return await res.json()
}

export async function getGradesByCourseIdAndStudentId(courseId, studentId, token) {
    const res = await fetch(`${API_BASE}/grades/${courseId}/${studentId}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            Authorization: `Bearer ${token}`,
        },
    })
    if (!res.ok) {
        const msg = await res.text()
        throw new Error(msg)
    }
    return await res.json()
}

export async function createGrade(grade, token) {
    const res = await fetch(`${API_BASE}/grades`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(grade),
    })
    if (!res.ok) {
        const msg = await res.text()
        throw new Error(msg)
    }
    return await res.json()
}

export async function updateGrade(gradeId, grade, token) {
    const res = await fetch(`${API_BASE}/grades/${gradeId}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(grade),
    })
    if (!res.ok) {
        const msg = await res.text()
        throw new Error(msg)
    }
    return await res.json()
}

export async function deleteGrade(gradeId, token) {
    const res = await fetch(`${API_BASE}/grades/${gradeId}`, {
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
