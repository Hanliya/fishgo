const token = localStorage.getItem("token");

function addVendor() {
  fetch("http://localhost:8080/admin/vendors", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      "Authorization": "Bearer " + token
    },
    body: JSON.stringify({
      name: name.value,
      phone: phone.value,
      harbourLocation: harbour.value
    })
  }).then(() => loadVendors());
}

function loadVendors() {
  fetch("http://localhost:8080/admin/vendors", {
    headers: { "Authorization": "Bearer " + token }
  })
  .then(res => res.json())
  .then(data => {
    vendorList.innerHTML = data.map(v =>
      `<li>${v.name} - ${v.phone}</li>`
    ).join("");
  });
}

loadVendors();
