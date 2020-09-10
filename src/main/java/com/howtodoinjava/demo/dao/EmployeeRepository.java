package com.howtodoinjava.demo.dao;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;

import com.howtodoinjava.demo.model.Employee;

import reactor.core.publisher.Flux;
 
public interface EmployeeRepository extends ReactiveMongoRepository<Employee, Integer> {
    @Query("{ 'name': ?0 }")
    Flux<Employee> findByName(final String name);

    // MongoDB Capped Collections: 
    // https://docs.mongodb.com/manual/core/capped-collections/
    @Tailable
    Flux<Employee> findWithTailableCursorBy();
}