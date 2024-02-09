package com.avasthi.varahamihir.identityserver.services;

import com.avasthi.varahamihir.common.errors.ExceptionMessage;
import com.avasthi.varahamihir.common.exceptions.InvalidPasswordException;
import com.avasthi.varahamihir.identityserver.entities.User;
import com.avasthi.varahamihir.identityserver.pojo.AuthToken;
import com.avasthi.varahamihir.identityserver.pojo.Login;
import com.avasthi.varahamihir.identityserver.repositories.UserRepository;
import com.avasthi.varahamihir.identityserver.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AuthenticateService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public Optional<AuthToken> auth(Login login) {
        User user
                = userRepository
                .findByUsernameOrEmail(login.username())
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User %s not found", login.username())));
        if (passwordEncoder.matches(login.password(), user.getPassword())) {
            return Optional.of(generateTokens(user));
        }
        throw new InvalidPasswordException(String.format(ExceptionMessage.INVALID_PASSWORD.getReason(), login.username()),
                String.format(ExceptionMessage.INVALID_PASSWORD.getError(), user.getUsername()));
    }

    public Optional<AuthToken> refresh(User user) {
        return Optional.of(generateTokens(user));
    }
    private AuthToken generateTokens(User user) {
        String authToken = jwtService.generateAuthToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        return new AuthToken(user.getUsername(), authToken, refreshToken, user.getGrantedAuthorities(), user.getProfilePicture() == null ? Constants.defaultProfilePicture : user.getProfilePicture());
    }
}
