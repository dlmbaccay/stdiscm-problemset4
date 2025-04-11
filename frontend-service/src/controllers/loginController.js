import { login } from '../models/authModel.js'

document.addEventListener('DOMContentLoaded', () => {
    console.log(JSON.parse(localStorage.getItem('user')))
    const form = document.getElementById('loginForm')
    form.addEventListener('submit', submitLoginForm)
})

function submitLoginForm(event) {
    event.preventDefault()

    const email = document.getElementById('email').value.trim()
    const password = document.getElementById('password').value.trim()
    const errorContainer = document.getElementById('error-container')
    errorContainer.style.display = 'none'

    const requestData = { email, password }

    login(requestData)
}
