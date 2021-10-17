package sb.ecomm.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AuthorizationFilter extends BasicAuthenticationFilter {

    public AuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader("Authorization");

        if (header == null) {
            chain.doFilter(request, response);
            return;
        }

        String requestUri = request.getRequestURI();

        UsernamePasswordAuthenticationToken authentication;

        if (isUriForUserEndpoint(requestUri)) {
            String resourceId = getResourceIdFromUri(requestUri);
            authentication =
                    authenticateWithUserIdMatchesResourceIdCheck(request,
                            resourceId);
        } else {
            authentication = authenticate(request);
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);

    }


    private boolean isUriForUserEndpoint(String uri) {
        Pattern pattern = Pattern.compile("api/v1/users/\\w*",
                Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(uri);
        return matcher.find();
    }

    private String getResourceIdFromUri(String uri) {
        return uri.split("users/")[1];
    }

    private UsernamePasswordAuthenticationToken authenticateWithUserIdMatchesResourceIdCheck(HttpServletRequest request, String resourceId) {
        String token = JwtUtils.getJwtTokenFromRequestHeader(request);

        if (!token.isEmpty()) {
            Jws<Claims> claims =
                    JwtUtils.parseJwtTokenClaimsWithRequiredSubject(token,
                            resourceId);

            if (claims != null) {
                return new UsernamePasswordAuthenticationToken(claims, null,
                        new ArrayList<>());
            } else {
                return null;
            }
        }

        return null;
    }

    private UsernamePasswordAuthenticationToken authenticate(HttpServletRequest request) {
        String token = JwtUtils.getJwtTokenFromRequestHeader(request);

        if (!token.isEmpty()) {
            Jws<Claims> claims = JwtUtils.parseJwtTokenClaims(token);

            if (claims != null) {
                return new UsernamePasswordAuthenticationToken(claims, null,
                        new ArrayList<>());
            } else {
                return null;
            }
        }

        return null;

    }


}
