package com.restapi.karthik.repositories;

import com.restapi.karthik.entities.Order;
import com.restapi.karthik.entities.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @EntityGraph(attributePaths = "items.product")
    @Query("SELECT o from Order o  WHERE o.customer = :customer")
    List<Order> getOrdersByCustomer(@Param("customer") User user);

    @EntityGraph(attributePaths = "items.product")
    @Query("SELECT o from Order o Where o.id =:orderId")
    Optional<Order> getOrderWithItems(@Param("orderId")long orderId);
}