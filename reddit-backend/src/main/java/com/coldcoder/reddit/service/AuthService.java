package com.coldcoder.reddit.service;

import com.coldcoder.reddit.dto.AuthenticationResponse;
import com.coldcoder.reddit.dto.LoginRequest;
import com.coldcoder.reddit.dto.RegisterRequest;
import com.coldcoder.reddit.exception.SpringRedditException;
import com.coldcoder.reddit.model.NotificationEmail;
import com.coldcoder.reddit.model.User;
import com.coldcoder.reddit.model.VerificationToken;
import com.coldcoder.reddit.repository.UserRepository;
import com.coldcoder.reddit.repository.VerificationTokenRepository;
import com.coldcoder.reddit.security.JwtProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    @Transactional
    public void signup(RegisterRequest registerRequest) {
        User user = new User();

        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setCreated(Instant.now());
        user.setEnabled(false);

        userRepository.save(user);

        String token = generateVerificationToken(user);
        String body = "Thank you for signing up to Spring Reddit," +
                "please click on link below to activate your account :" +
                "http://localhost:8080/api/auth/accountVerification/" + token;

        mailService.sendMail(
                new NotificationEmail(
                        "Please Activate your account",
                        user.getEmail(),
                        body
                )
                            );
    }

    private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();

        verificationToken.setToken(token);
        verificationToken.setUser(user);

        verificationTokenRepository.save(verificationToken);
        return token;
    }

    public void verifyAccount(String token) {
        Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
        verificationToken.orElseThrow(() -> new SpringRedditException("Invalid Token"));
        fetchUserAndEnable(verificationToken.get());
    }

    @Transactional
    public void fetchUserAndEnable(VerificationToken verificationToken) {
        String username = verificationToken.getUser().getUsername();
        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new SpringRedditException("User not " +
                                                                     "found " +
                                                                     "with" +
                                                                     " name :" + username));
        user.setEnabled(true);
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User getCurrentUser() {
        org.springframework.security.core.userdetails.User principal =
                (org.springframework.security.core.userdetails.User) SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getPrincipal();
        return userRepository.findByUsername(principal.getUsername())
                             .orElseThrow(() -> new UsernameNotFoundException("Username not found - " + principal.getUsername()));
    }

    public AuthenticationResponse login(LoginRequest loginRequest) {
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                                                                                           loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.generateToken(authentication);
        return new AuthenticationResponse(token, loginRequest.getUsername());
    }
}
