package Stocks;
import java.time.*;
public class InventoryAlerts {

	private int alert_id;
	private int product_id;
	private int alert_message;
	private  LocalDateTime alert_time;
	
	public InventoryAlerts(int alert_id, int product_id, int alert_message) {
		super();
		this.alert_id = alert_id;
		this.product_id = product_id;
		this.alert_message = alert_message;
	}

	public int getAlert_id() {
		return alert_id;
	}

	public void setAlert_id(int alert_id) {
		this.alert_id = alert_id;
	}

	public int getProduct_id() {
		return product_id;
	}

	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}

	public int getAlert_message() {
		return alert_message;
	}

	public void setAlert_message(int alert_message) {
		this.alert_message = alert_message;
	}

	@Override
	public String toString() {
		return "InventoryAlerts [alert_id=" + alert_id + ", product_id=" + product_id + ", alert_message="
				+ alert_message + "]";
	}
	
}
