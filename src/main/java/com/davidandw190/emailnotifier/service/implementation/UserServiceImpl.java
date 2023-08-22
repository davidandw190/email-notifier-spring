package com.davidandw190.emailnotifier.service.implementation;

import com.davidandw190.emailnotifier.domain.Confirmation;
import com.davidandw190.emailnotifier.domain.User;
import com.davidandw190.emailnotifier.dto.UserRequestDTO;
import com.davidandw190.emailnotifier.repository.ConfirmationRepository;
import com.davidandw190.emailnotifier.repository.UserRepository;
import com.davidandw190.emailnotifier.service.EmailService;
import com.davidandw190.emailnotifier.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service implementation for managing user-related operations.
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ConfirmationRepository confirmationRepository;
    private final EmailService emailService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ConfirmationRepository confirmationRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.confirmationRepository = confirmationRepository;
        this.emailService = emailService;
    }

    /**
     * Registers a new user with the provided information, initially NOT ENABLING him, and creates
     * a confirmation object awaiting the token code confirmation for the user to activate his account
     *
     * @param request The user registration request containing user details.
     * @return The newly registered user.
     * @throws RuntimeException if a user with the same email already exists.
     */
    @Override
    @Transactional
    public User saveUser(UserRequestDTO request) {
        if (userRepository.existsByEmailIgnoreCase(request.getEmail())) {
            throw new RuntimeException(
                    String.format("A user with the email `%s` already exists!", request.getEmail())
            );
        }

        User newUser = createUserFromRequest(request);
        userRepository.save(newUser);

        Confirmation confirmation = new Confirmation(newUser);
        confirmationRepository.save(confirmation);

//        emailService.sendSimpleEmailMessage(
//                newUser.getName(),
//                newUser.getEmail(),
//                confirmation.getToken()
//        );
//
//        emailService.sendMessageWithAttachment(
//                newUser.getName(),
//                newUser.getEmail(),
//                confirmation.getToken()
//        );

        emailService.sendHtmlEmailWithEmbeddedFiles(
                newUser.getName(),
                newUser.getEmail(),
                confirmation.getToken()
        );

        return newUser;
    }

    /**
     * Verifies a user's registration token and enables the user account.
     *
     * @param token The token to verify.
     * @return true if the token is valid and the user account is enabled successfully.
     * @throws RuntimeException if the token is invalid or the associated user is not found.
     */
    @Override
    public Boolean verifyToken(String token) {
        Confirmation confirmation = confirmationRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid token!"));

        User user = getUserFromConfirmation(confirmation);
        user.setEnabled(true);
        userRepository.save(user);
        confirmationRepository.delete(confirmation);

        return Boolean.TRUE;
    }

    private User createUserFromRequest(UserRequestDTO request) {
        User newUser = new User();
        newUser.setName(request.getName());
        newUser.setEmail(request.getEmail());
        newUser.setPassword(request.getPassword());
        newUser.setEnabled(false);
        return newUser;
    }

    private User getUserFromConfirmation(Confirmation confirmation) {
        String email = confirmation.getUser().getEmail();
        return userRepository.findByEmailIgnoreCase(email).orElseThrow(
                () -> new RuntimeException(String.format("User with email `%s` not found!",email))
        );
    }
}
