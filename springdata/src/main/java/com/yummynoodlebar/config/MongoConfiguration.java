package com.yummynoodlebar.config;

import com.mongodb.Mongo;
import com.yummynoodlebar.persistence.repository.MenuItemRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.net.UnknownHostException;

@Configuration
@EnableMongoRepositories(basePackages = "com.yummynoodlebar.persistence.repository",
      includeFilters = @ComponentScan.Filter(value = {MenuItemRepository.class}, type = FilterType.ASSIGNABLE_TYPE))
public class MongoConfiguration {

  public @Bean
  MongoTemplate mongoTemplate(Mongo mongo) throws UnknownHostException {
    return new MongoTemplate(mongo, "yummynoodle");
  }

  public @Bean Mongo mongo() throws UnknownHostException {
    return new Mongo("localhost");
  }
}
