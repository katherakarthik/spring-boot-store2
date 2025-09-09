package com.restapi.karthik.mappers;

import com.restapi.karthik.dtos.CartDTO;
import com.restapi.karthik.dtos.CartItemDTO;
import com.restapi.karthik.entities.Cart;
import com.restapi.karthik.entities.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartMapper {
    @Mapping(target = "items",source = "cartItems")
    @Mapping(target = "totalPrice",expression = "java(cart.getTotalPrice())")
    CartDTO toDTO(Cart cart);

    @Mapping(target="totalPrice",expression = "java(cartItem.getTotalPrice())")
    CartItemDTO toDTO(CartItem cartItem);
}
