package sb.ecomm.auth;

import io.jsonwebtoken.JwtException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import sb.ecomm.email.EmailService;
import sb.ecomm.jwt.JwtTokenType;
import sb.ecomm.jwt.JwtUtils;
import sb.ecomm.order.Order;
import sb.ecomm.security.UserDetailsImpl;
import sb.ecomm.user.Role;
import sb.ecomm.user.User;
import sb.ecomm.user.UserRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    private String testFingerprint = "abcdefghijk123";

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthenticationService authenticationService;

    @Test
    void authenticateUser() {
    }

    @Test
    void reauthenticateUser() {
    }

    @Test
    void refreshUser() {
    }

    @Test
    void verifyUserEmail() {
    }

    @Test
    void requestPasswordReset() {
    }

    @Test
    void resetPassword() {
    }

    @Test
    void logoutUser_success() {
        User user = getTestUser();
        String testJWT = generateTestJWTToken(user);

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        ResponseEntity<HttpStatus> response = authenticationService.logoutUser(testJWT, this.testFingerprint);

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void logoutUserThrowsExceptionWhenWrongFingerprintIsGiven() {
        User user = getTestUser();
        String testJWT = generateTestJWTToken(user);

        assertThrows(JwtException.class, () -> authenticationService.logoutUser(testJWT, "wrongfingerprint"),
                "User is not authorised to perform to request");
    }

    private String generateTestJWTToken(User user) {
        UserDetailsImpl userDetails = new UserDetailsImpl();
        userDetails.setId(user.getId());
        userDetails.setUsername(user.getEmail());
        userDetails.setEnabled(user.isEnabled());
        userDetails.setAuthorities(getAuthorities(user));

        String hashedAccessTokenFingerprint = JwtUtils.hashJwtFingerprint(this.testFingerprint);

        return JwtUtils.generateJwtToken(userDetails, hashedAccessTokenFingerprint, JwtTokenType.ACCESS);
    }

    private User getTestUser() {
        UUID testUUID = UUID.randomUUID();
        Set<Order> orders = Collections.emptySet();
        User testUser = new User("username", "email@test.com", "password123", Role.CUSTOMER, "abcde12345", orders);
        testUser.setId(testUUID);
        return testUser;
    }

    private Collection<GrantedAuthority> getAuthorities(User user) {
        Set<GrantedAuthority> authorities = new HashSet<>();
        user.getRole().getAuthorities().forEach(authority -> authorities.add(new SimpleGrantedAuthority(authority.getAuthority())));
        return authorities;
    }
}