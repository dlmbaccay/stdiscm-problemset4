export default function registerView() {
    const app = document.getElementById('app')
    app.innerHTML = `
      <div class="form-container">
        <h2>Register at Garynation University</h2>
        <form id="register-form">
          <input type="text" placeholder="Full Name" name="name" required />
          <input type="email" placeholder="Email" name="email" required />
          <input type="password" placeholder="Password" name="password" required />
          <button type="submit">Register</button>
        </form>
      </div>
    `

    document.getElementById('register-form').addEventListener('submit', async (e) => {
        e.preventDefault()
        const name = e.target.name.value
        const email = e.target.email.value
        const password = e.target.password.value

        // Simulated register (replace with actual fetch call)
        if (name && email && password) {
            localStorage.setItem('token', 'fake-token')
            alert('Registration successful!')
            location.hash = '/courses'
        } else {
            alert('Please fill all fields.')
        }
    })
}
