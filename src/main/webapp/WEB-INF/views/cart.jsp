<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"
         isELIgnored="false" %>

<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>Shopping Cart - FashionStore</title>

    <link rel="stylesheet"
          href="<c:url value='/assets/css/style.css' />">

    <link rel="stylesheet"
          href="<c:url value='/assets/css/cart.css' />">

</head>

<body>

    <jsp:include page="/WEB-INF/views/partials/navbar.jsp" />

    <main class="cart-page">

        <div class="container">

            <div class="cart-header">

                <h1>Shopping Cart</h1>

                <p>
                    Review your selected fashion items.
                </p>

            </div>

            <c:choose>

                <c:when test="${empty cartItems}">

                    <div class="empty-cart">

                        <div class="empty-cart-icon">
                            🛍️
                        </div>

                        <h2>
                            Your cart is empty
                        </h2>

                        <p>
                            Looks like you haven't added anything
                            to your cart yet.
                        </p>

                        <a
                            href="<c:url value='/products' />"
                            class="continue-shopping-button">

                            Continue Shopping

                        </a>

                    </div>

                </c:when>

                <c:otherwise>

                    <div class="cart-layout">

                        <section class="cart-items-section">

                            <c:forEach
                                var="cartItem"
                                items="${cartItems}">

                                <article
                                    class="cart-item"
                                    data-price="${cartItem.price}"
                                    data-stock="${cartItem.stock}">

                                    <a
                                        href="<c:url value='/product-details' />?productId=${cartItem.productId}"
                                        class="cart-item-image">

                                        <c:choose>

                                            <c:when test="${not empty cartItem.imagePath}">

                                                <img
                                                    src="<c:url value='/${cartItem.imagePath}' />"
                                                    alt="${cartItem.productName}">

                                            </c:when>

                                            <c:otherwise>

                                                <span>👕</span>

                                            </c:otherwise>

                                        </c:choose>

                                    </a>

                                    <div class="cart-item-details">

                                        <p class="cart-item-brand">
                                            ${cartItem.brand}
                                        </p>

                                        <h2>

                                            <a
                                                href="<c:url value='/product-details' />?productId=${cartItem.productId}">

                                                ${cartItem.productName}

                                            </a>

                                        </h2>

                                        <p class="cart-item-size">

                                            Size:
                                            <strong>
                                                ${cartItem.size}
                                            </strong>

                                        </p>

                                        <p class="cart-item-price">

                                            ₹${cartItem.price}

                                        </p>

                                        <p class="cart-item-subtotal">

                                            Subtotal:
                                            <strong>
                                                ₹${cartItem.price * cartItem.quantity}
                                            </strong>

                                        </p>

                                    </div>

                                    <div class="cart-item-actions">

                                        <div class="quantity-section">

                                            <label
                                                for="quantity-${cartItem.cartItemId}">

                                                Quantity

                                            </label>

                                            <div class="quantity-control">

                                                <button
                                                    type="button"
                                                    class="quantity-button decrease-quantity"
                                                    aria-label="Decrease quantity">

                                                    −

                                                </button>

                                                <input
                                                    type="number"
                                                    id="quantity-${cartItem.cartItemId}"
                                                    class="quantity-input"
                                                    value="${cartItem.quantity}"
                                                    min="1"
                                                    max="${cartItem.stock}"
                                                    data-cart-item-id="${cartItem.cartItemId}"
                                                    data-price="${cartItem.price}">

                                                <button
                                                    type="button"
                                                    class="quantity-button increase-quantity"
                                                    aria-label="Increase quantity">

                                                    +

                                                </button>

                                            </div>

                                            <small class="stock-message">

                                                ${cartItem.stock} available

                                            </small>

                                        </div>

                                        <form
                                            action="<c:url value='/cart' />"
                                            method="post"
                                            class="remove-form">

                                            <input
                                                type="hidden"
                                                name="action"
                                                value="remove">

                                            <input
                                                type="hidden"
                                                name="cartItemId"
                                                value="${cartItem.cartItemId}">

                                            <button
                                                type="submit"
                                                class="remove-button">

                                                Remove

                                            </button>

                                        </form>

                                    </div>

                                </article>

                            </c:forEach>

                        </section>

                        <aside class="cart-summary">

                            <h2>
                                Order Summary
                            </h2>

                            <div class="summary-row">

                                <span>
                                    Items
                                </span>

                                <span id="cartItemCount">
                                    ${cartItems.size()}
                                </span>

                            </div>

                            <div class="summary-row">

                                <span>
                                    Subtotal
                                </span>

                                <span id="cartSubtotal">
                                    ₹${cartTotal}
                                </span>

                            </div>

                            <div class="summary-row">

                                <span>
                                    Shipping
                                </span>

                                <span>
                                    Free
                                </span>

                            </div>

                            <div class="summary-divider"></div>

                            <div class="summary-total">

                                <span>
                                    Total
                                </span>

                                <strong id="cartTotal">
                                    ₹${cartTotal}
                                </strong>

                            </div>

                            <a
                                href="<c:url value='/checkout' />"
                                class="checkout-button">

                                Proceed to Checkout

                            </a>

                            <a
                                href="<c:url value='/products' />"
                                class="continue-shopping-link">

                                Continue Shopping

                            </a>

                        </aside>

                    </div>

                </c:otherwise>

            </c:choose>

        </div>

    </main>

    <jsp:include page="/WEB-INF/views/partials/footer.jsp" />

    <script>
        window.contextPath = "${pageContext.request.contextPath}";
    </script>

    <script src="<c:url value='/assets/js/cart.js' />"></script>

</body>

</html>