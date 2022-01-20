package sb.ecomm.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.apache.commons.rng.UniformRandomProvider;
import org.apache.commons.rng.simple.RandomSource;
import org.apache.commons.text.RandomStringGenerator;
import sb.ecomm.constants.TempSecurityConstants;
import sb.ecomm.security.UserDetailsImpl;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JwtUtils {

    public static String getJwtTokenFromRequestHeader(HttpServletRequest req) {
        return req.getHeader("Authorization").substring(7);
    }

    public static String generateJwtToken(UserDetailsImpl user, String hashedFingerprint,
                                                         JwtTokenType tokenType) {
        List<String> authorities = new ArrayList<>();
        user.getAuthorities().forEach(authority -> authorities.add(authority.getAuthority()));
        long expiryTime = tokenType == JwtTokenType.ACCESS ? 1000L*60*30 : 1000L*60*60*24;
        Date exp = new Date(System.currentTimeMillis() + expiryTime);
        Key key = Keys.hmacShaKeyFor(TempSecurityConstants.jwtKey.getBytes());

        if (tokenType == JwtTokenType.ACCESS) {
            return Jwts.builder()
                    .claim("email", user.getUsername())
                    .claim("authorities", authorities)
                    .claim("isEnabled", user.isEnabled())
                    .claim("user_context", hashedFingerprint)
                    .setSubject(user.getId().toString()).signWith(key,
                            SignatureAlgorithm.HS512).setExpiration(exp).compact();
        } else {
            return Jwts.builder()
                    .claim("user_context", hashedFingerprint)
                    .setSubject(user.getId().toString()).signWith(key,
                            SignatureAlgorithm.HS512).setExpiration(exp).compact();
        }
    }

    public static String generateRandomStringForJwtFingerprint() {
        UniformRandomProvider rng = RandomSource.MT.create();

        char[][] pairs = {{'0', '9'}, {'A', 'Z'}, {'a', 'z'}};

        RandomStringGenerator generator =
                new RandomStringGenerator.Builder().withinRange(pairs).usingRandom(rng::nextInt).build();

        return generator.generate(32);
    }

    public static Jws<Claims> parseJwtTokenClaims(String token) {
        try {
            return Jwts.parserBuilder().setSigningKey(TempSecurityConstants.jwtKey.getBytes()).build().parseClaimsJws(token);
        } catch (JwtException ex) {
            throw new JwtException("User is not authorised to perform to request");
        }
    }

    public static Jws<Claims> parseJwtTokenClaimsWithRequiredSubject(String token, String requiredSubject) {
        try {
            return Jwts.parserBuilder().requireSubject(requiredSubject).setSigningKey(TempSecurityConstants.jwtKey.getBytes()).build().parseClaimsJws(token);
        } catch (IncorrectClaimException ex) {
            throw new IncorrectClaimException(ex.getHeader(), ex.getClaims(),
                    "Not authorised to access this resource");
        }
    }
}
