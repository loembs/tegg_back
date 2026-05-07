package sn.tegg.platforme.services;

import sn.tegg.platforme.data.models.User;
import sn.tegg.platforme.web.dto.response.UserResponse;

import java.util.Optional;

public interface UserService {

    UserResponse getUserById(Long id);

    UserResponse getUserByPhone(String phone);

    UserResponse updateUserProfile(Long userId, String firstName, String lastName, String email);

    boolean changePassword(Long userId, String oldPassword, String newPassword);

    boolean deactivateUser(Long userId);
}
