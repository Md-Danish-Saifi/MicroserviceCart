package com.user_service.Model;



public class CartModel {

	private long id;
	
	private String title;
	
	private int price;
	
	private String description;
	
	private String image;
	
	private long userId;
	public CartModel(long id, String title, int price, String description, long userId,String image) {
		super();
		this.id = id;
		this.title = title;
		this.price = price;
		this.description = description;
		this.userId = userId;
		this.image = image;
	}
	public CartModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	
	
}
