<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"
         isELIgnored="false" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<footer class="footer">
    <div class="container footer-container">
        <div class="footer-brand">
            <h3>Fashion<span>Store</span></h3>
            <p>Your destination for the latest fashion and trends.</p>
        </div>
        <div class="footer-links">
            <h4>Quick Links</h4>
            <a href="<c:url value='/' />">Home</a>
            <a href="<c:url value='/products' />">Products</a>
            <a href="<c:url value='/cart' />">Cart</a>
            <a href="<c:url value='/login' />">Login</a>
        </div>
        <div class="footer-info">
            <h4>FashionStore</h4>
            <p>Quality fashion for every style.</p>
            <p>&copy; 2026 FashionStore. All rights reserved.</p>
        </div>
    </div>
</footer>