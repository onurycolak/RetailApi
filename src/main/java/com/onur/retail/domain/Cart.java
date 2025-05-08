package com.onur.retail.domain;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class Cart {
    @Id
    @GeneratedValue
    private UUID id;
    private BigDecimal shippingAmount = BigDecimal.ZERO;
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> cartItems = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;
    @OneToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;


    public Cart() {}

    public Cart(
        Customer customer
    ) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer must be non-null");
        }

        this.customer = customer;
        this.customer.setCart(this);
    }

    public UUID getId() {
        return id;
    }

    @Transient
    public BigDecimal getTotalAmount() {
        BigDecimal totalAmount = this.cartItems.stream().
                map(CartItem::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (coupon != null) {
            return coupon.calculateDiscountedTotal(totalAmount);
        }

        return totalAmount;
    }

    @Transient
    public BigDecimal getTotalWithShipping() {
        return getTotalAmount().add(shippingAmount);
    }

    @Transient
    public BigDecimal getTotalOriginalAmount() {
        return this.cartItems.stream().
                map(CartItem::getOriginalTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getShippingAmount() {
        return shippingAmount;
    }

    public void setShippingAmount(BigDecimal shippingAmount) {
        if (shippingAmount == null) {
            throw new IllegalArgumentException("shippingAmount must be non-null");
        }

        this.shippingAmount = shippingAmount;
    }

    public List<CartItem> getCartItems() {
        return this.cartItems;
    }

    public Coupon getCoupon() {
        return coupon;
    }

    public void setCoupon(Coupon coupon) {
        if (coupon != null && !coupon.isCouponValid()) {
            throw new IllegalArgumentException("Invalid coupon provided");
        }

        if (this.coupon != null && this.coupon.getCart() == this) {
            this.coupon.setCart(null);
        }

        this.coupon = coupon;

        if (coupon != null) {
            coupon.setCart(this); // safe bidirectional sync
        }
    }

    public Customer getCustomer() {
        return customer;
    }

    public void addItemToCart(CartItem item) {
        if (item == null) {
            throw new IllegalArgumentException("Item added to cart must be non-null");
        }

        cartItems.add(item);
        item.setCart(this);
    }

    public void removeItemFromCart(CartItem item) {
        if (item == null) {
            throw new IllegalArgumentException("Item to be removed from cart must be non-null");
        }

        cartItems.removeIf(cartItem -> cartItem.getId().equals(item.getId()));
    }

    public void clearCart() {
        cartItems.clear();
    }

    public void setCartItems(List<CartItem> cartItems) {
        if (cartItems == null) { // i left the empty case due to session changes, maybe the new logged in user might have empty cart
            throw new IllegalArgumentException("Cart items to be set as cart must be non-null");
        }

        this.cartItems = cartItems;

        cartItems.forEach((item) -> item.setCart(this));
    }
}
