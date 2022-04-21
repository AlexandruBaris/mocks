package com.endava.internship.mocking.service;

import com.endava.internship.mocking.model.Status;
import com.endava.internship.mocking.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;


class BasicValidationServiceTest {

    private final BasicValidationService service = new BasicValidationService();

    @Test
    void checkInvalidAmount() {
        Assertions.assertThrows(IllegalArgumentException.class, ()->service.validateAmount(null));
        Assertions.assertThrows(IllegalArgumentException.class, ()->service.validateAmount(0.00));
    }

    @Test
    void checkValidAmount(){
        Assertions.assertDoesNotThrow(()-> service.validateAmount(200.00));
    }

    @Test
    void checkInvalidPaymentId() {
        Assertions.assertThrows(IllegalArgumentException.class, ()->service.validatePaymentId(null));
    }

    @Test
    void checkValidPaymentId(){
        Assertions.assertDoesNotThrow(()->service.validatePaymentId(UUID.randomUUID()));
    }

    @Test
    void checkInvalidUserId() {
        Assertions.assertThrows(IllegalArgumentException.class, ()->service.validateUserId(null));
    }

    @Test
    void checkValidUserId(){
        Assertions.assertDoesNotThrow(()->service.validateUserId(1));
    }

    @Test
    void checkInvalidUserWithStatusInactive() {
        User user = new User(1,"User", Status.INACTIVE);
        Assertions.assertThrows(IllegalArgumentException.class, ()->service.validateUser(user));
    }

    @Test
    void checkValidUserWithStatusActive(){
        User user = new User(1,"User",Status.ACTIVE);
        Assertions.assertDoesNotThrow(()->service.validateUser(user));
    }

    @Test
    void checkInvalidMessage() {
        Assertions.assertThrows(IllegalArgumentException.class, ()->service.validateMessage(null));
    }

    @Test
    void checkValidMessage(){
        Assertions.assertDoesNotThrow(()->service.validateMessage("message"));
    }
}