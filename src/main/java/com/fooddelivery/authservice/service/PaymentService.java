package com.fooddelivery.authservice.service;

import com.fooddelivery.authservice.dto.PaymentRequest;
import com.fooddelivery.authservice.dto.PaymentResponse;

public interface PaymentService {
    PaymentResponse processPayment(PaymentRequest paymentRequest);
}
