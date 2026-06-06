package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
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

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    @Column(name = "is_verified", nullable = false)
    private boolean isVerified = false;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    @Column(name = "description", length = 500)
    @Size(max = 500, message = "Описание не должно превышать 500 символов")
    private String description;

    @Column(name = "profile_picture_url", length = 255)
    private String profilePictureUrl;

    @Column(name = "address", length = 255)
    @Size(max = 255, message = "Адрес не должен превышать 255 символов")
    private String address;

    @Column(name = "city", length = 50)
    @Size(max = 50, message = "Город не должен превышать 50 символов")
    private String city;

    @Column(name = "country", length = 50)
    @Size(max = 50, message = "Страна не должна превышать 50 символов")
    private String country;

    @Column(name = "postal_code", length = 20)
    @Pattern(regexp = "^[0-9]{5,10}$", message = "Почтовый индекс должен содержать от 5 до 10 цифр", allowEmpty = true)
    private String postalCode;

    // Constructor for creating user without ID
    public User(String username, String email, String password, Role role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.isActive = true;
        this.isVerified = false;
    }

    // Constructor for creating user with personal details
    public User(String username, String email, String password, String firstName, String lastName, Role role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.isActive = true;
        this.isVerified = false;
    }

    // JPA lifecycle callback - automatically set createdAt and updatedAt
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", role=" + role +
                ", isActive=" + isActive +
                ", isVerified=" + isVerified +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
