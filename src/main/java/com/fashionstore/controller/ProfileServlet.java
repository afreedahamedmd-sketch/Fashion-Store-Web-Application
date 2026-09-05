package com.fashionstore.controller;

import java.io.IOException;

import com.fashionstore.dao.UserDAO;
import com.fashionstore.dao.impl.UserDAOImpl;
import com.fashionstore.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDAOImpl();
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

        int userId =
                (Integer) session.getAttribute("userId");

        User user =
                userDAO.getUserById(userId);

        if (user == null) {

            session.invalidate();

            response.sendRedirect(
                    request.getContextPath() + "/login");

            return;
        }

        request.setAttribute("user", user);

        request.getRequestDispatcher(
                "/WEB-INF/views/profile.jsp")
                .forward(request, response);
    }
}