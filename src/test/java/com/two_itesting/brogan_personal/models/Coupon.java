package com.two_itesting.brogan_personal.models;

import java.math.BigDecimal;

public final record Coupon (
        String couponCode,
        BigDecimal discountValue
) {}