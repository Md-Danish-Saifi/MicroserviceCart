package com.user_service.Model;
public class CatalogModel {
	
	private long id;
	
	private String title;
	
	private int price;
	
	private String description;
	
	private String image;
	public CatalogModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CatalogModel(long id, String title, int price, String description,String image) {
		super();
		this.id = id;
		this.title = title;
		this.price = price;
		this.description = description;
		this.image = image;
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
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	
	
	

}
