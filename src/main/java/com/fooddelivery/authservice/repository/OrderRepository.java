package com.fooddelivery.authservice.repository;

import com.fooddelivery.authservice.entity.Order;
import com.fooddelivery.authservice.entity.Order.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
    List<Order> findByUserIdOrderByCreatedAtDesc(Long userId);
    List<Order> findByStatus(OrderStatus status);
    List<Order> findByRestaurantId(Long restaurantId);
}
