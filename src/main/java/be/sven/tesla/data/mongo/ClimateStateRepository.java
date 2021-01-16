package be.sven.tesla.data.mongo;

import be.sven.tesla.core.ClimateState;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface ClimateStateRepository extends MongoRepository<ClimateState, UUID> {
}
