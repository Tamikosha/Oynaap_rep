package com.oynaap.models;

import org.springframework.jdbc.support.rowset.SqlRowSet;

public class Cart {

    private Integer cart_id;
    private String payment_status;
    private Double price;
    private Integer quantity;
    private String status;
    private Integer gm_id;
    private String username;
    private Integer order_id;
    private String Game_name;
    
    public Integer getCart_id() {
        return cart_id;
    }
    public void setCart_id(Integer cart_id) {
        this.cart_id = cart_id;
    }
    public String getPayment_status() {
        return payment_status;
    }
    public void setPayment_status(String payment_status) {
        this.payment_status = payment_status;
    }
    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public Integer getGm_id() {
        return gm_id;
    }
    public void setGm_id(Integer game_id) {
        this.gm_id = game_id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public Integer getOrder_id() {
        return order_id;
    }
    public void setOrder_id(Integer order_id) {
        this.order_id = order_id;
    }
    public String getGame_name() {
        return Game_name;
    }
    public void setGame_name(String Game_name) {
        this.Game_name = Game_name;
    }

    public static Cart convert(SqlRowSet rs){
        Cart cart = new Cart();
        cart.setPrice(rs.getDouble("price"));
        cart.setQuantity(rs.getInt("quantity"));
        cart.setGame_name(rs.getString("Game_name"));
        cart.setStatus(rs.getString("status"));
        cart.setCart_id(rs.getInt("cart_id"));
        return cart;
    }






}
