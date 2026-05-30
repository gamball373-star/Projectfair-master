package com.example.demo.model;


import jakarta.persistence.*;
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
    private Long id;

    private String username;
    private String email;
    private Role role;

//    @Override
//public String toString() {
//    return "User{" +
//            "id=" + id +
//            ", username='" + username + '\'' +
//            ", email='" + email + '\'' +
//            ", role=" + role +
//            '}';
//}



}
