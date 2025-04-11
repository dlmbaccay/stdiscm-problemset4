import { register } from '../models/authModel.js'

document.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById('registerForm')
    form.addEventListener('submit', submitRegisterForm)
})

export function submitRegisterForm(event) {
    event.preventDefault()

    const firstName = document.getElementById('firstName').value.trim()
    const lastName = document.getElementById('lastName').value.trim()
    const email = document.getElementById('email').value.trim()
    const password = document.getElementById('password').value.trim()
    const role = document.getElementById('role').value

    const errorContainer = document.getElementById('error-container')
    errorContainer.style.display = 'none'

    const requestData = {
        firstName,
        lastName,
        email,
        password,
        role,
    }

    register(requestData)
}
