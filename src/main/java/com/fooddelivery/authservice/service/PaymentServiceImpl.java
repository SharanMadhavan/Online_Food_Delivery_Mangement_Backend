package com.fooddelivery.authservice.service;

import com.fooddelivery.authservice.dto.PaymentRequest;
import com.fooddelivery.authservice.dto.PaymentResponse;
import com.fooddelivery.authservice.entity.Order;
import com.fooddelivery.authservice.exception.ResourceNotFoundException;
import com.fooddelivery.authservice.exception.ValidationException;
import com.fooddelivery.authservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public PaymentResponse processPayment(PaymentRequest paymentRequest) {
        // Verify order exists
        Order order = orderRepository.findById(paymentRequest.getOrderId())
                .orElseThrow(() -> new ResourceNotFoundException("Order", paymentRequest.getOrderId()));

        // Verify amount matches
        if (!order.getTotalAmount().equals(paymentRequest.getAmount())) {
            throw new ValidationException("Payment amount does not match order total");
        }

        // Mock payment processing - randomly succeed or fail
        boolean success = Math.random() > 0.2; // 80% success rate
        String transactionId = UUID.randomUUID().toString();
        
        if (success) {
            // Update order status to confirmed
            order.setStatus(Order.OrderStatus.CONFIRMED);
            orderRepository.save(order);
            
            return new PaymentResponse(true, "Payment processed successfully", transactionId);
        } else {
            return new PaymentResponse(false, "Payment processing failed. Please try again.", transactionId);
        }
    }
}
