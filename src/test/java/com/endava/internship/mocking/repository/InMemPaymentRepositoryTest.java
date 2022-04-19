package com.endava.internship.mocking.repository;

import com.endava.internship.mocking.model.Payment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class InMemPaymentRepositoryTest {

    private InMemPaymentRepository paymentRepository;
    private Payment payment;
    private UUID uuid;


    @BeforeEach
    void setup(){
        paymentRepository = new InMemPaymentRepository();
        payment = new Payment(1,200.00,"TXN");
        uuid = payment.getPaymentId();
        paymentRepository.save(payment);

    }

    @Test
    void findById() {
        Optional<Payment> optional = Optional.of(payment);
        assertThat(paymentRepository.findById(uuid)).isPresent();
        assertThat(paymentRepository.findById(uuid)).isEqualTo(optional);
    }

    @Test
    void checkFindByIdWithNullThrowException(){
        Assertions.assertThrows(IllegalArgumentException.class, ()-> paymentRepository.findById(null));
    }

    @Test
    void findAll() {
        ArrayList<Payment> list = new ArrayList<>();
        list.add(payment);
        assertThat(paymentRepository.findAll()).isEqualTo(list);
    }

    @Test
    void save() {
        Payment payment1 = new Payment(2,300.00,"Txn");
        assertThat(paymentRepository.save(payment1)).isEqualTo(payment1);
    }

    @Test
    void saveNullPaymentThrowException(){
        Assertions.assertThrows(IllegalArgumentException.class, ()->paymentRepository.save(null));
    }

    @Test
    void saveAlreadyExistingPaymentThrowException(){
        Payment payment1 = new Payment(2,300.00,"Txn");
        paymentRepository.save(payment1);
        Assertions.assertThrows(IllegalArgumentException.class, ()->paymentRepository.save(payment1));
    }

    @Test
    void editMessage() {
        UUID id = payment.getPaymentId();
        Payment payment1 = payment;
        assertThat(paymentRepository.editMessage(id,"Message")).isEqualTo(payment1);
    }

    @Test
    void editMessagePassingNullThrowException(){
        Assertions.assertThrows(NoSuchElementException.class,()-> paymentRepository.editMessage(null,null));
    }
}