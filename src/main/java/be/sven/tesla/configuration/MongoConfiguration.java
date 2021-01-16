package be.sven.tesla.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "be.sven.tesla.data.mongo")
public class MongoConfiguration {
}
