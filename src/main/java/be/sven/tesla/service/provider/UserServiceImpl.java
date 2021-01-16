package be.sven.tesla.service.provider;

import be.sven.tesla.core.AppUser;
import be.sven.tesla.data.sql.UserRepository;
import be.sven.tesla.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public AppUser saveUser(AppUser user) {
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public Optional<AppUser> getUserForEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    @Transactional
    public Optional<AppUser> getUserForTeslaEmail(String email) {
        return userRepository.findByTeslaEmail(email);
    }
}
