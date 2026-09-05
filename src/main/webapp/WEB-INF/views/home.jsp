<%@ page contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    isELIgnored="false" %>

<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>FashionStore - Fashion for Everyone</title>

    <link rel="stylesheet"
          href="<c:url value='/assets/css/style.css' />">

    <link rel="stylesheet"
          href="<c:url value='/assets/css/home.css' />">
</head>

<body>

    <jsp:include page="/WEB-INF/views/partials/navbar.jsp" />

    <main class="fashion-home">

        <!-- ================= HERO SECTION ================= -->

        <section class="home-hero">

            <div class="home-hero-container">

                <!-- LEFT SIDE : TEXT -->

                <div class="home-hero-content">

                    <h1>
                        Discover Your
                        <span>Perfect Style</span>
                    </h1>

                    <p>
                        Explore the latest fashion trends for men, women and kids.
                        Find styles that match your personality and make every day
                        fashionable.
                    </p>

                    <div class="home-hero-buttons">

                        <a href="<c:url value='/products' />"
                           class="home-btn home-btn-primary">
                            Shop Now
                        </a>

                        <a href="<c:url value='/products' />"
                           class="home-btn home-btn-secondary">
                            Explore Collection
                        </a>

                    </div>

                </div>


                <!-- RIGHT SIDE : HERO IMAGE -->

                <div class="home-hero-image">

                    <img
                        src="<c:url value='/assets/images/hero-fashion.jpg' />"
                        alt="Fashion Collection"
                        class="home-hero-image-img">

                </div>

            </div>

        </section>


        <!-- ================= CATEGORY SECTION ================= -->

        <section class="home-section">

            <div class="home-container">

                <div class="home-section-header">

                    <h2>Shop by Category</h2>

                    <a href="<c:url value='/products' />">
                        View All
                    </a>

                </div>


                <div class="home-category-grid">

                    <c:forEach var="category" items="${categories}">

                        <a
                            href="<c:url value='/products' />?categoryId=${category.categoryId}"
                            class="home-category-card">

                            <div class="home-category-image">

                                <c:if test="${not empty categoryImages[category.categoryId]}">

                                    <img
                                        src="<c:url value='/${categoryImages[category.categoryId].imagePath}' />"
                                        alt="${category.categoryName}">

                                </c:if>

                                <c:if test="${empty categoryImages[category.categoryId]}">

                                    <span>👕</span>

                                </c:if>

                            </div>


                            <div class="home-category-info">

                                <h3>${category.categoryName}</h3>

                                <p>${category.description}</p>

                            </div>

                        </a>

                    </c:forEach>

                </div>

            </div>

        </section>


        <!-- ================= NEW ARRIVALS ================= -->

        <section class="home-section">

            <div class="home-container">

                <div class="home-section-header">

                    <h2>New Arrivals</h2>

                    <a href="<c:url value='/products' />">
                        View All Products
                    </a>

                </div>


                <div class="home-product-grid">

                    <c:forEach var="product" items="${featuredProducts}">

                        <div class="home-product-card">

                            <div class="home-product-image">

                                <c:if test="${not empty primaryImages[product.productId]}">

                                    <img
                                        src="<c:url value='/${primaryImages[product.productId].imagePath}' />"
                                        alt="${product.productName}">

                                </c:if>

                                <c:if test="${empty primaryImages[product.productId]}">

                                    <span>👕</span>

                                </c:if>

                            </div>


                            <div class="home-product-info">

                                <p class="home-product-brand">
                                    ${product.brand}
                                </p>

                                <h3>
                                    ${product.productName}
                                </h3>

                                <p class="home-product-gender">
                                    ${product.gender}
                                </p>

                                <div class="home-product-price">
                                    ₹${product.price}
                                </div>

                                <a
                                    href="<c:url value='/product-details' />?productId=${product.productId}"
                                    class="home-btn home-btn-primary home-product-button">
                                    View Product
                                </a>

                            </div>

                        </div>

                    </c:forEach>

                </div>

            </div>

        </section>


        <!-- ================= OFFER SECTION ================= -->

        <section class="home-section">

            <div class="home-container">

                <div class="home-offer-banner">

                    <div>

                        <h2>
                            Refresh Your Wardrobe
                        </h2>

                        <p>
                            Discover stylish fashion at prices you'll love.
                        </p>

                    </div>

                    <a
                        href="<c:url value='/products' />"
                        class="home-btn home-offer-button">
                        Shop Collection
                    </a>

                </div>

            </div>

        </section>

    </main>


    <jsp:include page="/WEB-INF/views/partials/footer.jsp" />

</body>
</html>