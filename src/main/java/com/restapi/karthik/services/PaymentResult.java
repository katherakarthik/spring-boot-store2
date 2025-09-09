package com.restapi.karthik.services;

import com.restapi.karthik.entities.OrderStatus;
import com.restapi.karthik.entities.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter

public class PaymentResult {

    private long orderId;
    private PaymentStatus status;


}
