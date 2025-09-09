package com.restapi.karthik.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class CheckOutRequest {
    @NotNull(message ="Order id is required")
    private UUID cartId;


}
