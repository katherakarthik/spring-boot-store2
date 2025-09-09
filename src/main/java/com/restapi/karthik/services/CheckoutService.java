package com.restapi.karthik.services;


import com.restapi.karthik.dtos.CheckOutRequest;
import com.restapi.karthik.dtos.CheckoutResponse;
import com.restapi.karthik.entities.Order;
import com.restapi.karthik.entities.OrderStatus;
import com.restapi.karthik.entities.PaymentStatus;
import com.restapi.karthik.exceptions.CartEmptyException;
import com.restapi.karthik.exceptions.CartNotFoundException;
import com.restapi.karthik.exceptions.PaymentException;
import com.restapi.karthik.repositories.CartRepository;
import com.restapi.karthik.repositories.OrderRepository;

import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.PaymentIntent;
import com.stripe.net.Webhook;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashSet;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class CheckoutService {

    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final AuthService authService;
    private final CartService cartService;
    private final PaymentGateway paymentGateway;
    private static final Logger log = LoggerFactory.getLogger(CheckoutService.class);




    @Value("${websiteUrl}")
    private String websiteUrl;
    @Transactional
    public CheckoutResponse checkout(CheckOutRequest request) {


        var cart = cartRepository.getCartWithItems(request.getCartId()).orElse(null);
        if (cart == null) {
            throw new CartNotFoundException();

        }

        if (cart.isEmpty()) {
            throw new CartEmptyException();
        }

        // Validate current user

        var order = Order.fromCart(cart, authService.getCurrentUser());
        // Initialize orderItems if null
        if (order.getOrderItems() == null) {
            order.setOrderItems(new HashSet<>());
        }

        // Convert cart items to order items


        // Save order and clear cart
        var savedOrder = orderRepository.save(order);

        try {
            var session = paymentGateway.createCheckoutSession(savedOrder);
            cartService.clearCart(cart.getId());

            return new CheckoutResponse(savedOrder.getId(), session.getCheckoutUrl());


        } catch (PaymentException e) {

            orderRepository.delete(savedOrder);
            throw e;

        }
    }
    public void handleWebHookEvent(WebHookRequest request) {
        try {
            paymentGateway.parseWebhookRequest(request)
                    .ifPresent(payment -> {
                        log.info("✅ Webhook received for order {}", payment.getOrderId());
                        var order = orderRepository.findById(payment.getOrderId()).orElseThrow();
                        order.setStatus(OrderStatus.PAID);
                        orderRepository.save(order);
                        log.info("✅ Order {} updated to PAID", order.getId());
                    });
        } catch (Exception e) {
            log.error("❌ Error processing webhook", e);
        }
    }




}
