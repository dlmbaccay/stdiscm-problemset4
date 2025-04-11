export default function loginView() {
    const app = document.getElementById('app')
    app.innerHTML = `
      <div class="form-container">
        <h2>Login to Garynation University</h2>
        <form id="login-form">
          <input type="email" placeholder="Email" name="email" required />
          <input type="password" placeholder="Password" name="password" required />
          <button type="submit">Login</button>
        </form>
      </div>
    `

    document.getElementById('login-form').addEventListener('submit', async (e) => {
        e.preventDefault()
        const email = e.target.email.value
        const password = e.target.password.value

        // Simulated login (replace with actual fetch call)
        if (email && password) {
            localStorage.setItem('token', 'fake-token')
            alert('Login successful!')
            location.hash = '/courses'
        } else {
            alert('Invalid credentials.')
        }
    })
}
