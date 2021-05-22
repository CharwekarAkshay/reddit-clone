package com.coldcoder.reddit.service;

import com.coldcoder.reddit.dto.RegisterRequest;
import com.coldcoder.reddit.model.NotificationEmail;
import com.coldcoder.reddit.model.User;
import com.coldcoder.reddit.model.VerificationToken;
import com.coldcoder.reddit.repository.UserRepository;
import com.coldcoder.reddit.repository.VerificationTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailService mailService;

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
                "http://localhost:8080/api/auth/accountVerficiation/" + token;

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

}
