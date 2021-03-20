package com.dgm.dio.ecommerce.chekout.service;


import com.dgm.dio.ecommerce.chekout.entity.CheckoutEntity;
import com.dgm.dio.ecommerce.chekout.entity.CheckoutItemEntity;
import com.dgm.dio.ecommerce.chekout.entity.ShippingEntity;
import com.dgm.dio.ecommerce.chekout.repository.CheckoutRepository;
import com.dgm.dio.ecommerce.chekout.resource.checkout.CheckoutRequest;
import com.dgm.dio.ecommerce.chekout.streaming.CheckoutCreatedSource;
import com.hatanaka.ecommerce.checkout.event.CheckoutCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CheckoutServiceImpl implements CheckoutService {

    private final CheckoutRepository checkoutRepository;
    private final CheckoutCreatedSource checkoutCreatedSource;

    @Override
    public Optional<CheckoutEntity> create(CheckoutRequest checkoutRequest) {
        log.info("M=create, checkoutRequest={}", checkoutRequest);

        final var checkoutEntity = CheckoutEntity.builder()
                .code(UUID.randomUUID().toString())
                .status(CheckoutEntity.Status.CREATED)
                .saveAddress(checkoutRequest.getSaveAddress())
                .saveInformation(checkoutRequest.getSaveInfo())
                .shipping(ShippingEntity.builder()
                        .address(checkoutRequest.getAddress())
                        .complement(checkoutRequest.getComplement())
                        .country(checkoutRequest.getCountry())
                        .state(checkoutRequest.getState())
                        .cep(checkoutRequest.getCep())
                        .build())
                .build();
        checkoutEntity.setItems(checkoutRequest.getProducts()
                .stream()
                .map(product -> CheckoutItemEntity.builder()
                        .checkout(checkoutEntity)
                        .product(product)
                        .build())
                .collect(Collectors.toList()));

        final var entity = checkoutRepository.save(checkoutEntity);

        final var checkoutCreatedEvent = CheckoutCreatedEvent.newBuilder()
                .setCheckoutCode(entity.getCode())
                .setStatus(entity.getStatus().name())
                .build();
        checkoutCreatedSource.output().send(MessageBuilder.withPayload(checkoutCreatedEvent).build());

        return Optional.of(entity);
    }

    @Override
    public Optional<CheckoutEntity> updateStatus(String checkoutCode, CheckoutEntity.Status status) {
        final var checkoutEntity = checkoutRepository
                .findByCode(checkoutCode)
                .orElse(CheckoutEntity.builder().build());

        checkoutEntity.setStatus(CheckoutEntity.Status.APPROVED);

        return Optional.of(checkoutRepository.save(checkoutEntity));
    }
}
