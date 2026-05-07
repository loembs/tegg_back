package sn.tegg.platforme.web.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import sn.tegg.platforme.security.UserDetailsImpl;
import sn.tegg.platforme.services.UserService;
import sn.tegg.platforme.web.dto.response.ApiResponse;
import sn.tegg.platforme.web.dto.response.UserResponse;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserResponse>> getCurrentUser(
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        UserResponse response = userService.getUserById(userDetails.getId());
        return ResponseEntity.ok(ApiResponse.success("User retrieved successfully", response));
    }

    @PutMapping("/me")
    public ResponseEntity<ApiResponse<UserResponse>> updateProfile(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String email,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        UserResponse response = userService.updateUserProfile(
                userDetails.getId(), firstName, lastName, email);
        return ResponseEntity.ok(ApiResponse.success("Profile updated successfully", response));
    }

    @PostMapping("/change-password")
    public ResponseEntity<ApiResponse<Void>> changePassword(
            @RequestParam String oldPassword,
            @RequestParam String newPassword,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        boolean changed = userService.changePassword(userDetails.getId(), oldPassword, newPassword);
        if (changed) {
            return ResponseEntity.ok(ApiResponse.success("Password changed successfully"));
        }
        return ResponseEntity.badRequest()
                .body(ApiResponse.error("Old password is incorrect"));
    }

    @PostMapping("/deactivate")
    public ResponseEntity<ApiResponse<Void>> deactivateAccount(
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        userService.deactivateUser(userDetails.getId());
        return ResponseEntity.ok(ApiResponse.success("Account deactivated successfully"));
    }
}
