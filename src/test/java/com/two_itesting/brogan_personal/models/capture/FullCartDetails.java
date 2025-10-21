package com.two_itesting.brogan_personal.models.capture;

import java.math.BigDecimal;

public record FullCartDetails (
        BigDecimal firstProductSubtotal,
        BigDecimal subtotal,
        BigDecimal couponDeduction,
        BigDecimal shippingCost,
        BigDecimal finalTotal
) {}