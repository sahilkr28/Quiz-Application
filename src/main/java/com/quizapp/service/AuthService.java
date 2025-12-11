package com.quizapp.service;

import com.quizapp.dao.UserDAO;
import com.quizapp.model.User;
import com.quizapp.util.PasswordUtils;

/**
 * Handles authentication logic such as login and registration.
 */
public class AuthService {

    private final UserDAO userDAO = new UserDAO();

    public boolean register(String username, String password, String role) {
        if (userDAO.getUserByUsername(username) != null) {
            return false; // user already exists
        }

        String hashed = PasswordUtils.hashPassword(password);
        User user = new User(username, hashed, role);
        return userDAO.addUser(user);
    }

    public User login(String username, String password) {
        User user = userDAO.getUserByUsername(username);
        if (user == null) return null;

        boolean match = PasswordUtils.verifyPassword(password, user.getPasswordHash());
        return match ? user : null;
    }
}
