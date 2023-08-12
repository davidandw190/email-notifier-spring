package com.davidandw190.emailnotifier.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter @Setter
public class UserRequestDTO {
    @NonNull
    private String name;
    @NonNull
    private String email;
    @NonNull
    private String password;

    public UserRequestDTO(@NonNull String name,
                          @NonNull String email,
                          @NonNull String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
