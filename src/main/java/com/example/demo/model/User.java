package com.example.demo.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;


@Entity
@Table(name = "Users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username", nullable = false, length = 50, unique = true)
    @NotBlank(message = "Имя пользователя не может быть пустым")
    @Size(min = 3, max = 50, message = "Имя пользователя должно быть от 3 до 50 символов")
    @Pattern(regexp = "^[a-zA-Z0-9_-]+$", message = "Имя пользователя может содержать только букву, цифры, дефис и подчеркивание")
    private String username;

    @Column(name = "email", nullable = false, length = 100, unique = true)
    @NotBlank(message = "Email не может быть пустым")
    @Email(message = "Email должен быть в корректном формате")
    @Size(min = 5, max = 100, message = "Email должен быть от 5 до 100 символов")
    private String email;

    @Column(name = "password", nullable = false, length = 255)
    @NotBlank(message = "Пароль не может быть пустым")
    @Size(min = 6, max = 255, message = "Пароль должен быть от 6 до 255 символов")
    private String password;

    @Column(name = "first_name", length = 50)
    @Size(max = 50, message = "Имя не должно превышать 50 символов")
    private String firstName;

    @Column(name = "last_name", length = 50)
    @Size(max = 50, message = "Фамилия не должна превышать 50 символов")
    private String lastName;

    @Column(name = "phone", length = 20)
    @Pattern(regexp = "^[+]?[0-9]{10,20}$", message = "Номер телефона должен содержать от 10 до 20 цифр и может начинаться с +")
    private String phone;

    @Column(name = "role", nullable = false, length = 50)
    @NotNull(message = "Роль не может быть пустой")
    @Enumerated(EnumType.STRING)
    private Role role;

}
