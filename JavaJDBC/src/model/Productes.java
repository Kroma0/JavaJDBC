package model;

import java.io.Serializable;
import java.util.Date;
import java.time.LocalDate;

public class Productes implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Integer idProduct;
    private String name;
    private Integer price;
    private Integer stock;
    private Date startCatalogue;
    private Date endingCatalogue;
    
	public Productes(Integer idProduct, String name, Integer price, Integer stock, Date startCatalogue,Date endingCatalogue) {
		super();
		this.idProduct = idProduct;
		this.name = name;
		this.price = price;
		this.stock = stock;
		this.startCatalogue = startCatalogue;
		this.endingCatalogue = endingCatalogue;
	}


	public Integer getIdProduct() {
		return idProduct;
	}



	public void setIdProduct(Integer idProduct) {
		this.idProduct = idProduct;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public Integer getPrice() {
		return price;
	}



	public void setPrice(Integer price) {
		this.price = price;
	}



	public Integer getStock() {
		return stock;
	}



	public void setStock(Integer stock) {
		this.stock = stock;
	}



	public Date getStartCatalogue() {
		return startCatalogue;
	}



	public void setStartCatalogue(Date startCatalogue) {
		this.startCatalogue = startCatalogue;
	}



	public Date getEndingCatalogue() {
		return endingCatalogue;
	}



	public void setEndingCatalogue(Date endingCatalogue) {
		this.endingCatalogue = endingCatalogue;
	}



	@Override
	public String toString() {
		return "Products [idProduct=" + idProduct + ", name=" + name + ", price=" + price + ", stock=" + stock
				+ ", startCatalogue=" + startCatalogue + ", endingCatalogue=" + endingCatalogue + "]";
	}
    
    
}
