// Function to add auth headers to all fetch requests
const originalFetch = window.fetch
window.fetch = async function (url, options = {}) {
    const token = localStorage.getItem('authToken')

    if (token) {
        options.headers = {
            ...options.headers,
            Authorization: `Bearer ${token}`,
        }
    }

    try {
        const response = await originalFetch(url, options)

        // Handle 401/403 responses
        if (response.status === 401 || response.status === 403) {
            localStorage.removeItem('authToken')
            window.location.href = '/login'
            throw new Error('Session expired. Please login again.')
        }

        return response
    } catch (error) {
        throw error
    }
}

// Function to check if user is authenticated
function isAuthenticated() {
    return localStorage.getItem('authToken') !== null
}

// Function to redirect to login if not authenticated
function requireAuth() {
    if (!isAuthenticated()) {
        window.location.href = '/login'
    }
}

// Add auth check on page load for protected pages
document.addEventListener('DOMContentLoaded', () => {
    // Check if we're on a protected page (not login or register)
    if (!window.location.pathname.includes('/login') && !window.location.pathname.includes('/register')) {
        requireAuth()
    }
})
