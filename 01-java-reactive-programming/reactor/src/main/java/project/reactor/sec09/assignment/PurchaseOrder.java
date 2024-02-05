package project.reactor.sec09.assignment;

import lombok.Data;
import lombok.ToString;
import project.reactor.courseutil.Util;

@Data
@ToString
public class PurchaseOrder {

	private String item;
	private Double price;
	private String category;

	public PurchaseOrder() {
		this.item = Util.faker().commerce().productName();
		this.price = Double.parseDouble(Util.faker().commerce().price());
		this.category = Util.faker().commerce().department();
	}

}
