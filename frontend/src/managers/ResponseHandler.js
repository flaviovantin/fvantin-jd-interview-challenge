export const responseHandle = response => new Promise((resolve, reject) => {
    response.json().then(data => {
        response.ok ? resolve(data) : reject(data)
    })
})