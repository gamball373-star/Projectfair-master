package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void registerUser(User user) {
        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            throw new IllegalArgumentException("Имя пользователя не может быть пустым!");
        }
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email не может быть пустым!");
        }
        userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь с ID " + userId + " не найден!"));
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь с именем " + username + " не найден!"));
    }

    public void updateUser(User user) {
        if (!userRepository.findById(user.getId()).isPresent()) {
            throw new IllegalArgumentException("Пользователь с ID " + user.getId() + " не найден!");
        }
        userRepository.save(user);
    }

    public void deleteUser(Long userId) {
        if (!userRepository.findById(userId).isPresent()) {
            throw new IllegalArgumentException("Пользователь с ID " + userId + " не найден!");
        }
        userRepository.deleteById(userId);
    }
}
    }
}
