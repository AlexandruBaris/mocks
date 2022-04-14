package com.endava.internship.mocking.service;

import com.endava.internship.mocking.model.Payment;
import com.endava.internship.mocking.model.Status;
import com.endava.internship.mocking.model.User;
import com.endava.internship.mocking.repository.PaymentRepository;
import com.endava.internship.mocking.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    private PaymentService service;
    private UserRepository userRepository;
    private PaymentRepository paymentRepository;
    private ValidationService validationService;


    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        paymentRepository = mock(PaymentRepository.class);
        validationService = mock(ValidationService.class);
        service = new PaymentService(userRepository,paymentRepository,validationService);



    }

    @Test
    void createPayment() {
        User user = new User(1,"User",Status.ACTIVE);

        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        service.createPayment(user.getId(),200.00);

        verify(validationService).validateUserId(any());
        verify(validationService).validateAmount(anyDouble());
        verify(validationService).validateUser(any());

    }

    @Test
    void editMessage() {
        Payment payment = new Payment(1,200.00,"");

        service.editPaymentMessage(payment.getPaymentId(),"Message");
        verify(validationService).validateMessage(anyString());
        verify(validationService).validatePaymentId(payment.getPaymentId());
        verify(paymentRepository).editMessage(payment.getPaymentId(),"Message");

    }

    @Test
    void getAllByAmountExceeding() {
        List<Payment> payments = new ArrayList<>();
        payments.add(new Payment(1,200.00,""));

        when(paymentRepository.findAll()).thenReturn(payments);
        assertThat(service.getAllByAmountExceeding(100.00)).isEqualTo(payments);

    }
}
