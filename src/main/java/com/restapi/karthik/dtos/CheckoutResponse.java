package com.restapi.karthik.dtos;


import lombok.Data;

@Data
public class CheckoutResponse {

    private long orderId;
    private String checkoutUrl;
    public CheckoutResponse(long orderId, String checkoutUrl) {

        this.orderId = orderId;
        this.checkoutUrl = checkoutUrl;
    }
}
