package edu.tamu.scholars.middleware.auth.model.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;

import edu.tamu.scholars.middleware.auth.model.User;

public interface UserRepo extends JpaRepository<User, Long> {

    @RestResource(exported = false)
    public Optional<User> findByEmail(String email);

    @RestResource(exported = false)
    public boolean existsByEmail(String email);

}
