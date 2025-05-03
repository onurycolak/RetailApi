package com.onur.retail.api.request;

import com.onur.retail.domain.CouponType;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.Instant;

public class CouponRequest {
    @NotBlank(message = "couponCode must be non-blank")
    private String couponCode;
    @NotNull(message = "coupon type must be no-null")
    private CouponType couponType;
    @NotNull(message = "discount amount must be non-null")
    @Positive(message = "discount amount must be positive value")
    private BigDecimal discountAmount;
    @NotNull(message = "expiryDate must be non-null")
    @Future(message = "expiryDate must be in the future")
    private Instant expiryDate;

    public CouponRequest() {}

    public CouponRequest(
            String couponCode,
            CouponType couponType,
            BigDecimal discountAmount,
            Instant expiryDate
    ) {
        this.couponType = couponType;
        this.discountAmount = discountAmount;
        this.couponCode =  couponCode;
        this.expiryDate = expiryDate;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public CouponType getCouponType() {
        return couponType;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public Instant getExpiryDate() {
        return expiryDate;
    }
}
