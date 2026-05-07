package sn.tegg.platforme.web.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sn.tegg.platforme.services.RequestService;
import sn.tegg.platforme.services.ServiceService;
import sn.tegg.platforme.services.UserService;
import sn.tegg.platforme.web.dto.response.ApiResponse;
import sn.tegg.platforme.web.dto.response.ServiceRequestResponse;
import sn.tegg.platforme.web.dto.response.UserResponse;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@CrossOrigin(origins = "*")
public class AdminController {

    private final UserService userService;
    private final RequestService requestService;

    @GetMapping("/users")
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers() {
        List<UserResponse> users = List.of();
        return ResponseEntity.ok(ApiResponse.success("Users retrieved successfully", users));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable Long id) {
        UserResponse user = userService.getUserById(id);
        return ResponseEntity.ok(ApiResponse.success("User retrieved successfully", user));
    }

    @GetMapping("/requests")
    public ResponseEntity<ApiResponse<List<ServiceRequestResponse>>> getAllRequests() {
        List<ServiceRequestResponse> requests = requestService.getRequestsByStatus("PENDING");
        return ResponseEntity.ok(ApiResponse.success("Requests retrieved successfully", requests));
    }

    @GetMapping("/requests/status/{status}")
    public ResponseEntity<ApiResponse<List<ServiceRequestResponse>>> getRequestsByStatus(
            @PathVariable String status) {
        List<ServiceRequestResponse> requests = requestService.getRequestsByStatus(status);
        return ResponseEntity.ok(ApiResponse.success("Requests retrieved successfully", requests));
    }
}
