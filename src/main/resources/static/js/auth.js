function login() {
    const email = document.getElementById("username").value;
    const password = document.getElementById("password").value;

    fetch("/api/auth/login", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            email: email,
            password: password
        })
    })
    .then(res => {
        if (!res.ok) {
            return res.text().then(t => { throw new Error(t || "Login failed"); });
        }
        return res.json();
    })
    .then(data => {
        localStorage.setItem("token", data.token);
        window.location.href = "/frontend/dashboard.html";
    })
    .catch(err => {
        alert(err.message);
        console.error(err);
    });
}
