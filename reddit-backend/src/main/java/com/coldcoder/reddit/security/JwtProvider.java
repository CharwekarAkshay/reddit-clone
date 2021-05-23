package com.coldcoder.reddit.security;

import com.coldcoder.reddit.exception.SpringRedditException;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;
import java.time.Instant;

import static java.util.Date.from;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class JwtProvider {

    private KeyStore keyStore;
//    @Value("${jwt.expiration.time}")
    private Long jwtExpirationInMillis = 100000l;

    @PostConstruct
    public void init() {
        try {
            keyStore = KeyStore.getInstance("JKS");
            InputStream resourceAsStream = getClass().getResourceAsStream("/spring-reddit.jks");
            keyStore.load(resourceAsStream, "password".toCharArray());
        } catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException exception) {
            throw new SpringRedditException("Exception occurred while loading keystore");
        }
    }

    public String generateToken(Authentication authentication) {
        org.springframework.security.core.userdetails.User principal =
                (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        return Jwts.builder()
                   .setSubject(principal.getUsername())
                   .setIssuedAt(from(Instant.now()))
                   .signWith(getPrivateKey())
                   .setExpiration(from(Instant.now().plusMillis(jwtExpirationInMillis)))
                   .compact();
    }

    private PrivateKey getPrivateKey() {
        try {
            return (PrivateKey) keyStore.getKey("mykey", "password".toCharArray());
        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
            throw new SpringRedditException("Exception occurred while retrieving public key from keystore");

        }
    }
}
