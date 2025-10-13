package com.fooddelivery.authservice.dto;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public class DeliveryDTO {
    private Long id;
    private Long orderId;
    
    @NotBlank(message = "Driver name is required")
    private String driverName;
    
    @NotBlank(message = "Driver phone is required")
    private String driverPhone;
    
    private String status;
    private String estimatedTime;
    private Double currentLocationLat;
    private Double currentLocationLng;
    private LocalDateTime createdAt;

    public DeliveryDTO() {}

    public DeliveryDTO(Long id, Long orderId, String driverName, String driverPhone, String status, String estimatedTime, Double currentLocationLat, Double currentLocationLng, LocalDateTime createdAt) {
        this.id = id;
        this.orderId = orderId;
        this.driverName = driverName;
        this.driverPhone = driverPhone;
        this.status = status;
        this.estimatedTime = estimatedTime;
        this.currentLocationLat = currentLocationLat;
        this.currentLocationLng = currentLocationLng;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverPhone() {
        return driverPhone;
    }

    public void setDriverPhone(String driverPhone) {
        this.driverPhone = driverPhone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(String estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public Double getCurrentLocationLat() {
        return currentLocationLat;
    }

    public void setCurrentLocationLat(Double currentLocationLat) {
        this.currentLocationLat = currentLocationLat;
    }

    public Double getCurrentLocationLng() {
        return currentLocationLng;
    }

    public void setCurrentLocationLng(Double currentLocationLng) {
        this.currentLocationLng = currentLocationLng;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
