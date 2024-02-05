package com.vinsguru.userservice.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.vinsguru.userservice.entity.User;

@Repository
public interface UserRepository extends ReactiveMongoRepository<User, String> {
}
