package project.reactor.sec05;

import project.reactor.courseutil.Util;
import project.reactor.sec05.assignment.InventoryService;
import project.reactor.sec05.assignment.OrderService;
import project.reactor.sec05.assignment.RevenueService;

public class Lec06Assignment {

	public static void main(String[] args) {

		OrderService orderService = new OrderService();
		RevenueService revenueService = new RevenueService();
		InventoryService inventoryService = new InventoryService();

		//revenue and inv - observe the order stream
		orderService.orderStream().subscribe(revenueService.subscribeOrderStream());
		orderService.orderStream().subscribe(inventoryService.subscribeOrderStream());

		inventoryService.inventoryStream()
				.subscribe(Util.subscriber("inventory"));

		revenueService.revenueStream()
				.subscribe(Util.subscriber("revenue"));

		Util.sleepSeconds(60);

	}

}
