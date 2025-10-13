package com.fooddelivery.authservice.service;

import com.fooddelivery.authservice.dto.DeliveryDTO;
import java.util.List;

public interface DeliveryService {
    DeliveryDTO getDeliveryByOrderId(Long orderId);
    DeliveryDTO updateDeliveryStatus(Long id, String status);
    List<DeliveryDTO> getAllDeliveries();
    List<DeliveryDTO> getDeliveriesByStatus(String status);
}
