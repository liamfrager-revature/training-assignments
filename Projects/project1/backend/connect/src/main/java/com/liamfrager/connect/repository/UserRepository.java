package com.liamfrager.connect.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.liamfrager.connect.entity.User;

import jakarta.transaction.Transactional;

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
     * Get the user with the given email.
     * @param email The email of the user to return.
     * @return An <code>Optional</code> of the user's user information.
     */
    public Optional<User> findByEmail(String email);

    /**
     * Get the user with the given username and password.
     * @param username The username of the user to return.
     * @param password The password of the user to return.
     * @return An <code>Optional</code> of the user's user information.
     */
    public Optional<User> findByUsernameAndPassword(String username, String password);

    /**
     * Get the user with the given email and password.
     * @param email The email of the user to return.
     * @param password The password of the user to return.
     * @return An <code>Optional</code> of the user's user information.
     */
    public Optional<User> findByEmailAndPassword(String email, String password);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.pfp = :pfp WHERE u.id = :userID")
    public int setPfpByUserID(@Param("userID") long userID, @Param("pfp") byte[] pfp);


    @Modifying
    @Transactional
    @Query("UPDATE User u SET " +
           "u.username = COALESCE(:username, u.username ), " +
           "u.email = COALESCE(:email, u.email ), " +
           "u.password = COALESCE(:password, u.password ), " +
           "u.pfp = COALESCE(:pfp, u.pfp) " +
           "WHERE u.id = :userID")
    int updateUserDetails(@Param("userID") long userID, @Param("username") String username, @Param("email") String email, @Param("password") String password, @Param("pfp") byte[] pfp);

    @Query("SELECT u FROM User u WHERE LOWER(u.username) LIKE LOWER(CONCAT('%', :query, '%')) AND u.id != :currentUserID")
    List<User> searchByUsernameExcludingUser(@Param("query") String query, @Param("currentUserID") Long currentUserID);
}
