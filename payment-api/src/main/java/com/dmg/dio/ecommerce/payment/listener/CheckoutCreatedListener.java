package com.dmg.dio.ecommerce.payment.listener;


import com.dgm.dio.ecommerce.checkout.event.CheckoutCreatedEvent;
import com.dgm.dio.ecommerce.payment.event.PaymentCreatedEvent;
import com.dmg.dio.ecommerce.payment.service.PaymentService;
import com.dmg.dio.ecommerce.payment.streaming.CheckoutProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CheckoutCreatedListener {

    private final CheckoutProcessor checkoutProcessor;

    private final PaymentService paymentService;

    @StreamListener(CheckoutProcessor.INPUT)
    public void handler(CheckoutCreatedEvent checkoutCreatedEvent) {
        final var paymentEntity = paymentService
                .create(checkoutCreatedEvent)
                .orElseThrow(() -> new EmptyResultDataAccessException(1));

        final var paymentCreatedEvent = PaymentCreatedEvent.newBuilder()
                .setCheckoutCode(paymentEntity.getCheckoutCode())
                .setPaymentCode(paymentEntity.getCode())
                .build();
        
        checkoutProcessor.output().send(MessageBuilder.withPayload(paymentCreatedEvent).build());
    }
}
