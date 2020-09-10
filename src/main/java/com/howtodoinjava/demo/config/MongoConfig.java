package com.howtodoinjava.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
 
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
 
@Configuration
@EnableReactiveMongoRepositories(basePackages = "com.howtodoinjava.demo.dao")
public class MongoConfig extends AbstractReactiveMongoConfiguration
{  
    @Value("${dbname}")
    private String dbName;
    
    @Value("${dbUrl}")
    private String dbUrl;
 
    @Override
    public MongoClient reactiveMongoClient() {
        //return MongoClients.create();
        return MongoClients.create(dbUrl);
    }
    
    @Override
    protected String getDatabaseName() {
        return dbName;
    }
 
    @Bean
    public ReactiveMongoTemplate reactiveMongoTemplate() {
        return new ReactiveMongoTemplate(reactiveMongoClient(), getDatabaseName());
    }
}