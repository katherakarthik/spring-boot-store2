package com.restapi.karthik.repositories;

import com.restapi.karthik.entities.Address;
import org.springframework.data.repository.CrudRepository;

public interface AddressRepository extends CrudRepository<Address, Integer> {
}
