package model;

import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder={"number", "name", "price", "qty", "onOrder", "reOrder", "catId", "supId", "costPrice", "unit"})
public class ItemBean {
	
	private int number;
	private String name;
	private double price;
	private int qty;
	private int onOrder;
	private int reOrder;
	private int catId;
	private int supId;
	private double costPrice;
	private String unit;
	
	
	// Setters
	public void setNumber(int number) {
		this.number = number;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public void setOnOrder(int onOrder) {
		this.onOrder = onOrder;
	}

	public void setReOrder(int reOrder) {
		this.reOrder = reOrder;
	}

	public void setCatId(int catId) {
		this.catId = catId;
	}

	public void setSupId(int supId) {
		this.supId = supId;
	}

	public void setCostPrice(double costPrice) {
		this.costPrice = costPrice;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	// Getters
	public int getNumber() {
		return number;
	}
	
	public String getName() {
		return name;
	}
	
	public double getPrice() {
		return price;
	}

	public int getQty() {
		return qty;
	}

	public int getOnOrder() {
		return onOrder;
	}

	public int getReOrder() {
		return reOrder;
	}

	public int getCatId() {
		return catId;
	}

	public int getSupId() {
		return supId;
	}

	public double getCostPrice() {
		return costPrice;
	}

	public String getUnit() {
		return unit;
	}

}
