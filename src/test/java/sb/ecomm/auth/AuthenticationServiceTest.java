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
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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

    @Mock
    private EmailService emailService;

    @InjectMocks
    private AuthenticationService authenticationService;

    @Test
    void authenticateUser_success() {
        User user = getTestUser();
        UserDetailsImpl userDetails = getUserDetailsImpl(user);
        AuthenticationRequest authRequest = new AuthenticationRequest(user.getEmail(), user.getPassword());
        TestingAuthenticationToken auth = new TestingAuthenticationToken(userDetails, new ArrayList<>());

        when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authRequest.getEmail(),
                authRequest.getPassword(),
                new ArrayList<>()
        ))).thenReturn(auth);

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);


        ResponseEntity<LoginResponse> response = authenticationService.authenticateUser(authRequest);

        assertEquals(200, response.getStatusCodeValue());

        String header = response.getHeaders().getFirst("Authorization");

        assertNotNull(header);

        String responseBody = response.getBody().getRefreshToken();

        assertNotNull(responseBody);
    }

    @Test
    void authenticateUserThrowsAuthExceptionWhenAuthenticationFails() {
        User user = getTestUser();
        AuthenticationRequest authRequest = new AuthenticationRequest(user.getEmail(), user.getPassword());

        when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authRequest.getEmail(),
                authRequest.getPassword(),
                new ArrayList<>()
        ))).thenThrow(new RuntimeException("Hit an auth Exception"));

        assertThrows(RuntimeException.class, () -> authenticationService.authenticateUser(authRequest));
    }

    @Test
    void reauthenticateUser() {
    }

    @Test
    void refreshUser() {
    }

    @Test
    void verifyUserEmail_success() {
        User user = getTestUser();
        when(userRepository.findByVerificationHash(user.getVerificationHash())).thenReturn(Optional.of(user));

        ResponseEntity<HttpStatus> response = authenticationService.verifyUserEmail(user.getVerificationHash());

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void verifyUserEmailReturns400BadRequestWhenUserNotFoundInDB() {
        String verificationHash = "abcdefg";
        when(userRepository.findByVerificationHash(verificationHash)).thenReturn(Optional.empty());

        ResponseEntity<HttpStatus> response = authenticationService.verifyUserEmail(verificationHash);

        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    void requestPasswordReset_success() {
        User user = getTestUser();
        user.setEnabled(true);
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        ResponseEntity<HttpStatus> response = authenticationService.requestPasswordReset(user.getEmail());

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void requestPasswordResetThrowsExceptionWhenUserEmailNotFoundInDB() {
        String testEmail = "test@test.com";
        when(userRepository.findByEmail(testEmail)).thenThrow(new RuntimeException("User not found"));

        assertThrows(RuntimeException.class, () -> authenticationService.requestPasswordReset(testEmail));
    }

    @Test
    void resetPassword_success() {
        User user = getTestUser();
        String testJWT = generateTestJWTToken(user, JwtTokenType.RESET);
        user.setPasswordResetToken(testJWT);

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        ResetPasswordDto resetPasswordDto = new ResetPasswordDto();
        resetPasswordDto.setNewPassword("abcdefg12");
        resetPasswordDto.setResetToken(testJWT);

        ResponseEntity<HttpStatus> response = authenticationService.resetPassword(resetPasswordDto);

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void resetPasswordReturns403ForbiddenStatusWhenRefreshTokenDoesNotMatchStoredToken() {
        User user = getTestUser();
        String testJWT = generateTestJWTToken(user, JwtTokenType.RESET);

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        ResetPasswordDto resetPasswordDto = new ResetPasswordDto();
        resetPasswordDto.setNewPassword("abcdefg12");
        resetPasswordDto.setResetToken(testJWT);

        ResponseEntity<HttpStatus> response = authenticationService.resetPassword(resetPasswordDto);

        assertEquals(403, response.getStatusCodeValue());
    }

    @Test
    void logoutUser_success() {
        User user = getTestUser();
        String testJWT = generateTestJWTToken(user, JwtTokenType.ACCESS);

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        ResponseEntity<HttpStatus> response = authenticationService.logoutUser(testJWT, this.testFingerprint);

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void logoutUserThrowsExceptionWhenWrongFingerprintIsGiven() {
        User user = getTestUser();
        String testJWT = generateTestJWTToken(user, JwtTokenType.ACCESS);

        assertThrows(JwtException.class, () -> authenticationService.logoutUser(testJWT, "wrongfingerprint"),
                "User is not authorised to perform to request");
    }

    private String generateTestJWTToken(User user, JwtTokenType tokenType) {
        UserDetailsImpl userDetails = getUserDetailsImpl(user);

        String hashedAccessTokenFingerprint = JwtUtils.hashJwtFingerprint(this.testFingerprint);

        return JwtUtils.generateJwtToken(userDetails, hashedAccessTokenFingerprint, tokenType);
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

    private UserDetailsImpl getUserDetailsImpl(User user) {
        UserDetailsImpl userDetails = new UserDetailsImpl();
        userDetails.setId(user.getId());
        userDetails.setUsername(user.getEmail());
        userDetails.setEnabled(user.isEnabled());
        userDetails.setAuthorities(getAuthorities(user));

        return userDetails;

    }
}