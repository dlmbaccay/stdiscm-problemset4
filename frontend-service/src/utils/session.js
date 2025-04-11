export function saveSession(data) {
    localStorage.setItem('user', JSON.stringify(data))
}

export function getSession() {
    return JSON.parse(localStorage.getItem('user'))
}

export function clearSession() {
    localStorage.removeItem('user')
}
