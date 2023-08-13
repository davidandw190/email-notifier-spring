package com.davidandw190.emailnotifier.resource;

import com.davidandw190.emailnotifier.domain.HttpResponse;
import com.davidandw190.emailnotifier.domain.User;
import com.davidandw190.emailnotifier.dto.UserRequestDTO;
import com.davidandw190.emailnotifier.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * Represents a REST-ful resource for managing user-related operations.
 */
@RestController
@RequestMapping("/api/users")
public class UserResource {

    private final UserService userService;

    @Autowired
    public UserResource(UserService userService) {
        this.userService = userService;
    }

    /**
     * Creates a new user based on the provided user request data.
     *
     * @param userRequest The UserRequestDTO containing user details for creation.
     * @return A ResponseEntity containing an HTTP response with user creation status.
     */
    @PostMapping
    public ResponseEntity<HttpResponse> createUser(@RequestBody UserRequestDTO userRequest) {
        User newUser = userService.saveUser(userRequest);
        return ResponseEntity.created(URI.create("")).body(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .data(Map.of("user", newUser))
                        .message("User Created Successfully!")
                        .status(HttpStatus.CREATED)
                        .statusCode(HttpStatus.CREATED.value())
                        .build()
        );
    }

    /**
     * Validates a new user account using the provided verification token.
     *
     * @param token The verification token associated with the user's account.
     * @return A ResponseEntity containing an HTTP response with account validation status.
     */
    @GetMapping
    public ResponseEntity<HttpResponse> confirmNewUserAccount(@RequestParam("token") String token) {
        Boolean isSuccess = userService.verifyToken(token);
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .data(Map.of("Success", isSuccess))
                        .message((isSuccess == Boolean.TRUE)
                                ? "Account validated successfully!"
                                : "Account could not be validated!"
                        )
                        .status(HttpStatus.ACCEPTED)
                        .statusCode(HttpStatus.ACCEPTED.value())
                        .build()
        );
    }
}
