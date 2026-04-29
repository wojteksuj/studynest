package org.example.studynest.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
    }

    @Test
    void generateToken_returnsNonNullToken() {
        String token = jwtService.generateToken("alice@example.com", "alice");

        assertNotNull(token);
        assertFalse(token.isBlank());
    }

    @Test
    void extractEmail_returnsCorrectEmail() {
        String email = "alice@example.com";
        String token = jwtService.generateToken(email, "alice");

        assertEquals(email, jwtService.extractEmail(token));
    }

    @Test
    void extractEmail_returnsCorrectEmail_forDifferentUsers() {
        String token1 = jwtService.generateToken("alice@example.com", "alice");
        String token2 = jwtService.generateToken("bob@example.com", "bob");

        assertEquals("alice@example.com", jwtService.extractEmail(token1));
        assertEquals("bob@example.com", jwtService.extractEmail(token2));
    }

    @Test
    void extractEmail_throws_forTamperedToken() {
        String token = jwtService.generateToken("alice@example.com", "alice");
        String tampered = token.substring(0, token.length() - 5) + "XXXXX";

        assertThrows(Exception.class, () -> jwtService.extractEmail(tampered));
    }
}
