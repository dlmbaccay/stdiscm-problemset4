export async function getUserNotifications(userId, token) {
    const res = await fetch(`http://localhost:8081/notifications/recipient/${userId}`, {
        headers: {
            Authorization: `Bearer ${token}`,
        },
    })

    if (!res.ok) {
        const msg = await res.text()
        throw new Error(msg)
    }

    return res.json()
}

export async function markNotificationAsRead(notificationId, token) {
    const res = await fetch(`http://localhost:8081/notifications/${notificationId}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify({ read: true }),
    })

    if (!res.ok) {
        const msg = await res.text()
        throw new Error(msg)
    }

    return await res.json()
}

export async function deleteNotification(notificationId, token) {
    const res = await fetch(`http://localhost:8081/notifications/${notificationId}`, {
        method: 'DELETE',
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

export async function createNotification(notification, token) {
    const res = await fetch(`http://localhost:8081/notifications/create`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(notification),
    })

    if (!res.ok) {
        const msg = await res.text()
        throw new Error(msg)
    }

    return await res.json()
}
