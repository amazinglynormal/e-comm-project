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
import sb.ecomm.jwt.JwtTokenType;
import sb.ecomm.jwt.JwtUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
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

        String accessTokenFingerprint = JwtUtils.generateRandomStringForJwtFingerprint();
        String hashedAccessTokenFingerprint =
                Hashing.sha256().hashString(accessTokenFingerprint,
                StandardCharsets.UTF_8).toString();

        String accessToken = JwtUtils.generateJwtToken(user, hashedAccessTokenFingerprint,
                JwtTokenType.ACCESS);

        response.addHeader("Authorization", "Bearer " + accessToken);

        addAccessTokenFingerprintCookieToHeader(response, accessTokenFingerprint);

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
