package com.dgm.dio.ecommerce.checkout.service;

import com.dgm.dio.ecommerce.checkout.entity.CheckoutEntity;
import com.dgm.dio.ecommerce.checkout.resource.checkout.CheckoutRequest;

import java.util.Optional;

public interface CheckoutService {

    Optional<CheckoutEntity> create(CheckoutRequest checkoutRequest);
}
