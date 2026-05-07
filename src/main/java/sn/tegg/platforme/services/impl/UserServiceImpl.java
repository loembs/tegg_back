package sn.tegg.platforme.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sn.tegg.platforme.data.enums.UserStatus;
import sn.tegg.platforme.data.models.Client;
import sn.tegg.platforme.data.models.User;
import sn.tegg.platforme.data.repository.ClientRepository;
import sn.tegg.platforme.data.repository.UserRepository;
import sn.tegg.platforme.services.UserService;
import sn.tegg.platforme.web.dto.response.UserResponse;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ClientRepository clientRepository;
    private final PasswordEncoder encoder;

    @Override
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return buildUserResponse(user);
    }

    @Override
    public UserResponse getUserByPhone(String phone) {
        User user = userRepository.findByPhone(phone)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return buildUserResponse(user);
    }

    @Override
    public UserResponse updateUserProfile(Long userId, String firstName, String lastName, String email) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        clientRepository.findByUserId(userId).ifPresent(client -> {
            if (firstName != null) client.setFirstName(firstName);
            if (lastName != null) client.setLastName(lastName);
            if (email != null) client.setEmail(email);
            clientRepository.save(client);
        });

        user.setUpdatedAt(java.time.LocalDateTime.now());
        userRepository.save(user);

        return buildUserResponse(user);
    }

    @Override
    public boolean changePassword(Long userId, String oldPassword, String newPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!encoder.matches(oldPassword, user.getPasswordHash())) {
            return false;
        }

        user.setPasswordHash(encoder.encode(newPassword));
        user.setUpdatedAt(java.time.LocalDateTime.now());
        userRepository.save(user);

        return true;
    }

    @Override
    public boolean deactivateUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setStatus(UserStatus.BLOCKED);
        user.setUpdatedAt(java.time.LocalDateTime.now());
        userRepository.save(user);

        return true;
    }

    private UserResponse buildUserResponse(User user) {
        UserResponse.UserResponseBuilder builder = UserResponse.builder()
                .id(user.getId())
                .phone(user.getPhone())
                .userType(user.getUserType())
                .status(user.getStatus())
                .phoneVerified(user.getPhoneVerified())
                .createdAt(user.getCreatedAt())
                .lastLogin(user.getLastLogin());

        clientRepository.findByUserId(user.getId()).ifPresent(client -> {
            builder.client(UserResponse.ClientInfo.builder()
                    .id(client.getId())
                    .firstName(client.getFirstName())
                    .lastName(client.getLastName())
                    .email(client.getEmail())
                    .photoUrl(client.getPhotoUrl())
                    .clientType(client.getClientType())
                    .companyName(client.getCompanyName())
                    .build());
        });

        return builder.build();
    }
}
