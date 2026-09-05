<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
    isELIgnored="false" %>

<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Order Successful - FashionStore</title>

    <link rel="stylesheet"
          href="<c:url value='/assets/css/style.css' />">

    <link rel="stylesheet"
          href="<c:url value='/assets/css/order-success.css' />">
</head>

<body>

    <!-- NAVBAR -->
    <jsp:include page="/WEB-INF/views/partials/navbar.jsp" />


    <!-- ================= ORDER SUCCESS ================= -->

    <main class="success-page">

        <!-- Animated background particles -->
        <div class="success-particle particle-1"></div>
        <div class="success-particle particle-2"></div>
        <div class="success-particle particle-3"></div>
        <div class="success-particle particle-4"></div>
        <div class="success-particle particle-5"></div>
        <div class="success-particle particle-6"></div>


        <div class="success-card">

            <!-- Success Icon -->

            <div class="success-icon-wrapper">

                <div class="success-circle">

                    <svg class="success-check"
                         viewBox="0 0 52 52">

                        <path
                            class="success-check-path"
                            d="M14 27 L22 35 L39 17">
                        </path>

                    </svg>

                </div>

            </div>


            <!-- Celebration -->

            <div class="celebration">
                <span>🎉</span>
                <span>✨</span>
                <span>🎊</span>
            </div>


            <!-- Heading -->

            <h1>
                Order Placed Successfully!
            </h1>


            <p class="success-message">
                Thank you for shopping with
                <strong>Fashion<span>Store</span></strong>!
            </p>


            <p class="success-submessage">
                Your order has been successfully placed.
                We will notify you once your order is shipped.
            </p>


            <!-- Order Information -->

            <div class="order-info">

                <div class="order-info-item">

                    <div class="info-icon">
                        📦
                    </div>

                    <div>
                        <span class="info-label">
                            Order Status
                        </span>

                        <strong class="status-success">
                            Confirmed
                        </strong>
                    </div>

                </div>


                <div class="order-info-item">

                    <div class="info-icon">
                        🚚
                    </div>

                    <div>
                        <span class="info-label">
                            Delivery
                        </span>

                        <strong>
                            Processing
                        </strong>
                    </div>

                </div>

            </div>


            <!-- Buttons -->

            <div class="success-buttons">

                <a href="<c:url value='/products' />"
                   class="success-btn primary-btn">

                    🛍️ Continue Shopping

                </a>


                <a href="<c:url value='/orders' />"
                   class="success-btn secondary-btn">

                    📋 View My Orders

                </a>

            </div>


            <!-- Bottom Message -->

            <div class="success-footer">

                <span class="heart">♥</span>

                <span>
                    We hope you love your purchase!
                </span>

            </div>

        </div>

    </main>


    <!-- FOOTER -->

    <jsp:include page="/WEB-INF/views/partials/footer.jsp" />


</body>
</html>