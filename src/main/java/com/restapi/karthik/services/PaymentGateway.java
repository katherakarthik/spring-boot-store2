package com.restapi.karthik.services;

import com.restapi.karthik.entities.Order;
import com.restapi.karthik.entities.PaymentStatus;

import java.util.Optional;

public interface PaymentGateway {
    CheckoutSession createCheckoutSession(Order order);
    Optional<PaymentResult> parseWebhookRequest(WebHookRequest request);
}
