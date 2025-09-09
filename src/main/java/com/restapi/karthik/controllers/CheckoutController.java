package com.restapi.karthik.controllers;


import com.restapi.karthik.dtos.CheckOutRequest;
import com.restapi.karthik.dtos.CheckoutResponse;
import com.restapi.karthik.dtos.ErrorDTO;
import com.restapi.karthik.entities.OrderStatus;
import com.restapi.karthik.exceptions.CartEmptyException;
import com.restapi.karthik.exceptions.CartNotFoundException;
import com.restapi.karthik.exceptions.PaymentException;
import com.restapi.karthik.repositories.CartRepository;
import com.restapi.karthik.repositories.OrderRepository;
import com.restapi.karthik.services.AuthService;
import com.restapi.karthik.services.CartService;
import com.restapi.karthik.services.CheckoutService;
import com.restapi.karthik.services.WebHookRequest;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.net.Webhook;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/checkout")

public class CheckoutController {

    private final CheckoutService checkoutService;
    private final OrderRepository orderRepository;

    @Value("${stripe.webhookSecretKey}")
    private String webHookSecretKey;

    @PostMapping
    public CheckoutResponse checkout(@Valid @RequestBody CheckOutRequest request) {

        return checkoutService.checkout(request);

    }
    @PostMapping("/webhook")
    public void handleWebHook(
         @RequestHeader Map<String, String> headers,
         @RequestBody String payload
    ){

        checkoutService.handleWebHookEvent(new WebHookRequest(headers, payload));
    }


    public ResponseEntity<?> handlePaymentException(){
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorDTO("Error creating checkout Session"));

    }


    @ExceptionHandler({CartEmptyException.class, CartNotFoundException.class})
    public ResponseEntity<ErrorDTO>handleException(Exception ex){
        return ResponseEntity.badRequest().body(new ErrorDTO(ex.getMessage()));

    }

}