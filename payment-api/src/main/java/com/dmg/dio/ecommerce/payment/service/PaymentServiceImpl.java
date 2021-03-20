package com.dmg.dio.ecommerce.payment.service;

import com.dgm.dio.ecommerce.checkout.event.CheckoutCreatedEvent;
import com.dmg.dio.ecommerce.payment.entity.PaymentEntity;
import com.dmg.dio.ecommerce.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    @Override
    public Optional<PaymentEntity> create(CheckoutCreatedEvent checkoutCreatedEvent) {
        final var paymentEntity = PaymentEntity.builder()
                .checkoutCode(checkoutCreatedEvent.getCheckoutCode())
                .code(UUID.randomUUID().toString())
                .build();

        paymentRepository.save(paymentEntity);

        return Optional.of(paymentEntity);
    }
}
