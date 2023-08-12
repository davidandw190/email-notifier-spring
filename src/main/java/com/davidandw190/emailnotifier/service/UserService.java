package com.davidandw190.emailnotifier.service;

import com.davidandw190.emailnotifier.domain.User;
import com.davidandw190.emailnotifier.dto.UserRequestDTO;
import org.springframework.stereotype.Service;

public interface UserService {

    User saveUser(UserRequestDTO request);

    Boolean verifyToken(String token);
}
