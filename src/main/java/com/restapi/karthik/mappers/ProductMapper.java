package com.restapi.karthik.mappers;

import com.restapi.karthik.dtos.ProductDTO;
import com.restapi.karthik.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(target = "category_id",source = "category.id")
    ProductDTO toDTO(Product product);
    Product toEntity(ProductDTO product);
    @Mapping(target = "id",ignore = true)
    void update(ProductDTO productDTO, @MappingTarget Product product);
}
