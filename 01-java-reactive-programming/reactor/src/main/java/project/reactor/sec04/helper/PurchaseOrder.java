package project.reactor.sec04.helper;

import lombok.Data;
import lombok.ToString;
import project.reactor.courseutil.Util;

@Data
@ToString
public class PurchaseOrder {

	private String item;
	private String price;
	private int userId;

	public PurchaseOrder(int userId) {
		this.userId = userId;
		this.item = Util.faker().commerce().productName();
		this.price = Util.faker().commerce().price();
	}

}
