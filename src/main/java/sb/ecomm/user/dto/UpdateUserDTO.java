package sb.ecomm.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserDTO {

    private String username;
    private String email;
    private String password;

    private String line1;
    private String line2;
    private String line3;
    private String city;
    private String province;
    private String country;
    private String zipCode;

}
