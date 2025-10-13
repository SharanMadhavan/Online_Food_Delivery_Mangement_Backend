package com.fooddelivery.authservice.service;

import com.fooddelivery.authservice.dto.OrderDTO;
import com.fooddelivery.authservice.dto.OrderItemDTO;
import com.fooddelivery.authservice.entity.*;
import com.fooddelivery.authservice.exception.AccessDeniedException;
import com.fooddelivery.authservice.exception.ResourceNotFoundException;
import com.fooddelivery.authservice.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Override
    public OrderDTO createOrder(OrderDTO orderDTO, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", userId));
        
        Restaurant restaurant = restaurantRepository.findById(orderDTO.getRestaurantId())
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant", orderDTO.getRestaurantId()));

        Order order = new Order(user, restaurant, orderDTO.getTotalAmount(), orderDTO.getDeliveryAddress());
        Order savedOrder = orderRepository.save(order);

        // Create order items
        for (OrderItemDTO itemDTO : orderDTO.getOrderItems()) {
            MenuItem menuItem = menuItemRepository.findById(itemDTO.getMenuItemId())
                    .orElseThrow(() -> new ResourceNotFoundException("Menu item", itemDTO.getMenuItemId()));
            
            OrderItem orderItem = new OrderItem(savedOrder, menuItem, itemDTO.getQuantity(), itemDTO.getPrice());
            orderItemRepository.save(orderItem);
        }

        // Create delivery record
        Delivery delivery = new Delivery(savedOrder, "Driver " + (int)(Math.random() * 1000), 
                "+1234567890", "30-45 minutes");
        deliveryRepository.save(delivery);

        return convertToDTO(savedOrder);
    }

    @Override
    public OrderDTO getOrderById(Long id, Long userId) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order", id));
        
        // Check if user owns the order or is admin
        if (!order.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("Access denied: You can only view your own orders");
        }
        
        return convertToDTO(order);
    }

    @Override
    public List<OrderDTO> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserIdOrderByCreatedAtDesc(userId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDTO updateOrderStatus(Long id, String status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order", id));
        
        try {
            order.setStatus(Order.OrderStatus.valueOf(status));
            Order updatedOrder = orderRepository.save(order);
            return convertToDTO(updatedOrder);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid order status: " + status);
        }
    }

    private OrderDTO convertToDTO(Order order) {
        List<OrderItemDTO> orderItems = orderItemRepository.findByOrderId(order.getId()).stream()
                .map(item -> new OrderItemDTO(
                        item.getId(),
                        item.getMenuItem().getId(),
                        item.getMenuItem().getName(),
                        item.getQuantity(),
                        item.getPrice()
                ))
                .collect(Collectors.toList());

        return new OrderDTO(
                order.getId(),
                order.getUser().getId(),
                order.getRestaurant().getId(),
                order.getRestaurant().getName(),
                order.getTotalAmount(),
                order.getStatus().toString(),
                order.getDeliveryAddress(),
                order.getCreatedAt(),
                orderItems
        );
    }
}
