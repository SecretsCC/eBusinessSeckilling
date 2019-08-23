package com.seckillingProject.service.model;

import java.math.BigDecimal;

public class OrderModel {
    //transaction number follow some rules
    private String id;

    //user id
    private Integer userId;

    //item id
    private Integer itemId;

    //if not empty, promo item exist
    private Integer promoId;

    //unit price,if pormoId not empty, price is promo item price
    private BigDecimal itemPrice;

    //count
    private Integer amount;

    //how much, same to itemPrice
    private BigDecimal orderPrice;

    public Integer getPromoId() {
        return promoId;
    }

    public void setPromoId(Integer promoId) {
        this.promoId = promoId;
    }

    public BigDecimal getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(BigDecimal itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public BigDecimal getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(BigDecimal orderAmount) {
        this.orderPrice = orderAmount;
    }
}
