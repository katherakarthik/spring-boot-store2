package com.restapi.karthik.controllers;

import com.restapi.karthik.dtos.AddItemsToCart;
import com.restapi.karthik.dtos.CartDTO;
import com.restapi.karthik.dtos.CartItemDTO;
import com.restapi.karthik.dtos.UpdateCartItemRequest;

import com.restapi.karthik.exceptions.CartNotFoundException;
import com.restapi.karthik.exceptions.ProductNotFoundException;
import com.restapi.karthik.services.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;
import java.util.UUID;
@Tag(name = "Carts")
@AllArgsConstructor
@RestController
@RequestMapping("/carts")
public class CartController {


    private final CartService cartService;


    @PostMapping
    public ResponseEntity<CartDTO> createcart(
            UriComponentsBuilder uriBuilder
    ){
        var cartDTO = cartService.createCart();
        var uri =  uriBuilder.path("/carts/{id}").buildAndExpand(cartDTO.getId()).toUri();
        return  ResponseEntity.created(uri).body(cartDTO);


    }
    @PostMapping("/{cartId}/items")
    @Operation(summary ="Add a Product to the cart")
    public ResponseEntity<CartItemDTO> addToCart(
            @Parameter(description = "The id of the cart")
            @PathVariable UUID cartId,

            @RequestBody AddItemsToCart request
    ){

        var cartItemDTO = cartService.addToCart(cartId,request.getProductId());



        return  ResponseEntity.status(HttpStatus.CREATED).body(cartItemDTO);
    }
    @GetMapping("/{cartId}")
    public CartDTO getCart(
            @PathVariable UUID cartId
    ){
        return cartService.getCart(cartId);



    }
    @PutMapping("/{cartId}/items/{productId}")
    public CartItemDTO updateItem(
            @PathVariable("cartId")UUID cartId,
            @PathVariable("productId")Long productId,
            @Valid @RequestBody UpdateCartItemRequest request
    )
    {
        return  cartService.updateItem(cartId,productId, request.getQuantity());

    }
    @DeleteMapping("/{cartId}/items/{productId}")
    public ResponseEntity<?>removeItem(
            @PathVariable("cartId") UUID cartId,
            @PathVariable("productId") Long productId
    )
    {
        cartService.removeItem(cartId,productId);

        return ResponseEntity.noContent().build();

    }
    @DeleteMapping("/{cartId}/items")
    public ResponseEntity<Void>clearCart(
            @PathVariable UUID cartId

    )
    {

        cartService.clearCart(cartId);

        return ResponseEntity.noContent().build();

    }
    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<Map<String,String>> handlerCartNotFound(){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error","Cart not found"));
    }
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Map<String,String>>handleProductNotFound(){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error","Product not found in the cart"));
    }
}
