package com.fooddelivery.authservice.service;

import com.fooddelivery.authservice.dto.OrderDTO;
import java.util.List;

public interface OrderService {
    OrderDTO createOrder(OrderDTO orderDTO, Long userId);
    OrderDTO getOrderById(Long id, Long userId);
    List<OrderDTO> getOrdersByUserId(Long userId);
    OrderDTO updateOrderStatus(Long id, String status);
}
