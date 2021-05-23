package com.coldcoder.reddit.controller;

import com.coldcoder.reddit.dto.AuthenticationResponse;
import com.coldcoder.reddit.dto.LoginRequest;
import com.coldcoder.reddit.dto.RegisterRequest;
import com.coldcoder.reddit.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest) {
        authService.signup(registerRequest);
        return new ResponseEntity<String>("User Registration Successful", HttpStatus.CREATED);
    }

    @GetMapping("/accountVerification/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable String token) {
        authService.verifyAccount(token);
        return new ResponseEntity<String>("Account Activated Successfully", HttpStatus.ACCEPTED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginRequest loginRequest) {
        AuthenticationResponse response = authService.login(loginRequest);
        return new ResponseEntity<AuthenticationResponse>(response, HttpStatus.OK);
    }
}
