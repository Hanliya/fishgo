FishGo

One-liner:
FishGo is a role-based online fish marketplace connecting admins, fishermen, and customers with real-time fish management.

Table of Contents

Overview

Features

Technologies

Setup & Installation

API Endpoints

Frontend

Notes

Overview

FishGo is a web application that enables:

Admins to manage fishermen, approve fish listings, and handle settlements.

Fishermen (Vendors) to add fish for sale and manage their inventory.

Customers to browse available fish and place orders.

The system uses JWT authentication for secure role-based access.

Features

Role-based Authentication: Admin, Fisherman, Customer, and Delivery Partner roles.

Fish Management: Add, view, and approve fish listings.

Order Management: Customers can place orders; Admins and Fishermen can update statuses.

Payouts & Payments: Admin-controlled payouts and Razorpay integration for payments.

Dashboard & Frontend: Responsive UI with fish cards, login pages, and management dashboards.

Technologies

Backend: Java, Spring Boot, Spring Security, JWT, Hibernate, MySQL

Frontend: HTML, CSS, JavaScript

Database: MySQL

Payment Gateway: Razorpay

Build Tool: Maven

Setup & Installation

Clone Repository

git clone <your-repo-url>
cd fishgo


Configure Database

Create a MySQL schema named fishgo.

Update application.properties with your DB credentials.

spring.datasource.url=jdbc:mysql://localhost:3306/fishgo
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update


Build & Run

mvn clean install
mvn spring-boot:run


Access Frontend

Open browser: http://localhost:8080/frontend/login.html

API Endpoints

Authentication

POST /api/auth/register – Register user

POST /api/auth/login – Login user (returns JWT)

Admin

GET /api/admin/fishermen/pending – List pending fishermen

POST /api/admin/fishermen/{id}/approve – Approve fisherman

POST /api/admin/fish/{id}/status – Update fish status

Fish

GET /api/fish/available – List available fish

POST /api/fish/add – Add fish (Admin & Fisherman only)

GET /api/fish/my – List fish by vendor

Orders & Payments

POST /api/orders – Create order (Customer only)

POST /api/orders/{id}/status – Update order status (Admin & Fisherman)

POST /api/payments/initiate – Initiate payment

POST /api/payments/verify – Verify payment

Settlements

GET /api/admin/settlements/pending – List pending payouts

POST /api/admin/settlements/{payoutId}/pay – Mark payout as paid

Frontend

HTML pages: login.html, dashboard.html, vendors.html, fish.html

JS files: auth.js, dashboard.js, fish.js, vendors.js

CSS: style.css

Images: fish1.jpg to fish8.jpg

Notes

Only approved Fishermen can add fish; Admin approval required.

JWT token must be included in Authorization: Bearer <token> header for protected endpoints.

Payment integration is mocked with Razorpay; use frontend keys for testing.

For quick deployment, frontend uses static HTML/CSS; can be upgraded later.# fishgo
FishGo: A role-based online fish marketplace connecting admins, fishermen, and customers with real-time fish management
