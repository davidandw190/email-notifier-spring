package com.davidandw190.emailnotifier.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Represents a confirmation entity for user registration.
 * Confirmations are used to verify user registrations and store related information.
 */
@Entity
@Getter @Setter
@Table(name = "confiramtions")
public class Confirmation {
    @Id @GeneratedValue
    private Long id;
    private String token;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdDate;

    @OneToOne(
            targetEntity = User.class,
            fetch = FetchType.EAGER
    )
    @JoinColumn(
            nullable = false,
            name = "user_id"
    )
    private User user;

    public Confirmation() {}

    public Confirmation(User user) {
        this.user =  user;
        this.createdDate = LocalDateTime.now();
        this.token = UUID.randomUUID().toString();

    }


}
