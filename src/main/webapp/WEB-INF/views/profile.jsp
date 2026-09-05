<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"
         isELIgnored="false" %>

<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="UTF-8">

    <title>My Profile - FashionStore</title>

    <link rel="stylesheet"
          href="<c:url value='/assets/css/style.css' />">

    <link rel="stylesheet"
          href="<c:url value='/assets/css/profile.css' />">

</head>

<body>

<jsp:include page="/WEB-INF/views/partials/navbar.jsp" />

<main class="profile-page">

    <div class="profile-container">

        <h1>My Profile</h1>

        <section class="profile-card">

            <div class="profile-header">
                <div class="profile-avatar">
                    ${user.userName.substring(0,1)}
                </div>

                <div>
                    <h2>${user.userName}</h2>
                    <p>${user.email}</p>
                </div>
            </div>

            <div class="profile-section">

                <h3>Personal Information</h3>

                <div class="profile-grid">

                    <div class="profile-field">
                        <span class="profile-label">Full Name</span>
                        <span class="profile-value">
                            ${user.userName}
                        </span>
                    </div>

                    <div class="profile-field">
                        <span class="profile-label">Email</span>
                        <span class="profile-value">
                            ${user.email}
                        </span>
                    </div>

                    <div class="profile-field">
                        <span class="profile-label">Phone</span>
                        <span class="profile-value">
                            ${user.phone}
                        </span>
                    </div>

                    <div class="profile-field">
                        <span class="profile-label">Member Since</span>
                        <span class="profile-value">
                            ${user.createdDate}
                        </span>
                    </div>

                </div>

            </div>

            <div class="profile-section">

                <h3>Delivery Address</h3>

                <div class="profile-grid">

                    <div class="profile-field profile-full">
                        <span class="profile-label">Address</span>
                        <span class="profile-value">
                            ${user.address}
                        </span>
                    </div>

                    <div class="profile-field">
                        <span class="profile-label">City</span>
                        <span class="profile-value">
                            ${user.city}
                        </span>
                    </div>

                    <div class="profile-field">
                        <span class="profile-label">State</span>
                        <span class="profile-value">
                            ${user.state}
                        </span>
                    </div>

                    <div class="profile-field">
                        <span class="profile-label">Pincode</span>
                        <span class="profile-value">
                            ${user.pincode}
                        </span>
                    </div>

                </div>

            </div>

            <div class="profile-actions">

                <a href="<c:url value='/products' />"
                   class="profile-btn">
                    Continue Shopping
                </a>

                <a href="<c:url value='/logout' />"
                   class="profile-btn secondary">
                    Logout
                </a>

            </div>

        </section>

    </div>

</main>

</body>

</html>