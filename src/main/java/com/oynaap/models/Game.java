package com.oynaap.models;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.sql.ResultSet;
import java.sql.SQLException;



public class Game {

    private Integer gm_id;
    private byte[] image;
    private String name;
    private String description;
    private Double price;
    private Integer category_id;
    private String category;

    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public Integer getGm_id() {
        return gm_id;
    }
    public void setGm_id(Integer gm_id) {
        this.gm_id = gm_id;
    }
    public byte[] getImage() {
        return image;
    }
    public void setImage(byte[] image) {
        this.image = image;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
    public Integer getCategory_id() {
        return category_id;
    }
    public void setCategory_id(Integer category_id) {
        this.category_id = category_id;
    }

    public static Game convert(SqlRowSet rs)  throws SQLException{
        Game game = new Game();
        game.setName(rs.getString("name"));
        game.setDescription(rs.getString("description"));
        game.setPrice(rs.getDouble("price"));
        game.setCategory_id(rs.getInt("category_id"));
        game.setGm_id(rs.getInt("gm_id"));

        return game;
    }

    public static Game populateImage(ResultSet rs) throws SQLException{
        final Game game = new Game();

        game.setImage(rs.getBytes("image"));
        game.setName(rs.getString("name"));
        game.setDescription(rs.getString("description"));
        game.setPrice(rs.getDouble("price"));
        game.setCategory_id(rs.getInt("category_id"));
        game.setGm_id(rs.getInt("gm_id"));
        return game;
    }

}
