<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"
         isELIgnored="false" %>

<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>My Orders - FashionStore</title>

    <link rel="stylesheet"
          href="<c:url value='/assets/css/style.css' />">

    <link rel="stylesheet"
          href="<c:url value='/assets/css/orders.css' />">
</head>

<body>

    <!-- NAVBAR -->
    <jsp:include page="/WEB-INF/views/partials/navbar.jsp" />


    <main class="orders-page">

        <div class="container">

            <!-- PAGE HEADER -->
            <div class="orders-header">
                <div>
                    <h1>My Orders</h1>
                    <p>View and track all your FashionStore orders.</p>
                </div>

                <a href="<c:url value='/products' />"
                   class="btn btn-primary">
                    Continue Shopping
                </a>
            </div>


            <!-- ERROR MESSAGE -->
            <c:if test="${not empty errorMessage}">
                <div class="orders-error">
                    ${errorMessage}
                </div>
            </c:if>


            <!-- NO ORDERS -->
            <c:if test="${empty orders && empty errorMessage}">

                <div class="empty-orders">

                    <div class="empty-orders-icon">
                        📦
                    </div>

                    <h2>No Orders Yet</h2>

                    <p>
                        You haven't placed any orders yet.
                        Start shopping and your orders will appear here.
                    </p>

                    <a href="<c:url value='/products' />"
                       class="btn btn-primary">
                        Start Shopping
                    </a>

                </div>

            </c:if>


            <!-- ORDERS LIST -->
            <c:if test="${not empty orders}">

                <div class="orders-list">

                    <c:forEach var="order" items="${orders}">

                        <div class="order-card">

                            <!-- ORDER TOP -->
                            <div class="order-top">

                                <div class="order-number">
                                    <span>Order ID</span>
                                    <strong>#${order.orderId}</strong>
                                </div>

                                <div class="order-date">
                                    <span>Order Date</span>

                                    <strong>
                                        ${order.orderDate}
                                    </strong>
                                </div>

                                <div class="order-status">

                                    <span>Status</span>

                                    <span class="status-badge
                                        ${order.status == 'DELIVERED' ? 'status-delivered' :
                                          order.status == 'CANCELLED' ? 'status-cancelled' :
                                          order.status == 'SHIPPED' ? 'status-shipped' :
                                          'status-pending'}">

                                        ${order.status}

                                    </span>

                                </div>

                            </div>


                            <!-- ORDER DETAILS -->
                            <div class="order-details">

                                <div class="order-detail">

                                    <span class="detail-label">
                                        Total Amount
                                    </span>

                                    <strong class="order-total">
                                        ₹${order.totalAmount}
                                    </strong>

                                </div>


                                <div class="order-detail">

                                    <span class="detail-label">
                                        Payment Method
                                    </span>

                                    <strong>
                                        ${order.paymentMethod}
                                    </strong>

                                </div>


                                <div class="order-detail">

                                    <span class="detail-label">
                                        Delivery Address
                                    </span>

                                    <strong>
                                        ${order.shippingAddress}
                                    </strong>

                                    <small>
                                        ${order.city},
                                        ${order.state} -
                                        ${order.pincode}
                                    </small>

                                </div>

                            </div>


                            <!-- ORDER FOOTER -->
                            <div class="order-footer">

                                <span>
                                    Thank you for shopping with FashionStore.
                                </span>

                            </div>

                        </div>

                    </c:forEach>

                </div>

            </c:if>

        </div>

    </main>


    <!-- FOOTER -->
    <jsp:include page="/WEB-INF/views/partials/footer.jsp" />

</body>
</html>