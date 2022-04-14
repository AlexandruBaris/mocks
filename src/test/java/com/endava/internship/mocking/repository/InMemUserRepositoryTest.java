package com.endava.internship.mocking.repository;

import com.endava.internship.mocking.model.Status;
import com.endava.internship.mocking.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


class InMemUserRepositoryTest {

    @Test
    void findById() {
        User user = new User(1, "John", Status.ACTIVE);
        Optional<User> optionalUser = Optional.of(user);
        InMemUserRepository userRepository = new InMemUserRepository();

        assertThat(userRepository.findById(1)).isPresent();
        assertThat(userRepository.findById(10)).isNotPresent();
        assertThat(userRepository.findById(1)).isEqualTo(optionalUser);
        Assertions.assertThrows(IllegalArgumentException.class, ()-> userRepository.findById(null));
    }
}