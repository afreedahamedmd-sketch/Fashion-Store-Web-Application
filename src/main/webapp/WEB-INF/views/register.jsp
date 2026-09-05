<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"
         isELIgnored="false" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Create Account - FashionStore</title>
    <link rel="stylesheet" href="<c:url value='/assets/css/style.css' />">
    <link rel="stylesheet" href="<c:url value='/assets/css/auth.css' />">
</head>
<body>
    <jsp:include page="/WEB-INF/views/partials/navbar.jsp" />
    <main class="auth-page">
        <div class="container">
            <div class="auth-container">
                <div class="auth-header">
                    <h1>Create Your Account</h1>
                    <p>Join FashionStore and start exploring the latest fashion.</p>
                </div>
                <c:if test="${not empty errorMessage}">
                    <div class="auth-message error-message">${errorMessage}</div>
                </c:if>
                <form action="<c:url value='/register' />" method="post" class="auth-form">
                    <div class="form-row">
                        <div class="form-group">
                            <label for="userName">Full Name</label>
                            <input type="text" id="userName" name="userName" value="${param.userName}" placeholder="Enter your full name" required>
                        </div>
                        <div class="form-group">
                            <label for="email">Email</label>
                            <input type="email" id="email" name="email" value="${param.email}" placeholder="Enter your email" required>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="form-group">
                            <label for="password">Password</label>
                            <input type="password" id="password" name="password" placeholder="Enter your password" required>
                        </div>
                        <div class="form-group">
                            <label for="phone">Phone</label>
                            <input type="tel" id="phone" name="phone" value="${param.phone}" placeholder="Enter your phone number" required>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="address">Address</label>
                        <input type="text" id="address" name="address" value="${param.address}" placeholder="Enter your address" required>
                    </div>
                    <div class="form-row">
                        <div class="form-group">
                            <label for="city">City</label>
                            <input type="text" id="city" name="city" value="${param.city}" placeholder="Enter your city" required>
                        </div>
                        <div class="form-group">
                            <label for="state">State</label>
                            <input type="text" id="state" name="state" value="${param.state}" placeholder="Enter your state" required>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="pincode">Pincode</label>
                        <input type="text" id="pincode" name="pincode" value="${param.pincode}" placeholder="Enter your pincode" required>
                    </div>
                    <button type="submit" class="auth-button">Create Account</button>
                </form>
                <div class="auth-footer">
                    <p>Already have an account? <a href="<c:url value='/login' />">Login here</a></p>
                </div>
            </div>
        </div>
    </main>
    <jsp:include page="/WEB-INF/views/partials/footer.jsp" />
</body>
</html>