package com.prueba.authcarvajalmicroservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.prueba.carvajal.model.User;

@Repository
public interface UserRepository extends MongoRepository<User, String>{

}
