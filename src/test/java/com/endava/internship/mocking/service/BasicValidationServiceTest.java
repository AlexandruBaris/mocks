package com.endava.internship.mocking.service;

import com.endava.internship.mocking.model.Status;
import com.endava.internship.mocking.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class BasicValidationServiceTest {

    private BasicValidationService service;

    @BeforeEach
    void setup(){
         service = new BasicValidationService();
    }

    @Test
    void validateAmount() {
        Assertions.assertThrows(IllegalArgumentException.class, ()->service.validateAmount(null));
        Assertions.assertThrows(IllegalArgumentException.class, ()->service.validateAmount(0.00));
    }

    @Test
    void validatePaymentId() {
        Assertions.assertThrows(IllegalArgumentException.class, ()->service.validatePaymentId(null));
    }

    @Test
    void validateUserId() {
        Assertions.assertThrows(IllegalArgumentException.class, ()->service.validateUserId(null));
    }

    @Test
    void validateUser() {
        User user = new User(1,"User", Status.INACTIVE);
        Assertions.assertThrows(IllegalArgumentException.class, ()->service.validateUser(user));
    }

    @Test
    void validateMessage() {
        Assertions.assertThrows(IllegalArgumentException.class, ()->service.validateMessage(null));
    }
}