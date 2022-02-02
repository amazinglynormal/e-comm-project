package sb.ecomm.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordDto {
    private String resetToken;
    private String newPassword;
}
