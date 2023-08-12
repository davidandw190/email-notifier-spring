package com.davidandw190.emailnotifier.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * Represents a user entity in the system.
 */
@Entity
@SuperBuilder
@Getter @Setter
@Table(name = "users")
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;
    private boolean isEnabled;

    public User() {}

    public User(Long id, String name, String email, String password, boolean isEnabled) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.isEnabled = isEnabled;
    }
}
