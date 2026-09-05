<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"
         isELIgnored="false" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - FashionStore</title>
    <link rel="stylesheet" href="<c:url value='/assets/css/style.css' />">
    <link rel="stylesheet" href="<c:url value='/assets/css/auth.css' />">
</head>
<body>
    <jsp:include page="/WEB-INF/views/partials/navbar.jsp" />
    <main class="auth-page">
        <div class="container">
            <div class="auth-container">
                <div class="auth-header">
                    <h1>Welcome Back</h1>
                    <p>Login to your FashionStore account.</p>
                </div>
                <c:if test="${param.registered == 'true'}">
                    <div class="auth-message success-message">Registration successful. Please login to continue.</div>
                </c:if>
                <c:if test="${not empty errorMessage}">
                    <div class="auth-message error-message">${errorMessage}</div>
                </c:if>
                <form action="<c:url value='/login' />" method="post" class="auth-form">
                    <div class="form-group">
                        <label for="email">Email</label>
                        <input type="email" id="email" name="email" value="${param.email}" placeholder="Enter your email" required>
                    </div>
                    <div class="form-group">
                        <label for="password">Password</label>
                        <input type="password" id="password" name="password" placeholder="Enter your password" required>
                    </div>
                    <button type="submit" class="auth-button">Login</button>
                </form>
                <div class="auth-footer">
                    <p>Don't have an account? <a href="<c:url value='/register' />">Create an account</a></p>
                </div>
            </div>
        </div>
    </main>
    <jsp:include page="/WEB-INF/views/partials/footer.jsp" />
</body>
</html>