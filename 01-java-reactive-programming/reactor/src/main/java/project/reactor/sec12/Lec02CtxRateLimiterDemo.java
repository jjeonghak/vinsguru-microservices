package project.reactor.sec12;

import project.reactor.courseutil.Util;
import project.reactor.sec12.helper.BookService;
import project.reactor.sec12.helper.UserService;
import reactor.util.context.Context;

public class Lec02CtxRateLimiterDemo {

	public static void main(String[] args) {

		BookService.getBook()
				.repeat(2)
				.contextWrite(UserService.userCategoryContext())
				.contextWrite(Context.of("user", "sam"))
				.subscribe(Util.subscriber());

	}

}
