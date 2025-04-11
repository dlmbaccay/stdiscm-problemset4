export async function fetchAPI(url, options = {}) {
    const token = localStorage.getItem('token')
    const headers = {
        'Content-Type': 'application/json',
        ...(token && { Authorization: `Bearer ${token}` }),
    }

    const response = await fetch(url, {
        ...options,
        headers,
    })

    if (response.status === 401) {
        logout()
    }

    return response.json()
}
