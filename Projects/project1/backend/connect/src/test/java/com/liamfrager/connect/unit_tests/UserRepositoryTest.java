package com.liamfrager.connect.unit_tests;

import com.liamfrager.connect.TestData;
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
        userRepository.deleteAll();
        user = userRepository.save(TestData.generateNewUser());
    }

    @Test
    public void testFindByUsername() {
        Optional<User> foundUser = userRepository.findByUsername(user.getUsername());

        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getUsername()).isEqualTo(user.getUsername());
    }

    @Test
    public void testFindByUsernameAndPassword() {
        Optional<User> foundUser = userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());

        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getUsername()).isEqualTo(user.getUsername());
        assertThat(foundUser.get().getPassword()).isEqualTo(user.getPassword());
    }
}