package be.sven.tesla.data.sql;

import be.sven.tesla.core.AppUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<AppUser, String> {

    Optional<AppUser> findByEmail(String email);
    Optional<AppUser> findByTeslaEmail(String email);
}
