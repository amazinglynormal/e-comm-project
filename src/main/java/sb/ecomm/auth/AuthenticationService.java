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

    @Autowired
    public AuthenticationService(AuthenticationManager authenticationManager, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
    }

    ResponseEntity<?> authenticateUser(AuthenticationRequest authenticationRequest) {
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
        Jws<Claims> claims = JwtUtils.parseJwtTokenClaims(refreshToken);
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
        String hashedAccessTokenFingerprint =
                Hashing.sha256().hashString(accessTokenFingerprint,
                        StandardCharsets.UTF_8).toString();

        String accessToken = JwtUtils.generateJwtToken(userDetails, hashedAccessTokenFingerprint,
                JwtTokenType.ACCESS);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);

        return new ResponseEntity<>(headers, HttpStatus.OK);

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

    ResponseEntity<?> successfulAuthentication(Authentication authResult) {
        UserDetailsImpl user = (UserDetailsImpl) authResult.getPrincipal();

        String accessTokenFingerprint = JwtUtils.generateRandomStringForJwtFingerprint();
        String hashedAccessTokenFingerprint =
                Hashing.sha256().hashString(accessTokenFingerprint,
                        StandardCharsets.UTF_8).toString();

        String refreshTokenFingerprint =
                JwtUtils.generateRandomStringForJwtFingerprint();
        String hashedRefreshTokenFingerprint =
                Hashing.sha256().hashString(refreshTokenFingerprint,
                        StandardCharsets.UTF_8).toString();


        String accessToken = JwtUtils.generateJwtToken(user, hashedAccessTokenFingerprint,
                JwtTokenType.ACCESS);

        String refreshToken = JwtUtils.generateJwtToken(user, hashedRefreshTokenFingerprint,
                JwtTokenType.REFRESH);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        addRefreshTokenCookieToHeader(headers, refreshToken);
        addTokenFingerprintCookieToHeader(headers, "access", accessTokenFingerprint);
        addTokenFingerprintCookieToHeader(headers, "refresh", refreshTokenFingerprint);

        persistRefreshToken(refreshToken, user.getId());

        return new ResponseEntity<>(headers, HttpStatus.OK);
    }

    private void addRefreshTokenCookieToHeader(HttpHeaders headers, String refreshToken) {
        long time =  86400;
        String path = "/api/v1/auth/refresh; ";

        String cookieExpires = getCookieExpiresString(time);

        headers.add("Set-cookie", "_Secure-refresh=" + refreshToken + "; " +
                        "Max-Age=86400; " +
                        "Secure; " +
                        "SameSite=Strict; " +
                        "Path=" + path + cookieExpires);
    }

    private void addTokenFingerprintCookieToHeader(HttpHeaders headers,
                                                   String token,
                                                   String fingerprint) {

        long time = token.equals("refresh") ? 86400 : 900;
        String cookiePrefix = token.equals("refresh") ? "_Secure" : "_Host";
        String path = token.equals("refresh") ? "/api/v1/auth/refresh; " :
                "/; ";

        String cookieExpires = getCookieExpiresString(time);

        headers.add("Set-cookie",
                cookiePrefix + "-fingerprint=" + fingerprint + "; " +
                        "Max-Age=86400; " +
//                        "Secure; " +
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
