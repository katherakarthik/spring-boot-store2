package com.restapi.karthik.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItemDTO {
    private CartProductDTO product;
    private int quantity;
    private BigDecimal totalPrice;





}
