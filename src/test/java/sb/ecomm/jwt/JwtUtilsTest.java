package sb.ecomm.jwt;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;


import javax.servlet.http.Cookie;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilsTest {

    private final String mockFingerprint = "fingerprint";

    @Test
    void getJwtTokenFromRequestHeader() {
        MockHttpServletRequest req = new MockHttpServletRequest();
        req.addHeader("Authorization", "Bearer abcdefghijk123");

        String response = JwtUtils.getJwtTokenFromRequestHeader(req);
        assertEquals("abcdefghijk123", response);
    }

    @Test
    void getJwtFingerprintFromRequestHeaderReturnsCorrectCookieValue() {
        MockHttpServletRequest req = new MockHttpServletRequest();
        Cookie hostCookie = new Cookie("_Host-fingerprint", "host-" + this.mockFingerprint);
        Cookie secureCookie = new Cookie("_Secure-fingerprint", "secure-" + this.mockFingerprint);
        req.setCookies(hostCookie, secureCookie);

        String response1 = JwtUtils.getJwtFingerprintFromRequestHeader(req, JwtTokenType.ACCESS);
        String response2 = JwtUtils.getJwtFingerprintFromRequestHeader(req, JwtTokenType.REFRESH);

        assertEquals("host-" + this.mockFingerprint, response1);
        assertEquals("secure-" + this.mockFingerprint, response2);
    }

}