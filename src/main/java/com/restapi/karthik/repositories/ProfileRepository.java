package com.restapi.karthik.repositories;

import com.restapi.karthik.entities.Profile;
import org.springframework.data.repository.CrudRepository;

public interface ProfileRepository extends CrudRepository<Profile, Integer> {
}
