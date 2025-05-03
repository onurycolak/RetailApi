package com.onur.retail.api.response;

import com.onur.retail.domain.Coupon;
import com.onur.retail.domain.CouponType;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public class CouponResponse {
    private UUID id;
    private String couponCode;
    private CouponType couponType; // flat or percentage
    private BigDecimal discountAmount;
    private Instant expiryDate;
    private Boolean isUsed;

    public static CouponResponse from(Coupon coupon) {
        CouponResponse response = new CouponResponse();

        response.id = coupon.getId();
        response.couponCode = coupon.getCouponCode();
        response.couponType = coupon.getCouponType();
        response.discountAmount = coupon.getDiscountAmount();
        response.expiryDate = coupon.getExpiryDate();
        response.isUsed = coupon.getUsed();

        return response;
    }

    public UUID getId() {
        return id;
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

    public Boolean getUsed() {
        return isUsed;
    }
}
