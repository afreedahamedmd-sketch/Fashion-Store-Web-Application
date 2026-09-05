<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"
         isELIgnored="false" %>

<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Checkout - FashionStore</title>

    <link rel="stylesheet"
          href="<c:url value='/assets/css/style.css' />">

    <link rel="stylesheet"
          href="<c:url value='/assets/css/checkout.css' />">
</head>

<body>

<jsp:include page="/WEB-INF/views/partials/navbar.jsp" />

<main class="checkout-page">

    <div class="checkout-container">

        <h1 class="checkout-title">Checkout</h1>

        <div class="checkout-layout">

            <!-- Delivery Information -->
            <section class="checkout-card">

                <h2>Delivery Information</h2>

                <form action="<c:url value='/checkout' />" method="post">

                    <div class="form-row">

                        <div class="form-group">
                            <label for="userName">Full Name</label>
                            <input type="text"
                                   id="userName"
                                   name="userName"
                                   value="${user.userName}"
                                   required>
                        </div>

                        <div class="form-group">
                            <label for="email">Email</label>
                            <input type="email"
                                   id="email"
                                   name="email"
                                   value="${user.email}"
                                   readonly>
                        </div>

                    </div>

                    <div class="form-group">
                        <label for="phone">Phone Number</label>
                        <input type="tel"
                               id="phone"
                               name="phone"
                               value="${user.phone}"
                               required>
                    </div>

                    <div class="form-group">
                        <label for="shippingAddress">Shipping Address</label>
                        <textarea id="shippingAddress"
                                  name="shippingAddress"
                                  required>${user.address}</textarea>
                    </div>

                    <div class="form-row">

                        <div class="form-group">
                            <label for="city">City</label>
                            <input type="text"
                                   id="city"
                                   name="city"
                                   value="${user.city}"
                                   required>
                        </div>

                        <div class="form-group">
                            <label for="state">State</label>
                            <input type="text"
                                   id="state"
                                   name="state"
                                   value="${user.state}"
                                   required>
                        </div>

                    </div>

                    <div class="form-group">
                        <label for="pincode">Pincode</label>
                        <input type="text"
                               id="pincode"
                               name="pincode"
                               value="${user.pincode}"
                               maxlength="10"
                               required>
                    </div>

                    <!-- Payment -->
                    <div class="payment-section">

                        <h2>Payment Method</h2>

                        <div class="payment-options">

                            <label class="payment-option">
                                <input type="radio"
                                       name="paymentMethod"
                                       value="Cash on Delivery"
                                       checked>
                                <span>Cash on Delivery</span>
                            </label>

                            <label class="payment-option">
                                <input type="radio"
                                       name="paymentMethod"
                                       value="UPI">
                                <span>UPI</span>
                            </label>

                            <label class="payment-option">
                                <input type="radio"
                                       name="paymentMethod"
                                       value="Credit Card">
                                <span>Credit Card</span>
                            </label>

                            <label class="payment-option">
                                <input type="radio"
                                       name="paymentMethod"
                                       value="Debit Card">
                                <span>Debit Card</span>
                            </label>

                            <label class="payment-option">
                                <input type="radio"
                                       name="paymentMethod"
                                       value="Net Banking">
                                <span>Net Banking</span>
                            </label>

                        </div>

                    </div>

                    <button type="submit" class="place-order-btn">
                        Place Order
                    </button>

                </form>

            </section>


            <!-- Order Summary -->
            <aside class="order-summary">

                <h2>Order Summary</h2>

                <c:forEach var="item" items="${cartItems}">

                    <div class="summary-item">

                        <div class="summary-product">

                            <div class="summary-product-name">
                                ${item.productName}
                            </div>

                            <div class="summary-product-details">
                                Size: ${item.size}
                                &nbsp; | &nbsp;
                                Qty: ${item.quantity}
                            </div>

                        </div>

                        <div class="summary-price">
                            ₹${item.price * item.quantity}
                        </div>

                    </div>

                </c:forEach>


                <div class="summary-total-row">
                    <span>Subtotal</span>
                    <span>₹${subtotal}</span>
                </div>


                <div class="summary-total-row">

                    <span>Shipping</span>

                    <span>
                        <c:choose>
                            <c:when test="${shipping == 0}">
                                <span class="shipping-free">FREE</span>
                            </c:when>

                            <c:otherwise>
                                ₹${shipping}
                            </c:otherwise>
                        </c:choose>
                    </span>

                </div>


                <div class="summary-total-row summary-total">
                    <span>Total</span>
                    <span>₹${total}</span>
                </div>

            </aside>

        </div>

    </div>

</main>

</body>
</html>