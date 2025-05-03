package com.onur.retail.api.response;

import com.onur.retail.domain.Cart;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class CartResponse {
    private CouponResponse coupon;
    private List<CartItemResponse> items;
    private UUID id;
    private BigDecimal totalAmount;
    private BigDecimal originalTotalAmount;
    private BigDecimal shippingAmount;
    private BigDecimal totalWithShippingAmount;

    public static CartResponse from(Cart cart) {
        CartResponse response = new CartResponse();

        response.id = cart.getId();
        response.coupon = (cart.getCoupon() != null) ? CouponResponse.from(cart.getCoupon()) : null;
        response.totalAmount = cart.getTotalAmount();
        response.originalTotalAmount = cart.getTotalOriginalAmount();
        response.totalWithShippingAmount = cart.getTotalWithShipping();
        response.shippingAmount = cart.getShippingAmount();
        response.items = cart.getCartItems().stream()
                .map(CartItemResponse::from)
                .toList();

        return response;
    }

    public CouponResponse getCoupon() {
        return coupon;
    }

    public List<CartItemResponse> getItems() {
        return items;
    }

    public UUID getId() {
        return id;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public BigDecimal getOriginalTotalAmount() {
        return originalTotalAmount;
    }

    public BigDecimal getShippingAmount() {
        return shippingAmount;
    }

    public BigDecimal getTotalWithShippingAmount() {
        return totalWithShippingAmount;
    }
}
