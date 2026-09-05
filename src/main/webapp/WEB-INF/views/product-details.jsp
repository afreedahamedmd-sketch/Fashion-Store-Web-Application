<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>${product.productName} - FashionStore</title>

    <link rel="stylesheet"
          href="<c:url value='/assets/css/style.css' />">

    <link rel="stylesheet"
          href="<c:url value='/assets/css/product-details.css' />">

    <script src="<c:url value='/assets/js/product-details.js' />"></script>
</head>

<body>

    <jsp:include page="/WEB-INF/views/partials/navbar.jsp" />

    <main class="product-details-page">

        <div class="container">

            <a href="<c:url value='/products' />"
               class="back-to-products">
                ← Back to Products
            </a>

            <div class="product-details">

                <section class="product-gallery">

                    <c:choose>

                        <c:when test="${not empty images}">

                            <div class="main-product-image">

                                <c:forEach var="image" items="${images}">

                                    <c:if test="${image.isPrimary()}">

                                        <img id="mainProductImage"
                                             src="<c:url value='/${image.imagePath}' />"
                                             alt="${product.productName}">

                                    </c:if>

                                </c:forEach>

                            </div>

                            <div class="product-thumbnails">

                                <c:forEach var="image" items="${images}">

                                    <button type="button"
                                            class="product-thumbnail"
                                            onclick="changeProductImage('<c:url value='/${image.imagePath}' />')">

                                        <img src="<c:url value='/${image.imagePath}' />"
                                             alt="${product.productName}">

                                    </button>

                                </c:forEach>

                            </div>

                        </c:when>

                        <c:otherwise>

                            <div class="main-product-image no-image">
                                <span>👕</span>
                            </div>

                        </c:otherwise>

                    </c:choose>

                </section>


                <section class="product-information">

                    <p class="product-brand">
                        ${product.brand}
                    </p>

                    <h1>
                        ${product.productName}
                    </h1>

                    <p class="product-gender">
                        ${product.gender}
                    </p>

                    <div class="product-price">
                        ₹${product.price}
                    </div>

                    <c:if test="${product.discount > 0}">

                        <p class="product-discount">
                            ${product.discount}% OFF
                        </p>

                    </c:if>


                    <div class="product-description">

                        <h2>Description</h2>

                        <p>
                            ${product.description}
                        </p>

                    </div>


                    <form action="<c:url value='/cart' />"
                          method="post"
                          class="add-to-cart-form">

                        <input type="hidden"
                               name="action"
                               value="add">

                        <input type="hidden"
                               name="productId"
                               value="${product.productId}">


                        <div class="product-size-section">

                            <h2>Select Size</h2>

                            <div class="size-options">

                                <c:forEach var="variant" items="${variants}">

                                    <label class="size-option">

                                        <input type="radio"
                                               name="variantId"
                                               value="${variant.variantId}"
                                               ${variant.stock == 0 ? 'disabled' : ''}
                                               required>

                                        <span>
                                            ${variant.size}
                                        </span>

                                    </label>

                                </c:forEach>

                            </div>

                        </div>


                        <div class="product-stock">

                            <c:choose>

                                <c:when test="${not empty variants}">
                                    <span>
                                        Select a size to see availability.
                                    </span>
                                </c:when>

                                <c:otherwise>
                                    <span>
                                        Currently unavailable.
                                    </span>
                                </c:otherwise>

                            </c:choose>

                        </div>


                        <div class="quantity-control">

                            <label for="quantity">
                                Quantity
                            </label>

                            <input type="number"
                                   id="quantity"
                                   name="quantity"
                                   value="1"
                                   min="1"
                                   max="10"
                                   required>

                        </div>


                        <button type="submit"
                                class="add-to-cart-button">

                            Add to Cart

                        </button>

                    </form>

                </section>

            </div>

        </div>


        <section class="related-products-section">

            <div class="container">

                <div class="related-products-header">

                    <h2>
                        Related Products
                    </h2>

                    <p>
                        Explore more styles from this category.
                    </p>

                </div>


                <div class="related-products-grid">

                    <c:forEach var="relatedProduct"
                               items="${relatedProducts}">

                        <a href="<c:url value='/product-details' />?productId=${relatedProduct.productId}"
                           class="related-product-card">


                            <div class="related-product-image">

                                <c:choose>

                                    <c:when test="${not empty relatedProductImages[relatedProduct.productId]}">

                                        <img src="<c:url value='/${relatedProductImages[relatedProduct.productId].imagePath}' />"
                                             alt="${relatedProduct.productName}">

                                    </c:when>

                                    <c:otherwise>

                                        <span>👕</span>

                                    </c:otherwise>

                                </c:choose>

                            </div>


                            <div class="related-product-info">

                                <p class="related-product-brand">
                                    ${relatedProduct.brand}
                                </p>

                                <h3>
                                    ${relatedProduct.productName}
                                </h3>

                                <p class="related-product-gender">
                                    ${relatedProduct.gender}
                                </p>

                                <p class="related-product-price">
                                    ₹${relatedProduct.price}
                                </p>

                            </div>

                        </a>

                    </c:forEach>

                </div>

            </div>

        </section>

    </main>


    <jsp:include page="/WEB-INF/views/partials/footer.jsp" />

</body>
</html>