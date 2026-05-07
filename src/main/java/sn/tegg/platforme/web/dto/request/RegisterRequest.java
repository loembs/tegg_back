package sn.tegg.platforme.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sn.tegg.platforme.data.enums.UserType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    @NotBlank
    @Pattern(regexp = "^\\+221\\d{9}$", message = "Phone number must be in format +221XXXXXXXXX")
    private String phone;

    @NotBlank
    @Size(min = 6, max = 100)
    private String password;

    @NotBlank
    private UserType userType;

    private String firstName;

    private String lastName;

    private String email;

    private String companyName;

    private String clientType;
}
