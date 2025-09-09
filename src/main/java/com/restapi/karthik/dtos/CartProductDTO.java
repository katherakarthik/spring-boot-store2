package com.restapi.karthik.dtos;

import lombok.Data;

import java.math.BigDecimal;
@Data

public class CartProductDTO {

    private Long id;
    private String name;
    private BigDecimal price;

}
