package be.sven.tesla.data.mongo;

import be.sven.tesla.core.DriveState;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface DriveStateRepositoy extends MongoRepository<DriveState, UUID> {
}
