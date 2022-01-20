package sb.ecomm.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.hash.Hashing;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.apache.commons.rng.UniformRandomProvider;
import org.apache.commons.rng.simple.RandomSource;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import sb.ecomm.auth.AuthenticationRequest;
import sb.ecomm.constants.TempSecurityConstants;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    public AuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            AuthenticationRequest req =
                    new ObjectMapper().readValue(request.getInputStream(),
                            AuthenticationRequest.class);

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(req.getEmail(),
                            req.getPassword(),
                            new ArrayList<>()));
        } catch (IOException ex) {
            throw new RuntimeException("Hit authentication Exception");
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        UserDetailsImpl user = (UserDetailsImpl) authResult.getPrincipal();
        List<String> authorities = new ArrayList<>();
        user.getAuthorities().forEach(authority -> authorities.add(authority.getAuthority()));
        Date exp = new Date(System.currentTimeMillis() + 1000L*60*30);
        Key key = Keys.hmacShaKeyFor(TempSecurityConstants.jwtKey.getBytes());

        String accessTokenFingerprint = generateRandomStringForJwtFingerprint();
        String hashedAccessTokenFingerprint =
                Hashing.sha256().hashString(accessTokenFingerprint,
                StandardCharsets.UTF_8).toString();

        String accessToken =
                Jwts.builder()
                        .claim("email", user.getUsername())
                        .claim("authorities", authorities)
                        .claim("isEnabled", user.isEnabled())
                        .claim("user_context", hashedAccessTokenFingerprint)
                        .setSubject(user.getId().toString()).signWith(key,
                SignatureAlgorithm.HS512).setExpiration(exp).compact();

        response.addHeader("Authorization", "Bearer " + accessToken);

        addAccessTokenFingerprintCookieToHeader(response, accessTokenFingerprint);

    }

    private String generateRandomStringForJwtFingerprint() {
        UniformRandomProvider rng = RandomSource.MT.create();

        char[][] pairs = {{'0', '9'}, {'A', 'Z'}, {'a', 'z'}};

        RandomStringGenerator generator =
                new RandomStringGenerator.Builder().withinRange(pairs).usingRandom(rng::nextInt).build();

        return generator.generate(32);
    }

    private void addAccessTokenFingerprintCookieToHeader(HttpServletResponse response,
                                              String fingerprint) {

        Date expiresDate = new Date();
        expiresDate.setTime(expiresDate.getTime() + (900 * 1000));
        DateFormat gmtFormat = new SimpleDateFormat("EEE, dd-MMM-yyyy " +
                "HH:mm:ss zzz", Locale.UK);
        gmtFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        String cookieExpires = "expires=" + gmtFormat.format(expiresDate);

        response.addHeader("Set-cookie",
                "_Host-fingerprint=" + fingerprint + "; Max-Age=900; " +
                        "Secure; " +
                        "HttpOnly; SameSite=Strict; Path=/; " + cookieExpires);
    }
}
