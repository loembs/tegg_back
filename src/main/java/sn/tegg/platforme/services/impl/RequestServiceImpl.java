package sn.tegg.platforme.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sn.tegg.platforme.data.models.*;
import sn.tegg.platforme.data.repository.*;
import sn.tegg.platforme.services.RequestService;
import sn.tegg.platforme.web.dto.request.ServiceRequestDto;
import sn.tegg.platforme.web.dto.request.UpdateRequestStatusRequest;
import sn.tegg.platforme.web.dto.response.ServiceRequestResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {

    private final ServiceRequestRepository requestRepository;
    private final UserRepository userRepository;
    private final ClientRepository clientRepository;
    private final ArtisanRepository artisanRepository;
    private final ServiceItemRepository serviceItemRepository;

    @Override
    public ServiceRequestResponse createRequest(Long clientId, ServiceRequestDto request) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found"));

        ServiceItem serviceItem = serviceItemRepository.findById(request.getServiceItemId())
                .orElseThrow(() -> new RuntimeException("Service not found"));

        ServiceRequest serviceRequest = ServiceRequest.builder()
                .clientId(clientId)
                .categoryId(serviceItem.getCategoryId())
                .subcategoryId(serviceItem.getSubcategoryId())
                .itemId(request.getServiceItemId())
                .title(serviceItem.getName())
                .description(request.getDescription())
                .address(request.getAddress())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .status(sn.tegg.platforme.data.enums.RequestStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();

        serviceRequest = requestRepository.save(serviceRequest);

        return buildServiceRequestResponse(serviceRequest);
    }

    @Override
    public List<ServiceRequestResponse> getClientRequests(Long clientId) {
        return requestRepository.findByClientId(clientId).stream()
                .map(this::buildServiceRequestResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ServiceRequestResponse> getArtisanRequests(Long artisanId) {
        return List.of();
    }

    @Override
    public List<ServiceRequestResponse> getRequestsByStatus(String status) {
        return requestRepository.findByStatus(status).stream()
                .map(this::buildServiceRequestResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ServiceRequestResponse updateRequestStatus(Long requestId, UpdateRequestStatusRequest request) {
        ServiceRequest serviceRequest = requestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        try {
            serviceRequest.setStatus(sn.tegg.platforme.data.enums.RequestStatus.valueOf(request.getStatus()));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid status: " + request.getStatus());
        }

        serviceRequest.setUpdatedAt(LocalDateTime.now());
        serviceRequest = requestRepository.save(serviceRequest);

        return buildServiceRequestResponse(serviceRequest);
    }

    @Override
    public ServiceRequestResponse getRequestById(Long id) {
        ServiceRequest serviceRequest = requestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Request not found"));
        return buildServiceRequestResponse(serviceRequest);
    }

    @Override
    public boolean cancelRequest(Long requestId, Long clientId) {
        ServiceRequest serviceRequest = requestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        if (!serviceRequest.getClientId().equals(clientId)) {
            throw new RuntimeException("Unauthorized to cancel this request");
        }

        if (serviceRequest.getStatus() != sn.tegg.platforme.data.enums.RequestStatus.PENDING) {
            throw new RuntimeException("Can only cancel pending requests");
        }

        serviceRequest.setStatus(sn.tegg.platforme.data.enums.RequestStatus.CANCELLED);
        serviceRequest.setCancelledAt(LocalDateTime.now());
        serviceRequest.setUpdatedAt(LocalDateTime.now());
        requestRepository.save(serviceRequest);

        return true;
    }

    private ServiceRequestResponse buildServiceRequestResponse(ServiceRequest request) {
        String clientName = null;
        Optional<Client> clientOpt = clientRepository.findById(request.getClientId());
        if (clientOpt.isPresent()) {
            Client client = clientOpt.get();
            clientName = client.getFirstName() + " " + client.getLastName();
        }

        String serviceTitle = request.getTitle();
        if (request.getItemId() != null) {
            Optional<ServiceItem> itemOpt = serviceItemRepository.findById(request.getItemId());
            if (itemOpt.isPresent()) {
                serviceTitle = itemOpt.get().getName();
            }
        }

        return ServiceRequestResponse.builder()
                .id(request.getId())
                .clientId(request.getClientId())
                .clientName(clientName)
                .serviceItemId(request.getItemId())
                .serviceTitle(serviceTitle)
                .artisanId(null)
                .artisanName(null)
                .status(request.getStatus() != null ? request.getStatus().name() : null)
                .description(request.getDescription())
                .address(request.getAddress())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .quotedPrice(null)
                .scheduledDate(null)
                .completedAt(null)
                .createdAt(request.getCreatedAt())
                .updatedAt(request.getUpdatedAt())
                .build();
    }
}
