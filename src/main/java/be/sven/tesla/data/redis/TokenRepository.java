package be.sven.tesla.data.redis;

import be.sven.tesla.core.Token;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends CrudRepository <Token, Integer> {
}
