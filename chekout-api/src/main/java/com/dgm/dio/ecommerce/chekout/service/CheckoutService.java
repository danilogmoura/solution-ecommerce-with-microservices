package com.dgm.dio.ecommerce.chekout.service;


import com.dgm.dio.ecommerce.chekout.entity.CheckoutEntity;
import com.dgm.dio.ecommerce.chekout.resource.checkout.CheckoutRequest;

import java.util.Optional;

public interface CheckoutService {

    Optional<CheckoutEntity> create(CheckoutRequest checkoutRequest);

    Optional<CheckoutEntity> updateStatus(String checkoutCode, CheckoutEntity.Status status);
}
