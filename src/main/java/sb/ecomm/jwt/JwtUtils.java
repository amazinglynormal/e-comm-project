package sb.ecomm.jwt;

import io.jsonwebtoken.*;
import sb.ecomm.constants.TempSecurityConstants;

import javax.servlet.http.HttpServletRequest;

public class JwtUtils {

    public static String getJwtTokenFromRequestHeader(HttpServletRequest req) {
        return req.getHeader("Authorization").substring(7);
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
