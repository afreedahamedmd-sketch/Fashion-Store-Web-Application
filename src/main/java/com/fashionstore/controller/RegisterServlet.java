package com.fashionstore.controller;

import com.fashionstore.util.PasswordUtility;
import java.io.IOException;

import com.fashionstore.dao.UserDAO;
import com.fashionstore.dao.impl.UserDAOImpl;
import com.fashionstore.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String userName = request.getParameter("userName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String city = request.getParameter("city");
        String state = request.getParameter("state");
        String pincode = request.getParameter("pincode");

        if (userName == null || email == null || password == null || phone == null ||
            address == null || city == null || state == null || pincode == null ||
            userName.trim().isEmpty() || email.trim().isEmpty() || password.trim().isEmpty() ||
            phone.trim().isEmpty() || address.trim().isEmpty() || city.trim().isEmpty() ||
            state.trim().isEmpty() || pincode.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Please fill in all fields.");
            request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
            return;
        }

        User existingUser = userDAO.getUserByEmail(email.trim());

        if (existingUser != null) {
            request.setAttribute("errorMessage", "An account with this email already exists.");
            request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
            return;
        }

        User user = new User();
        user.setUserName(userName.trim());
        user.setEmail(email.trim());
        user.setPassword(PasswordUtility.hashPassword(password));
        user.setPhone(phone.trim());
        user.setAddress(address.trim());
        user.setCity(city.trim());
        user.setState(state.trim());
        user.setPincode(pincode.trim());

        if (userDAO.addUser(user)) {
            response.sendRedirect(request.getContextPath() + "/login?registered=true");
        } else {
            request.setAttribute("errorMessage", "Registration failed. Please try again.");
            request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
        }
    }
}