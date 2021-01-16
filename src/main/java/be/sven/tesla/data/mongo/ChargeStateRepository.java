package be.sven.tesla.data.mongo;

import be.sven.tesla.core.ChargeState;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface ChargeStateRepository extends MongoRepository<ChargeState, UUID> {
}
