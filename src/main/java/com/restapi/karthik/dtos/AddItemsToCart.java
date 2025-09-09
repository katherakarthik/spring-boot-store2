package com.restapi.karthik.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddItemsToCart {
    @NotNull
    private Long productId;


}
