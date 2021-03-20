package com.dgm.dio.ecommerce.checkout.service;

import com.dgm.dio.ecommerce.checkout.entity.CheckoutEntity;
import com.dgm.dio.ecommerce.checkout.repository.CheckoutRepository;
import com.dgm.dio.ecommerce.checkout.resource.checkout.CheckoutRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CheckoutServiceImpl implements CheckoutService {

    private final CheckoutRepository checkoutRepository;

    @Override
    public Optional<CheckoutEntity> create(CheckoutRequest checkoutRequest) {
        return checkoutRequest;
    }
}
