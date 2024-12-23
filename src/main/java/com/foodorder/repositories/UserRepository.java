package com.foodorder.repositories;

import org.springframework.data.repository.CrudRepository;
import com.foodorder.models.Users;

public interface UserRepository extends CrudRepository<Users,Long> {

}
