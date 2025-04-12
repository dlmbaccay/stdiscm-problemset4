import { getUserNotifications } from '../src/models/notificationModel.js'

window.openNotificationModal = async function () {
    const modal = document.getElementById('notificationModal')
    const container = document.getElementById('notification-list')
    container.innerHTML = '<p>Loading notifications...</p>'
    modal.style.display = 'block'

    const user = JSON.parse(localStorage.getItem('user'))
    if (!user) return (container.innerHTML = '<p>You must be logged in.</p>')

    try {
        const notifications = await getUserNotifications(user.id, user.token)

        if (notifications.length === 0) {
            container.innerHTML = '<p>No notifications yet.</p>'
            return
        }

        container.innerHTML = notifications
            .map(
                (n) => `
            <div style="border-bottom: 1px solid #ccc; padding: 0.5rem 0;">
                <p><strong>${n.type.replace('_', ' ')}</strong> — ${n.message}</p>
                <small>${new Date(n.created_at).toLocaleString()}</small>
            </div>
        `,
            )
            .join('')
    } catch (err) {
        console.error('Failed to fetch notifications:', err)
        container.innerHTML = '<p>Error loading notifications.</p>'
    }
}
