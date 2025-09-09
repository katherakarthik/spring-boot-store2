package com.restapi.karthik.services;

import com.restapi.karthik.entities.Order;
import com.restapi.karthik.entities.OrderItem;
import com.restapi.karthik.entities.OrderStatus;
import com.restapi.karthik.entities.PaymentStatus;
import com.restapi.karthik.exceptions.PaymentException;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.stripe.param.checkout.SessionCreateParams;
import org.hibernate.annotations.ValueGenerationType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class StripePaymentGateway implements PaymentGateway {
    @Value("${websiteUrl}")
    private String websiteUrl;

    @Value("${stripe.webhookSecretKey}")
    private String webhookSecretKey;
    @Override
    public CheckoutSession createCheckoutSession(Order order) {
        try {
            var builder = SessionCreateParams.builder()
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl(websiteUrl + "/checkout-success?orderId=" + order.getId())
                    .setCancelUrl(websiteUrl + "/checkout-cancel")
                            .putMetadata("order_id",order.getId().toString());


            order.getOrderItems().forEach(item -> {
                var lineItem = createLineItem(item);
                builder.addLineItem(lineItem);
            });
            var session = Session.create(builder.build());
            return new CheckoutSession(session.getUrl());
        }catch(StripeException ex){
            System.out.println(ex.getMessage());
            throw new PaymentException();



        }
    }

    @Override

    public Optional<PaymentResult> parseWebhookRequest(WebHookRequest request) {
        try {
            String payload = request.getPayload();
            String signature = request.getHeaders().get("stripe-signature");

            Event event = Webhook.constructEvent(payload, signature, webhookSecretKey);

            switch (event.getType()) {
                case "payment_intent.succeeded" -> {
                    PaymentIntent intent = (PaymentIntent) event.getDataObjectDeserializer()
                            .getObject().orElse(null);
                    if (intent != null) {
                        String orderId = intent.getMetadata().get("order_id");
                        return Optional.of(new PaymentResult(Long.parseLong(orderId), PaymentStatus.PAID));
                    }
                }
                case "payment_intent.payment_failed" -> {
                    PaymentIntent intent = (PaymentIntent) event.getDataObjectDeserializer()
                            .getObject().orElse(null);
                    if (intent != null) {
                        String orderId = intent.getMetadata().get("order_id");
                        return Optional.of(new PaymentResult(Long.parseLong(orderId), PaymentStatus.FAILED));
                    }
                }
            }

            return Optional.empty();
        } catch (SignatureVerificationException e) {
            throw new PaymentException("Signature verification failed");
        }
    }


    private long extractOrderId(Event event){
        var stripeObject = event.getDataObjectDeserializer().getObject().orElseThrow(
                () -> new PaymentException("Couldnt deserialize the stripe event")
        );
        var paymentintent = (PaymentIntent) stripeObject;
        return Long.valueOf( paymentintent.getMetadata().get("order_id"));
    }

    private SessionCreateParams.LineItem createLineItem(OrderItem item) {
        return SessionCreateParams.LineItem.builder()
                .setQuantity(Long.valueOf(item.getQuantity()))
                .setPriceData(
                        createProductPrice(item)
                ).build();
    }

    private SessionCreateParams.LineItem.PriceData createProductPrice(OrderItem item) {
        return SessionCreateParams.LineItem.PriceData.builder()
                .setCurrency("usd")
                .setUnitAmountDecimal(
                        item.getUnitPrice().multiply(BigDecimal.valueOf(100)))
                .setProductData(createPriceData(item))
                .build();
    }

    private  SessionCreateParams.LineItem.PriceData.ProductData createPriceData(OrderItem item) {
        return SessionCreateParams.LineItem.PriceData.ProductData.builder()
                .setName(item.getProduct().getName())
                .build();
    }
}
