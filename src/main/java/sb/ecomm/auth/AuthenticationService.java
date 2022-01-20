package sb.ecomm.auth;

import com.google.common.hash.Hashing;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.apache.commons.rng.UniformRandomProvider;
import org.apache.commons.rng.simple.RandomSource;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import sb.ecomm.constants.TempSecurityConstants;
import sb.ecomm.exceptions.UserNotFoundException;
import sb.ecomm.security.UserDetailsImpl;
import sb.ecomm.user.User;
import sb.ecomm.user.UserRepository;

import java.nio.charset.StandardCharsets;
import java.security.Key;
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
        List<String> authorities = new ArrayList<>();
        user.getAuthorities().forEach(authority -> authorities.add(authority.getAuthority()));
        Date exp = new Date(System.currentTimeMillis() + 1000L*60*30);
        Key key = Keys.hmacShaKeyFor(TempSecurityConstants.jwtKey.getBytes());

        String accessTokenFingerprint = generateRandomStringForJwtFingerprint();
        String hashedAccessTokenFingerprint =
                Hashing.sha256().hashString(accessTokenFingerprint,
                        StandardCharsets.UTF_8).toString();

        String refreshTokenFingerprint =
                generateRandomStringForJwtFingerprint();
        String hashedRefreshTokenFingerprint =
                Hashing.sha256().hashString(refreshTokenFingerprint,
                        StandardCharsets.UTF_8).toString();


        String accessToken =
                Jwts.builder()
                        .claim("email", user.getUsername())
                        .claim("authorities", authorities)
                        .claim("isEnabled", user.isEnabled())
                        .claim("user_context", hashedAccessTokenFingerprint)
                        .setSubject(user.getId().toString()).signWith(key,
                                SignatureAlgorithm.HS512).setExpiration(exp).compact();

        String refreshToken =
                Jwts.builder()
                        .claim("user_context", hashedRefreshTokenFingerprint)
                        .setSubject(user.getId().toString()).signWith(key,
                                SignatureAlgorithm.HS512).setExpiration(exp).compact();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Authorization", "Refresh " + refreshToken);

        addTokenFingerprintCookieToHeader(headers, "access", accessTokenFingerprint);
        addTokenFingerprintCookieToHeader(headers, "refresh", refreshTokenFingerprint);

        persistRefreshToken(refreshToken, user.getId());

        return new ResponseEntity<>(headers, HttpStatus.OK);
    }

    private String generateRandomStringForJwtFingerprint() {
        UniformRandomProvider rng = RandomSource.MT.create();

        char[][] pairs = {{'0', '9'}, {'A', 'Z'}, {'a', 'z'}};

        RandomStringGenerator generator =
                new RandomStringGenerator.Builder().withinRange(pairs).usingRandom(rng::nextInt).build();

        return generator.generate(32);
    }

    private void addTokenFingerprintCookieToHeader(HttpHeaders headers,
                                                   String token,
                                                   String fingerprint) {

        long time = token.equals("refresh") ? 86400 : 900;
        String cookiePrefix = token.equals("refresh") ? "_Secure" : "_Host";
        String path = token.equals("refresh") ? "/api/v1/auth/refresh; " :
                "/; ";

        Date expiresDate = new Date();
        expiresDate.setTime(expiresDate.getTime() + (time * 1000));
        DateFormat gmtFormat = new SimpleDateFormat("EEE, dd-MMM-yyyy " +
                "HH:mm:ss zzz", Locale.UK);
        gmtFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        String cookieExpires = "expires=" + gmtFormat.format(expiresDate);

        headers.add("Set-cookie",
                cookiePrefix + "-fingerprint=" + fingerprint + "; " +
                        "Max-Age=86400; " +
                        "Secure; " +
                        "HttpOnly; SameSite=Strict; " +
                        "Path=" + path + cookieExpires);
    }

    private void persistRefreshToken(String refreshToken, UUID userId) {
        User user =
                userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

        user.setRefreshToken(refreshToken);
        userRepository.save(user);
    }


}
