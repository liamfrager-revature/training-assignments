package com.liamfrager.connect.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.liamfrager.connect.entity.User;

/**
 * A JPA repository for users.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Get the user with the given username.
     * @param username The username of the user to return.
     * @return An <code>Optional</code> of the user's user information.
     */
    public Optional<User> findByUsername(String username);

    /**
     * Get the user with the given username and password.
     * @param username The username of the user to return.
     * @param password The password of the user to return.
     * @return An <code>Optional</code> of the user's user information.
     */
    public Optional<User> findByUsernameAndPassword(String username, String password);
}
