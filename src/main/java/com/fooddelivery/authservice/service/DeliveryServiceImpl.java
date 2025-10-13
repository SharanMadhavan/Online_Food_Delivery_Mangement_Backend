package com.fooddelivery.authservice.service;

import com.fooddelivery.authservice.dto.DeliveryDTO;
import com.fooddelivery.authservice.entity.Delivery;
import com.fooddelivery.authservice.repository.DeliveryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class DeliveryServiceImpl implements DeliveryService {

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Override
    public DeliveryDTO getDeliveryByOrderId(Long orderId) {
        Delivery delivery = deliveryRepository.findByOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Delivery not found for order id: " + orderId));
        return convertToDTO(delivery);
    }

    @Override
    public DeliveryDTO updateDeliveryStatus(Long id, String status) {
        Delivery delivery = deliveryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Delivery not found with id: " + id));
        
        try {
            delivery.setStatus(Delivery.DeliveryStatus.valueOf(status));
            Delivery updatedDelivery = deliveryRepository.save(delivery);
            return convertToDTO(updatedDelivery);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid delivery status: " + status);
        }
    }

    @Override
    public List<DeliveryDTO> getAllDeliveries() {
        return deliveryRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<DeliveryDTO> getDeliveriesByStatus(String status) {
        try {
            Delivery.DeliveryStatus deliveryStatus = Delivery.DeliveryStatus.valueOf(status);
            return deliveryRepository.findByStatus(deliveryStatus).stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid delivery status: " + status);
        }
    }

    private DeliveryDTO convertToDTO(Delivery delivery) {
        return new DeliveryDTO(
                delivery.getId(),
                delivery.getOrder().getId(),
                delivery.getDriverName(),
                delivery.getDriverPhone(),
                delivery.getStatus().toString(),
                delivery.getEstimatedTime(),
                delivery.getCurrentLocationLat(),
                delivery.getCurrentLocationLng(),
                LocalDateTime.now() // You might want to add createdAt field to Delivery entity
        );
    }
}
