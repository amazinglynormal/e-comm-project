package sb.ecomm.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import sb.ecomm.jwt.JwtUtils;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    ResponseEntity<LoginResponse> authenticateUser(@RequestBody AuthenticationRequest authenticationRequest) {
        return authenticationService.authenticateUser(authenticationRequest);
    }

    @PostMapping("/refresh")
    ResponseEntity<?> refreshUser(
            @RequestHeader(name = "Authorization") String authorizationHeader,
            @CookieValue(name = "_Secure-fingerprint") String fingerprint
    ) {
        String refreshToken = authorizationHeader.split(" ")[1];
        System.out.println(fingerprint);
        System.out.println(JwtUtils.hashJwtFingerprint(fingerprint));
        return authenticationService.refreshUser(refreshToken, fingerprint);
    }

    @PostMapping("/verify/{verificationHash}")
    ResponseEntity<HttpStatus> verifyUserEmail(@PathVariable String verificationHash) {
        return authenticationService.verifyUserEmail(verificationHash);
    }

    @PostMapping("/logout")
    ResponseEntity<HttpStatus> logoutUser(
            @RequestHeader(name = "Authorization") String authorizationHeader,
            @CookieValue(name = "_Host-fingerprint") String fingerprint
    ) {
        String accessToken = authorizationHeader.split(" ")[1];
        return authenticationService.logoutUser(accessToken, fingerprint);
    }

    @PostMapping("/{userId}")
    ResponseEntity<HttpStatus> reauthenticateUser(@RequestBody AuthenticationRequest authenticationRequest,
                                                  @PathVariable UUID userId) throws AuthenticationException {

            return authenticationService.reauthenticateUser(authenticationRequest, userId);
    }
}
