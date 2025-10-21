package com.two_itesting.brogan_personal.models.site;

import java.math.BigDecimal;

public record Coupon(
        String code,
        BigDecimal discountValue
) {
}