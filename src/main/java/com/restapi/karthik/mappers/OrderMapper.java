package com.restapi.karthik.mappers;


import com.restapi.karthik.dtos.OrderDTO;
import com.restapi.karthik.entities.Order;
import org.mapstruct.Mapper;
import org.springframework.data.domain.jaxb.SpringDataJaxb;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderDTO toOrderDto(Order order);
}
