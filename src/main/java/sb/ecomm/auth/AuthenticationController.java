package sb.ecomm.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import sb.ecomm.security.UserDetailsImpl;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationManager authenticationManager, AuthenticationService authenticationService) {
        this.authenticationManager = authenticationManager;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    ResponseEntity authenticateUser(@RequestBody AuthenticationRequest authenticationRequest) {
        return authenticationService.authenticateUser(authenticationRequest);
    }

    @PostMapping("/{id}")
    void reauthenticateUser(@RequestBody AuthenticationRequest authReq,
                            @PathVariable UUID id) throws AuthenticationException {

            UsernamePasswordAuthenticationToken token =
                    new UsernamePasswordAuthenticationToken(authReq.getEmail(), authReq.getPassword());
            Authentication authentication =
                    authenticationManager.authenticate(token);

            UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();

            if (!authentication.isAuthenticated() ||
                    !isCorrectUser(user.getId(), id)) {
                throw new RuntimeException("Hit an Authentication Exception");
            }
    }

    private boolean isCorrectUser(UUID authenticatedId, UUID suppliedId) {
        return authenticatedId.equals(suppliedId);
    }


}
