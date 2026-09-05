package com.fashionstore.dao.impl;

import com.fashionstore.dao.UserDAO;
import com.fashionstore.model.User;
import com.fashionstore.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {

    // SQL Queries

    private static final String ADD_USER =
            "INSERT INTO users "
            + "(userName, email, password, phone, address, city, state, pincode) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String GET_USER_BY_ID =
            "SELECT * FROM users WHERE userId = ?";

    private static final String GET_USER_BY_EMAIL =
            "SELECT * FROM users WHERE email = ?";

    private static final String GET_ALL_USERS =
            "SELECT * FROM users";

    private static final String UPDATE_USER =
            "UPDATE users SET userName = ?, email = ?, password = ?, "
            + "phone = ?, address = ?, city = ?, state = ?, pincode = ? "
            + "WHERE userId = ?";

    private static final String DELETE_USER =
            "DELETE FROM users WHERE userId = ?";


    @Override
    public boolean addUser(User user) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(ADD_USER)) {

            statement.setString(1, user.getUserName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getPhone());
            statement.setString(5, user.getAddress());
            statement.setString(6, user.getCity());
            statement.setString(7, user.getState());
            statement.setString(8, user.getPincode());

            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


    @Override
    public User getUserById(int userId) {

        User user = null;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(GET_USER_BY_ID)) {

            statement.setInt(1, userId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                user = getUserFromResultSet(resultSet);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }


    @Override
    public User getUserByEmail(String email) {

        User user = null;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(GET_USER_BY_EMAIL)) {

            statement.setString(1, email);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                user = getUserFromResultSet(resultSet);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }


    @Override
    public List<User> getAllUsers() {

        List<User> users = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(GET_ALL_USERS);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                users.add(getUserFromResultSet(resultSet));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return users;
    }


    @Override
    public boolean updateUser(User user) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(UPDATE_USER)) {

            statement.setString(1, user.getUserName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getPhone());
            statement.setString(5, user.getAddress());
            statement.setString(6, user.getCity());
            statement.setString(7, user.getState());
            statement.setString(8, user.getPincode());
            statement.setInt(9, user.getUserId());

            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


    @Override
    public boolean deleteUser(int userId) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(DELETE_USER)) {

            statement.setInt(1, userId);

            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


    // Convert ResultSet into User object

    private User getUserFromResultSet(ResultSet resultSet) throws Exception {

        User user = new User();

        user.setUserId(resultSet.getInt("userId"));
        user.setUserName(resultSet.getString("userName"));
        user.setEmail(resultSet.getString("email"));
        user.setPassword(resultSet.getString("password"));
        user.setPhone(resultSet.getString("phone"));
        user.setAddress(resultSet.getString("address"));
        user.setCity(resultSet.getString("city"));
        user.setState(resultSet.getString("state"));
        user.setPincode(resultSet.getString("pincode"));

        if (resultSet.getTimestamp("createdDate") != null) {
            user.setCreatedDate(
                    resultSet.getTimestamp("createdDate").toLocalDateTime()
            );
        }

        if (resultSet.getTimestamp("lastLoginDate") != null) {
            user.setLastLoginDate(
                    resultSet.getTimestamp("lastLoginDate").toLocalDateTime()
            );
        }

        return user;
    }
}