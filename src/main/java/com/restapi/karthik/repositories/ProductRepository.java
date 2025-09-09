package com.restapi.karthik.repositories;

import com.restapi.karthik.entities.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    @EntityGraph(attributePaths = "category")
    List<Product> findByCategory_Id(Byte category_id);

    @EntityGraph(attributePaths = "category")
    @Query("select p from Product p ")
    List<Product>findAllWithCategory();
}
