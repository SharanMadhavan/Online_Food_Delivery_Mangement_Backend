package com.fooddelivery.authservice.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "deliveries")
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(name = "driver_name")
    private String driverName;

    @Column(name = "driver_phone")
    private String driverPhone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeliveryStatus status;

    @Column(name = "estimated_time")
    private String estimatedTime;

    @Column(name = "current_location_lat")
    private Double currentLocationLat;

    @Column(name = "current_location_lng")
    private Double currentLocationLng;

    public Delivery() {
        this.status = DeliveryStatus.ASSIGNED;
    }

    public Delivery(Order order, String driverName, String driverPhone, String estimatedTime) {
        this();
        this.order = order;
        this.driverName = driverName;
        this.driverPhone = driverPhone;
        this.estimatedTime = estimatedTime;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
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

    public DeliveryStatus getStatus() {
        return status;
    }

    public void setStatus(DeliveryStatus status) {
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

    public enum DeliveryStatus {
        ASSIGNED,
        IN_TRANSIT,
        DELIVERED
    }
}
