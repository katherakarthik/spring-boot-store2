package com.restapi.karthik.services;

import com.restapi.karthik.dtos.CartDTO;
import com.restapi.karthik.dtos.CartItemDTO;
import com.restapi.karthik.entities.Cart;
import com.restapi.karthik.exceptions.CartNotFoundException;
import com.restapi.karthik.exceptions.ProductNotFoundException;
import com.restapi.karthik.mappers.CartMapper;
import com.restapi.karthik.repositories.CartRepository;
import com.restapi.karthik.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor

public class CartService {
    private CartRepository cartRepository;
    private CartMapper cartMapper;
    private ProductRepository productRepository;
    public CartDTO createCart(){
        var cart = new Cart();


        cartRepository.save(cart);

        return cartMapper.toDTO(cart);
    }
    public CartItemDTO addToCart(UUID cartId, Long productId){

        var cart = cartRepository.getCartWithItems(cartId).orElse(null);
        if(cart == null){
            throw new CartNotFoundException();
        }

        var product = productRepository.findById(Math.toIntExact(productId)).orElse(null);

        if(product == null){
            throw new ProductNotFoundException();
        }

        var cartItem = cart.addItem(product);

        cartRepository.save(cart);


        return cartMapper.toDTO(cartItem);
    }
    public CartDTO getCart(UUID cartId){
        val cart = cartRepository.getCartWithItems(cartId).orElse(null);
        if(cart == null){
            throw new CartNotFoundException();
        }
        return  cartMapper.toDTO(cart);

    }
    public CartItemDTO updateItem(UUID cartId, Long productId, Integer quantity){
        var cart = cartRepository.getCartWithItems(cartId).orElse(null);

        if(cart == null){
            throw new CartNotFoundException();
        }
        var cartItem = cart.getItem(productId);
        if(cartItem==null){
            throw new ProductNotFoundException();
        }
        cartItem.setQuantity(quantity);
        cartRepository.save(cart);

        return cartMapper.toDTO(cartItem);
    }
    public void removeItem(UUID cartId,Long productId){
        var cart =cartRepository.getCartWithItems(cartId).orElse(null);
        if(cart == null){
           throw new CartNotFoundException();
        }
        cart.removeItem(productId);
        cartRepository.save(cart);
    }
    public void clearCart(UUID cartId){
        var cart = cartRepository.getCartWithItems(cartId).orElse(null);
        if(cart == null){
            throw new  CartNotFoundException();
        }
        cart.clear();
        cartRepository.save(cart);
    }
}
