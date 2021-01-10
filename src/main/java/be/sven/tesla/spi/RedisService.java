package be.sven.tesla.spi;

import be.sven.tesla.model.Token;
import be.sven.tesla.spi.redis.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RedisService {

    private TokenRepository repository;

    @Autowired
    public RedisService(TokenRepository tokenRepository) {
        this.repository = tokenRepository;
    }

    public Optional<Token> getTokenFor(String email) {
        return getTokenFor(email.hashCode());
    }

    public Optional<Token> getTokenFor(int id) {
        return repository.findById(id);
    }

    public Token saveToken(Token token) {
        return repository.save(token);
    }
}
