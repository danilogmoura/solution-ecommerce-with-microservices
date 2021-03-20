package com.dmg.dio.ecommerce.payment.service;

import com.dgm.dio.ecommerce.checkout.event.CheckoutCreatedEvent;
import com.dmg.dio.ecommerce.payment.entity.PaymentEntity;

import java.util.Optional;

public interface PaymentService {

    Optional<PaymentEntity> create(CheckoutCreatedEvent checkoutCreatedEvent);
}
