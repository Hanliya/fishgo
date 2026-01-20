const token = localStorage.getItem("token");

function addFish() {
  fetch("http://localhost:8080/admin/fish", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      "Authorization": "Bearer " + token
    },
    body: JSON.stringify({
      name: name.value,
      pricePerKg: price.value,
      quantityKg: qty.value
    })
  }).then(() => loadFish());
}

function loadFish() {
  fetch("http://localhost:8080/admin/fish", {
    headers: { "Authorization": "Bearer " + token }
  })
  .then(res => res.json())
  .then(data => {
    fishList.innerHTML = data.map(f =>
      `<li>${f.name} - ₹${f.pricePerKg}/kg</li>`
    ).join("");
  });
}

loadFish();
