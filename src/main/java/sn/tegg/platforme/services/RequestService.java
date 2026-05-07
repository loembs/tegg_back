package sn.tegg.platforme.services;

import sn.tegg.platforme.web.dto.request.ServiceRequestDto;
import sn.tegg.platforme.web.dto.request.UpdateRequestStatusRequest;
import sn.tegg.platforme.web.dto.response.ServiceRequestResponse;

import java.util.List;

public interface RequestService {

    ServiceRequestResponse createRequest(Long clientId, ServiceRequestDto request);

    List<ServiceRequestResponse> getClientRequests(Long clientId);

    List<ServiceRequestResponse> getArtisanRequests(Long artisanId);

    List<ServiceRequestResponse> getRequestsByStatus(String status);

    ServiceRequestResponse updateRequestStatus(Long requestId, UpdateRequestStatusRequest request);

    ServiceRequestResponse getRequestById(Long id);

    boolean cancelRequest(Long requestId, Long clientId);
}
