package com.oynaap.models;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.sql.ResultSet;
import java.sql.SQLException;


public class BlogPost {

	private Integer blog_id;
	private String title;
	private String author;
	private byte[] image;
	private String description;

	private Integer category_id;
	private String category;

	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public Integer getBlog_id() {
		return blog_id;
	}

	public byte[] getImage() {
		return image;
	}
	public void setImage(byte[] image) {
		this.image = image;
	}
	public void setBlog_id(Integer blog_id) {
		this.blog_id = blog_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}





	public Integer getCategory_id() {
		return category_id;
	}
	public void setCategory_id(Integer category_id) {
		this.category_id = category_id;
	}


	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}



	public static BlogPost convert(SqlRowSet rs)  throws SQLException{
		BlogPost blogpost = new BlogPost();
		blogpost.setTitle(rs.getString("title"));
		blogpost.setDescription(rs.getString("description"));
		blogpost.setAuthor(rs.getString("author"));
		blogpost.setBlog_id(rs.getInt("blog_id"));
		blogpost.setCategory_id(rs.getInt("category_id"));

		return blogpost;
	}
	public static BlogPost populateImage(ResultSet rs)  throws SQLException{
		BlogPost blogpost = new BlogPost();
		blogpost.setImage(rs.getBytes("image"));
		blogpost.setDescription(rs.getString("description"));
		blogpost.setTitle(rs.getString("title"));
		blogpost.setAuthor(rs.getString("author"));
		blogpost.setCategory_id(rs.getInt("category_id"));
		blogpost.setBlog_id(rs.getInt("blog_id"));

		return blogpost;
	}



}
