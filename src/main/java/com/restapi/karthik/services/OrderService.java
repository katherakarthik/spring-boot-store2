package com.restapi.karthik.services;

import com.restapi.karthik.dtos.ErrorDTO;
import com.restapi.karthik.dtos.OrderDTO;
import com.restapi.karthik.exceptions.OrderNotFoundException;
import com.restapi.karthik.mappers.OrderMapper;
import com.restapi.karthik.repositories.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
@AllArgsConstructor
@Service
public class OrderService {

    private final AuthService authService;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;



    public List<OrderDTO> getAllOrders() {
        var user = authService.getCurrentUser();
        var orders = orderRepository.getOrdersByCustomer(user);
        return orders.stream().map(orderMapper::toOrderDto).toList();
    }

    public OrderDTO getOrder(long orderId) {
       var order =  orderRepository
               .getOrderWithItems(orderId).orElseThrow(OrderNotFoundException::new);
       authService.getCurrentUser();
       var user = authService.getCurrentUser();
       if(!order.getCustomer().getId().equals(user.getId())) {
           throw new AccessDeniedException("You dont have access to this order");

       }
       return orderMapper.toOrderDto(order);
    }
    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<Void> handleOrderNotFoundException() {

        return ResponseEntity.notFound().build();

    }
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorDTO>handleEAccessDenied(Exception e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ErrorDTO(e.getMessage()));
    }
}
