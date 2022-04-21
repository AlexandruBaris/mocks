package com.endava.internship.mocking.service;

import com.endava.internship.mocking.model.Payment;
import com.endava.internship.mocking.model.Status;
import com.endava.internship.mocking.model.User;
import com.endava.internship.mocking.repository.PaymentRepository;
import com.endava.internship.mocking.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
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

    @Captor
    ArgumentCaptor<Payment> paymentArgumentCaptor;


    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        paymentRepository = mock(PaymentRepository.class);
        validationService = mock(ValidationService.class);
        service = new PaymentService(userRepository,paymentRepository,validationService);

    }

    @Test
    void createPayment() {
        Payment actual = new Payment(1,200.00,"User");
        User user = new User(1,"User",Status.ACTIVE);

        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(paymentRepository.save(any())).thenReturn(actual);
        Payment expected = service.createPayment(user.getId(),200.00);

        verify(validationService).validateUserId(any());
        verify(validationService).validateAmount(anyDouble());
        verify(validationService).validateUser(any());
        verify(userRepository).findById(anyInt());

        assertThat(expected.getMessage()).isEqualTo(actual.getMessage());
        assertThat(expected.getUserId()).isEqualTo(actual.getUserId());
        assertThat(expected.getAmount()).isEqualTo(actual.getAmount());

    }

    @Test
    void comparingExpectedAndActualPayment(){
        Payment actual = new Payment(1,200.00,"User");
        User user = new User(1,"User",Status.ACTIVE);

        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        when(paymentRepository.save(any())).thenReturn(actual);
        Payment expected = service.createPayment(user.getId(),200.00);
        paymentArgumentCaptor = ArgumentCaptor.forClass(Payment.class);
        verify(paymentRepository).save(paymentArgumentCaptor.capture());

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void createPaymentThrowExceptionIfUserIsNotFound(){
        when(userRepository.findById(1)).thenThrow(new NoSuchElementException());
        Assertions.assertThrows(NoSuchElementException.class,()->service.createPayment(1,200.00));

    }

    @Test
    void editMessage() {
        Payment payment = new Payment(1,200.00,"");
        Payment newPaymentMessage = Payment.copyOf(payment);
        newPaymentMessage.setMessage("Message");

        when(paymentRepository.editMessage(any(),anyString())).thenReturn(newPaymentMessage);

        Payment expected = service.editPaymentMessage(payment.getPaymentId(),"Message");

        verify(validationService).validateMessage(anyString());
        verify(validationService).validatePaymentId(payment.getPaymentId());
        verify(paymentRepository).editMessage(payment.getPaymentId(),"Message");

        assertThat(expected).isEqualTo(newPaymentMessage);
    }

    @Test
    void getAllByAmountExceeding() {
        Payment p1 = new Payment(1,200.00,"");
        Payment p2 = new Payment(1,200.00,"");
        Payment p3 = new Payment(3,50.00,"");
        Payment p4 = new Payment(1,100.00,"");
        List<Payment> list = new ArrayList<>();
        list.add(p1);
        list.add(p2);
        list.add(p3);
        list.add(p4);

        List<Payment> expected = new ArrayList<>();
        expected.add(p1);
        expected.add(p2);

        when(paymentRepository.findAll()).thenReturn(list);

        assertThat(service.getAllByAmountExceeding(100.00)).isEqualTo(expected);
    }
}
