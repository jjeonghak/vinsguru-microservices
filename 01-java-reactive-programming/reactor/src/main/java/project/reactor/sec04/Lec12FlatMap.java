package project.reactor.sec04;

import java.time.Duration;

import project.reactor.courseutil.Util;
import project.reactor.sec04.helper.OrderService;
import project.reactor.sec04.helper.UserService;

public class Lec12FlatMap {

	public static void main(String[] args) {

		UserService.getUsers()
				.flatMap(user -> OrderService.getOrders(user.getUserId()))
				.subscribe(Util.subscriber());

		Util.sleepSeconds(60);

	}

}
