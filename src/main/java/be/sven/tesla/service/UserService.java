package be.sven.tesla.service;

import be.sven.tesla.core.AppUser;

import java.util.Optional;

public interface UserService {

    AppUser saveUser(AppUser user);
    Optional<AppUser> getUserForEmail(String email);
    Optional<AppUser> getUserForTeslaEmail(String email);
}
