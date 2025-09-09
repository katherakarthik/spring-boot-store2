package com.restapi.karthik.controllers;

import com.restapi.karthik.dtos.OrderDTO;
import com.restapi.karthik.mappers.OrderMapper;
import com.restapi.karthik.repositories.OrderRepository;
import com.restapi.karthik.services.AuthService;
import com.restapi.karthik.services.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;



    @GetMapping
    public List<OrderDTO> getAllOrders() {

       return orderService.getAllOrders();




    }
    @GetMapping("/{orderId}")
    public OrderDTO getOrder(@PathVariable("orderId") long orderId) {

       return  orderService.getOrder(orderId);


    }
}
