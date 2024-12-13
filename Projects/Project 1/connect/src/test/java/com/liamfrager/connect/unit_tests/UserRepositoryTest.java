package com.liamfrager.connect.unit_tests;

import com.liamfrager.connect.entity.User;
import com.liamfrager.connect.repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    public void setUp() {
        user = TestData.generateUser();
        userRepository.save(user);
    }

    @Test
    public void testFindByUsername() {
        Optional<User> foundUser = userRepository.findByUsername("testuser");

        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getUsername()).isEqualTo("testuser");
    }

    @Test
    public void testFindByUsernameAndPassword() {
        Optional<User> foundUser = userRepository.findByUsernameAndPassword("testuser", "password");

        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getUsername()).isEqualTo("testuser");
    }
}