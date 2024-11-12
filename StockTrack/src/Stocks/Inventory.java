package Stocks;
import java.time.*;
public class Inventory {

	private int inventory_id;
	private int product_id;
	private int stock_level;
	private LocalDateTime last_updated;
	
	public Inventory(int inventory_id, int product_id, int stock_level) {
		super();
		this.inventory_id = inventory_id;
		this.product_id = product_id;
		this.stock_level = stock_level;
	}
	public int getInventory_id() {
		return inventory_id;
	}
	public void setInventory_id(int inventory_id) {
		this.inventory_id = inventory_id;
	}
	public int getProduct_id() {
		return product_id;
	}
	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}
	public int getStock_level() {
		return stock_level;
	}
	public void setStock_level(int stock_level) {
		this.stock_level = stock_level;
	}
	
	@Override
	public String toString() {
		return "Inventory [inventory_id=" + inventory_id + ", product_id=" + product_id + ", stock_level=" + stock_level + "]";
	} 
	
	
	
}
