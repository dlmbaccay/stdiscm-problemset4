import { saveSession } from '../utils/session.js'

export async function login(requestData) {
    const loginUrl = 'http://localhost:8081/auth/login'
    // Fake API call simulation — replace with fetch
    fetch(loginUrl, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(requestData),
    })
        .then(async (response) => {
            if (!response.ok) throw new Error('Invalid email or password')
            const data = await response.json()
            saveSession(data)
            window.location.href = '/public/courses.html'
        })
        .catch((error) => {
            console.error('Login failed:', error)
            errorContainer.textContent = error.message || 'Invalid credentials.'
            errorContainer.style.display = 'block'
        })
}

export async function register(requestData) {
    await fetch('http://localhost:8081/auth/register', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(requestData),
    })
        .then(async (response) => {
            if (!response.ok) {
                throw new Error('Registration failed. Please check your input.')
            }
            const data = await response.json()
            saveSession(data)
            window.location.href = '/public/courses.html'
        })
        .catch((error) => {
            console.error('Registration failed:', error)
            errorContainer.textContent = error.message || 'Something went wrong.'
            errorContainer.style.display = 'block'
        })
}

export async function getUserById(userId, token) {
    const res = await fetch(`http://localhost:8081/auth/${userId}`, {
        method: 'GET',
        headers: {
            Authorization: `Bearer ${token}`,
        },
    })

    if (!res.ok) {
        const msg = await res.text()
        throw new Error(msg)
    }

    return await res.json()
}

export async function logout() {
    localStorage.removeItem('user')
    window.location.href = '/public/login.html'
}
