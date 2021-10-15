package sb.ecomm.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;


public class UserIdMatchesResourceIdAuthorizationFilter extends BasicAuthenticationFilter {

    public UserIdMatchesResourceIdAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        String header = request.getHeader("Authorization");

        if (header == null) {
            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = authenticate(request);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken authenticate(HttpServletRequest request) {
        String token = JwtUtils.getJwtTokenFromRequestHeader(request);

        String userResourceId = getUserResourceIdFromURI(request);

        if (!token.isEmpty()) {
            Jws<Claims> claims =
                    JwtUtils.parseJwtTokenClaimsWithRequiredSubject(token,
                            userResourceId);

            if (claims != null) {
                return new UsernamePasswordAuthenticationToken(claims, null,
                        new ArrayList<>());
            } else {
                return null;
            }
        }

        return null;

    }

    private String getUserResourceIdFromURI(HttpServletRequest req) {
            return req.getRequestURI().split("users/")[1];
    }

}
