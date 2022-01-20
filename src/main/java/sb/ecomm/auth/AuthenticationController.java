package sb.ecomm.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    ResponseEntity<?> authenticateUser(@RequestBody AuthenticationRequest authenticationRequest) {
        return authenticationService.authenticateUser(authenticationRequest);
    }

    @PostMapping("/refresh")
    ResponseEntity<?> refreshUser(@RequestHeader(name = "Authorization") String authorizationHeader,
                               @CookieValue(name = "_Secure-fingerprint") String fingerprint) {
        String refreshToken = authorizationHeader.split(" ")[1];
        return authenticationService.refreshUser(refreshToken, fingerprint);
    }

    @PostMapping("/{userId}")
    ResponseEntity<HttpStatus> reauthenticateUser(@RequestBody AuthenticationRequest authenticationRequest,
                                                  @PathVariable UUID userId) throws AuthenticationException {

            return authenticationService.reauthenticateUser(authenticationRequest, userId);
    }
}
