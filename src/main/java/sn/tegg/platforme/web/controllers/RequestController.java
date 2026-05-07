package sn.tegg.platforme.web.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import sn.tegg.platforme.security.UserDetailsImpl;
import sn.tegg.platforme.services.RequestService;
import sn.tegg.platforme.web.dto.request.ServiceRequestDto;
import sn.tegg.platforme.web.dto.request.UpdateRequestStatusRequest;
import sn.tegg.platforme.web.dto.response.ApiResponse;
import sn.tegg.platforme.web.dto.response.ServiceRequestResponse;

import java.util.List;

@RestController
@RequestMapping("/api/requests")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class RequestController {

    private final RequestService requestService;

    @PostMapping
    public ResponseEntity<ApiResponse<ServiceRequestResponse>> createRequest(
            @Valid @RequestBody ServiceRequestDto request,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ServiceRequestResponse response = requestService.createRequest(userDetails.getId(), request);
        return ResponseEntity.ok(ApiResponse.success("Request created successfully", response));
    }

    @GetMapping("/my-requests")
    public ResponseEntity<ApiResponse<List<ServiceRequestResponse>>> getClientRequests(
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<ServiceRequestResponse> requests = requestService.getClientRequests(userDetails.getId());
        return ResponseEntity.ok(ApiResponse.success("Requests retrieved successfully", requests));
    }

    @GetMapping("/artisan")
    public ResponseEntity<ApiResponse<List<ServiceRequestResponse>>> getArtisanRequests(
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<ServiceRequestResponse> requests = requestService.getArtisanRequests(userDetails.getId());
        return ResponseEntity.ok(ApiResponse.success("Requests retrieved successfully", requests));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<List<ServiceRequestResponse>>> getRequestsByStatus(
            @PathVariable String status) {
        List<ServiceRequestResponse> requests = requestService.getRequestsByStatus(status);
        return ResponseEntity.ok(ApiResponse.success("Requests retrieved successfully", requests));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ServiceRequestResponse>> getRequestById(@PathVariable Long id) {
        ServiceRequestResponse request = requestService.getRequestById(id);
        return ResponseEntity.ok(ApiResponse.success("Request retrieved successfully", request));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<ApiResponse<ServiceRequestResponse>> updateRequestStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateRequestStatusRequest request) {
        ServiceRequestResponse response = requestService.updateRequestStatus(id, request);
        return ResponseEntity.ok(ApiResponse.success("Request status updated successfully", response));
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<ApiResponse<Void>> cancelRequest(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        requestService.cancelRequest(id, userDetails.getId());
        return ResponseEntity.ok(ApiResponse.success("Request cancelled successfully"));
    }
}
