package sn.tegg.platforme.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.tegg.platforme.data.models.ServiceRequest;

import java.util.List;

@Repository
public interface ServiceRequestRepository extends JpaRepository<ServiceRequest, Long> {

    List<ServiceRequest> findByClientId(Long clientId);

    List<ServiceRequest> findByStatus(String status);

    List<ServiceRequest> findByCategoryId(Long categoryId);

    List<ServiceRequest> findByItemId(Long itemId);
}
