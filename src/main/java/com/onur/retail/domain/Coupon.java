package com.onur.retail.domain;

import com.onur.retail.util.Validate;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
public class Coupon {
    @Id
    @GeneratedValue
    private UUID id;
    private String couponCode;
    @Enumerated(EnumType.STRING)
    private CouponType couponType; // flat or percentage
    private BigDecimal discountAmount;
    private Instant expiryDate;
    @OneToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;
    private Boolean isUsed = false;

    public Coupon(){}

    public Coupon(
            String couponCode,
            CouponType couponType,
            BigDecimal discountAmount,
            Instant expiryDate
    ) {
        Validate.validateBigDecimal(discountAmount);

        if (couponType == null || couponCode == null || expiryDate == null) {
            throw new IllegalArgumentException("Provided value(s) must be non-null");
        }

        if (couponCode.isBlank()) {
            throw new IllegalArgumentException("couponCode must be non-blank");
        }

        if (!expiryDate.isAfter(Instant.now())) {
            throw new IllegalArgumentException("expiryDate should be later than current date");
        }

        this.couponType = couponType;
        this.discountAmount = discountAmount;
        this.couponCode =  couponCode;
        this.expiryDate = expiryDate;
    }

    public BigDecimal calculateDiscountedTotal(BigDecimal totalAmount) {
        return couponType == CouponType.PERCENTAGE ?
                totalAmount.multiply(discountAmount) :
                totalAmount.subtract(discountAmount);
    };

    public Boolean isCouponValid() {
        return !isUsed && expiryDate.isAfter(Instant.now()) && this.cart == null;
    }

    public UUID getId() {
        return id;
    }

    public CouponType getCouponType() {
        return couponType;
    }

    public Instant getExpiryDate() {
        return expiryDate;
    }

    public Boolean getUsed() {
        return isUsed;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
        this.isUsed = (cart != null);
    }

    public String getCouponCode() {
        return couponCode;
    }

    public Cart getCart() {
        return cart;
    }
}
