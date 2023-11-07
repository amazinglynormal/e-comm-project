package sb.ecomm.auth;

import com.google.common.hash.Hashing;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import sb.ecomm.email.EmailService;
import sb.ecomm.exceptions.UserNotFoundException;
import sb.ecomm.jwt.JwtTokenType;
import sb.ecomm.jwt.JwtUtils;
import sb.ecomm.security.UserDetailsImpl;
import sb.ecomm.user.User;
import sb.ecomm.user.UserRepository;

import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final EmailService emailService;

    @Autowired
    public AuthenticationService(AuthenticationManager authenticationManager, UserRepository userRepository, EmailService emailService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    ResponseEntity<LoginResponse> authenticateUser(AuthenticationRequest authenticationRequest) {
        Authentication authentication =
                attemptAuthentication(authenticationRequest);

        return successfulAuthentication(authentication);
    }

    ResponseEntity<HttpStatus> reauthenticateUser(AuthenticationRequest authenticationRequest,
                                                  UUID userId) {
        Authentication authentication = attemptAuthentication(authenticationRequest);

        UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();

        if (!authentication.isAuthenticated() ||
                !isCorrectUser(user.getId(), userId)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    ResponseEntity<?> refreshUser(String refreshToken, String fingerprint) {
        Jws<Claims> claims = JwtUtils.parseJwtTokenClaims(refreshToken, fingerprint);
        String userContext = (String) claims.getBody().get("user_context");
        String hashedFingerprint = Hashing.sha256().hashString(fingerprint,
                StandardCharsets.UTF_8).toString();

        if (!hashedFingerprint.equals(userContext)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        UUID userId = UUID.fromString(claims.getBody().getSubject());

        User user =
                userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

        String storedRefreshToken = user.getRefreshToken();

        if (storedRefreshToken == null || !storedRefreshToken.equals(refreshToken)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        UserDetailsImpl userDetails = new UserDetailsImpl();
        userDetails.setId(user.getId());
        userDetails.setUsername(user.getEmail());
        userDetails.setEnabled(user.isEnabled());
        userDetails.setAuthorities(getAuthorities(user));

        String accessTokenFingerprint = JwtUtils.generateRandomStringForJwtFingerprint();
        String hashedAccessTokenFingerprint = JwtUtils.hashJwtFingerprint(accessTokenFingerprint);

        String accessToken = JwtUtils.generateJwtToken(userDetails, hashedAccessTokenFingerprint,
                JwtTokenType.ACCESS);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);

        addTokenFingerprintCookieToHeader(headers, JwtTokenType.ACCESS, accessTokenFingerprint);

        return new ResponseEntity<>(headers, HttpStatus.OK);

    }

    ResponseEntity<HttpStatus> verifyUserEmail(String verificationHash) {
        Optional<User> user = userRepository.findByVerificationHash(verificationHash);

        if (user.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        user.get().setEnabled(true);
        user.get().setVerificationHash(null);

        userRepository.save(user.get());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    ResponseEntity<HttpStatus> requestPasswordReset(String suppliedEmail) {
        User user = userRepository.findByEmail(suppliedEmail).orElseThrow(() -> new RuntimeException("User not found"));
        String randomString = JwtUtils.generateRandomStringForJwtFingerprint();
        String hashedString = JwtUtils.hashJwtFingerprint(randomString);

        UserDetailsImpl userDetails = new UserDetailsImpl();
        userDetails.setId(user.getId());
        userDetails.setUsername(user.getEmail());
        userDetails.setEnabled(user.isEnabled());
        userDetails.setAuthorities(getAuthorities(user));

        String resetToken = JwtUtils.generateJwtToken(userDetails, hashedString, JwtTokenType.RESET);

        user.setPasswordResetToken(resetToken);

        emailService.sendPasswordResetEmail(user.getUsername(), user.getEmail(), resetToken);

        userRepository.save(user);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    ResponseEntity<HttpStatus> resetPassword(ResetPasswordDto resetPasswordDto) {
        Jws<Claims> claims = JwtUtils.parseJwtResetToken(resetPasswordDto.getResetToken());

        UUID userId = UUID.fromString(claims.getBody().getSubject());

        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

        String storedPasswordResetToken = user.getPasswordResetToken();

        if (storedPasswordResetToken == null || !storedPasswordResetToken.equals(resetPasswordDto.getResetToken())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        user.setPassword(resetPasswordDto.getNewPassword());
        user.setPasswordResetToken(null);

        emailService.sendPasswordChangedEmail(user.getUsername(), user.getEmail());

        userRepository.save(user);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    ResponseEntity<HttpStatus> logoutUser(String accessToken, String fingerprint) {
        Jws<Claims> claims = JwtUtils.parseJwtTokenClaims(accessToken, fingerprint);

        UUID userId = UUID.fromString(claims.getBody().getSubject());

        User user =
                userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

        user.setRefreshToken(null);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private Authentication attemptAuthentication(AuthenticationRequest authenticationRequest) throws AuthenticationException {
        try {
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getEmail(),
                            authenticationRequest.getPassword(),
                            new ArrayList<>()
                    )
            );
        } catch (AuthenticationException ex) {
            throw new RuntimeException("Hit an auth exception");
        }
    }

    private ResponseEntity<LoginResponse> successfulAuthentication(Authentication authResult) {
        UserDetailsImpl user = (UserDetailsImpl) authResult.getPrincipal();

        String accessTokenFingerprint = JwtUtils.generateRandomStringForJwtFingerprint();
        String hashedAccessTokenFingerprint = JwtUtils.hashJwtFingerprint(accessTokenFingerprint);

        String refreshTokenFingerprint =
                JwtUtils.generateRandomStringForJwtFingerprint();
        String hashedRefreshTokenFingerprint =
                JwtUtils.hashJwtFingerprint(refreshTokenFingerprint);


        String accessToken = JwtUtils.generateJwtToken(user, hashedAccessTokenFingerprint,
                JwtTokenType.ACCESS);

        String refreshToken = JwtUtils.generateJwtToken(user, hashedRefreshTokenFingerprint,
                JwtTokenType.REFRESH);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        addTokenFingerprintCookieToHeader(headers, JwtTokenType.ACCESS, accessTokenFingerprint);
        addTokenFingerprintCookieToHeader(headers, JwtTokenType.REFRESH, refreshTokenFingerprint);

        persistRefreshToken(refreshToken, user.getId());

        LoginResponse loginResponse = new LoginResponse(refreshToken);

        return new ResponseEntity<>(loginResponse, headers, HttpStatus.OK);
    }

    private void addTokenFingerprintCookieToHeader(HttpHeaders headers,
                                                   JwtTokenType tokenType,
                                                   String fingerprint) {

        long time = tokenType == JwtTokenType.REFRESH ? 86400 : 900;
        String cookiePrefix = tokenType == JwtTokenType.REFRESH ? "_Secure" : "_Host";
        String path = tokenType == JwtTokenType.REFRESH ? "/api/v1/auth/refresh; " :
                "/; ";

        String cookieExpires = getCookieExpiresString(time);

        headers.add("Set-cookie",
                cookiePrefix + "-fingerprint=" + fingerprint + "; " +
                        "Max-Age=86400; " +
                        "Secure; " +
                        "HttpOnly; SameSite=Strict; " +
                        "Path=" + path + cookieExpires);
    }

    private String getCookieExpiresString(long time) {
        Date expiresDate = new Date();
        expiresDate.setTime(expiresDate.getTime() + (time * 1000));
        DateFormat gmtFormat = new SimpleDateFormat("EEE, dd-MMM-yyyy " +
                "HH:mm:ss zzz", Locale.UK);
        gmtFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        return "expires=" + gmtFormat.format(expiresDate);
    }

    private void persistRefreshToken(String refreshToken, UUID userId) {
        User user =
                userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

        user.setRefreshToken(refreshToken);
        userRepository.save(user);
    }

    private boolean isCorrectUser(UUID authenticatedId, UUID suppliedId) {
        return authenticatedId.equals(suppliedId);
    }

    private Collection<GrantedAuthority> getAuthorities(User user) {
        Set<GrantedAuthority> authorities = new HashSet<>();
        user.getRole().getAuthorities().forEach(authority -> authorities.add(new SimpleGrantedAuthority(authority.getAuthority())));
        return authorities;
    }
}
