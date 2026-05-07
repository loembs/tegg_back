package sn.tegg.platforme.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sn.tegg.platforme.data.enums.UserStatus;
import sn.tegg.platforme.data.enums.UserType;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private Long id;
    private String phone;
    private UserType userType;
    private UserStatus status;
    private Boolean phoneVerified;
    private LocalDateTime createdAt;
    private LocalDateTime lastLogin;

    private ClientInfo client;
    private ArtisanInfo artisan;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ClientInfo {
        private Long id;
        private String firstName;
        private String lastName;
        private String email;
        private String photoUrl;
        private String clientType;
        private String companyName;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ArtisanInfo {
        private Long id;
        private String firstName;
        private String lastName;
        private String email;
        private String photoUrl;
        private String bio;
        private Boolean isValidated;
        private String rating;
        private Boolean isOnline;
    }
}
