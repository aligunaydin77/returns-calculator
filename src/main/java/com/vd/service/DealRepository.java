package com.vd.service;


import com.vd.model.Deal;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DealRepository extends MongoRepository<Deal,String> {
}
