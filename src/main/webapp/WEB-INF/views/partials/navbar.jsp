<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"
         isELIgnored="false" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<header class="navbar">
    <div class="container navbar-container">
        <div class="logo">
            <a href="<c:url value='/' />">Fashion<span>Store</span></a>
        </div>
        <form class="search-bar" action="<c:url value='/products' />" method="get">
            <input type="text" name="keyword" placeholder="Search for fashion products...">
            <button type="submit">Search</button>
        </form>
        <nav class="nav-links">
            <a href="<c:url value='/' />">Home</a>
            <a href="<c:url value='/products' />">Products</a>
            <a href="<c:url value='/products' />?gender=Men">Men</a>
            <a href="<c:url value='/products' />?gender=Women">Women</a>
            <a href="<c:url value='/products' />?gender=Kids">Kids</a>
            <a href="<c:url value='/cart' />">Cart</a>
            <c:choose>
                <c:when test="${not empty sessionScope.loggedInUser}">
                    <a href="<c:url value='/profile' />">Profile</a>
                    <a href="<c:url value='/logout' />">Logout</a>
                </c:when>
                <c:otherwise>
                    <a href="<c:url value='/login' />">Login</a>
                </c:otherwise>
            </c:choose>
        </nav>
    </div>
</header>