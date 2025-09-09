package com.restapi.karthik.dtos;

import com.restapi.karthik.entities.Product;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemDTO {
    private OrderProductDTO orderProductDTO;
    private int quantity;
    private BigDecimal totalPrice;


}
