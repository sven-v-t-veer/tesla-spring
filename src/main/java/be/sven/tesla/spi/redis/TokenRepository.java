package be.sven.tesla.spi.redis;

import be.sven.tesla.model.Token;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends CrudRepository <Token, Integer> {
}
