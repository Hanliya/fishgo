const token = localStorage.getItem("token");
const role = localStorage.getItem("role");

if (!token) window.location.href = "/frontend/login.html";

document.getElementById("welcome-msg").innerText = `Welcome, ${role}`;

if (role === "ADMIN") document.getElementById("admin-section").style.display = "block";
if (role === "FISHERMAN") document.getElementById("fisherman-section").style.display = "block";
if (role === "CUSTOMER") document.getElementById("customer-section").style.display = "block";

function logout() {
    localStorage.clear();
    window.location.href = "/frontend/login.html";
}

// Load Admin Data
if(role === "ADMIN") {
    fetch("http://localhost:8080/api/admin/fishermen/pending", { headers: { "Authorization": "Bearer "+token } })
        .then(res => res.json())
        .then(data => {
            const container = document.getElementById("pending-fishermen");
            container.innerHTML = data.map(u => `
                <div class="fish-card">
                    <h4>${u.name}</h4>
                    <p>${u.email}</p>
                    <button onclick="approveFisherman(${u.id})">Approve</button>
                </div>
            `).join('');
        });
}

function approveFisherman(id){
    fetch(`http://localhost:8080/api/admin/fishermen/${id}/approve`, {
        method: "POST",
        headers: { "Authorization": "Bearer "+token }
    }).then(()=>window.location.reload());
}

// Load Customer Fish
if(role === "CUSTOMER") {
    fetch("http://localhost:8080/api/fish/available", { headers: { "Authorization": "Bearer "+token } })
        .then(res => res.json())
        .then(data => {
            const container = document.getElementById("available-fish");
            const images = ["fish1.jpg","fish2.jpg","fish3.jpg","fish4.jpg","fish5.jpg","fish6.jpg","fish7.jpg","fish8.jpg"];
            container.innerHTML = data.map((f,i) => `
                <div class="fish-card">
                    <img src="/images/${images[i % images.length]}" alt="${f.name}">
                    <h3>${f.name}</h3>
                    <p>${f.price} ₹ per kg</p>
                </div>
            `).join('');
        });
}

// Load Fisherman Fish
if(role === "FISHERMAN") {
    fetch("http://localhost:8080/api/fish/my", { headers: { "Authorization": "Bearer "+token } })
        .then(res => res.json())
        .then(data => {
            const container = document.getElementById("my-fish");
            const images = ["fish1.jpg","fish2.jpg","fish3.jpg","fish4.jpg","fish5.jpg","fish6.jpg","fish7.jpg","fish8.jpg"];
            container.innerHTML = data.map((f,i) => `
                <div class="fish-card">
                    <img src="/images/${images[i % images.length]}" alt="${f.name}">
                    <h3>${f.name}</h3>
                    <p>${f.price} ₹ per kg</p>
                    <p>Status: ${f.status}</p>
                </div>
            `).join('');
        });

    // Add Fish
    document.getElementById("add-fish-form").onsubmit = (e) => {
        e.preventDefault();
        const fish = {
            name: document.getElementById("fish-name").value,
            category: document.getElementById("fish-category").value,
            price: parseFloat(document.getElementById("fish-price").value),
            weight: parseFloat(document.getElementById("fish-weight").value),
            freshnessStatus: document.getElementById("fish-freshness").value,
            status: "PENDING"
        };
        fetch("http://localhost:8080/api/fish/add", {
            method: "POST",
            headers: {
                "Content-Type":"application/json",
                "Authorization":"Bearer "+token
            },
            body: JSON.stringify(fish)
        }).then(()=>window.location.reload());
    };
}
