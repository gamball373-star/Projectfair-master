package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.model.Role;
import com.example.demo.service.UserService;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public String registerUser(String username, String email, Role role) {
        try {
            User user = new User(null, username, email, role);
            userService.registerUser(user);
            return "Пользователь '" + username + "' успешно зарегистрирован!";
        } catch (IllegalArgumentException e) {
            return "Ошибка при регистрации: " + e.getMessage();
        }
    }

    public List<User> viewAllUsers() {
        return userService.getAllUsers();
    }

    public String viewUserById(Long userId) {
        try {
            User user = userService.getUserById(userId);
            return "ID: " + user.getId() + 
                   ", Имя: " + user.getUsername() + 
                   ", Email: " + user.getEmail() + 
                   ", Роль: " + user.getRole();
        } catch (IllegalArgumentException e) {
            return "Ошибка: " + e.getMessage();
        }
    }

    public String updateUser(Long userId, String username, String email) {
        try {
            User user = userService.getUserById(userId);
            user.setUsername(username);
            user.setEmail(email);
            userService.updateUser(user);
            return "Пользователь успешно обновлен!";
        } catch (IllegalArgumentException e) {
            return "Ошибка при обновлении: " + e.getMessage();
        }
    }

    public String deleteUser(Long userId) {
        try {
            userService.deleteUser(userId);
            return "Пользователь с ID " + userId + " успешно удален!";
        } catch (IllegalArgumentException e) {
            return "Ошибка при удалении: " + e.getMessage();
        }
    }

    public String findUserByUsername(String username) {
        try {
            User user = userService.getUserByUsername(username);
            return "ID: " + user.getId() + 
                   ", Имя: " + user.getUsername() + 
                   ", Email: " + user.getEmail() + 
                   ", Роль: " + user.getRole();
        } catch (IllegalArgumentException e) {
            return "Ошибка: " + e.getMessage();
        }
    }
}
}
