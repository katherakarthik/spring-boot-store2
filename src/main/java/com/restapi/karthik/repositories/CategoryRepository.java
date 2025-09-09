package com.restapi.karthik.repositories;

import com.restapi.karthik.entities.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Integer> {
}
