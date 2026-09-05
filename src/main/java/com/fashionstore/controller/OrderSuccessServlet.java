package com.fashionstore.controller;

import java.io.IOException;

import com.fashionstore.dao.OrderDAO;
import com.fashionstore.dao.impl.OrderDAOImpl;
import com.fashionstore.model.Order;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/order-success")
public class OrderSuccessServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private OrderDAO orderDAO;

    @Override
    public void init() throws ServletException {
        orderDAO = new OrderDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null ||
            session.getAttribute("userId") == null) {

            response.sendRedirect(
                    request.getContextPath() + "/login");

            return;
        }

        int userId = (Integer) session.getAttribute("userId");

        String orderIdParameter =
                request.getParameter("orderId");

        if (orderIdParameter == null ||
            orderIdParameter.trim().isEmpty()) {

            response.sendRedirect(
                    request.getContextPath() + "/home");

            return;
        }

        int orderId;

        try {

            orderId = Integer.parseInt(
                    orderIdParameter);

        } catch (NumberFormatException e) {

            response.sendRedirect(
                    request.getContextPath() + "/home");

            return;
        }

        Order order =
                orderDAO.getOrderById(orderId);

        if (order == null ||
            order.getUserId() != userId) {

            response.sendRedirect(
                    request.getContextPath() + "/home");

            return;
        }

        request.setAttribute(
                "order",
                order);

        request.getRequestDispatcher(
                "/WEB-INF/views/order-success.jsp")
                .forward(request, response);
    }
}