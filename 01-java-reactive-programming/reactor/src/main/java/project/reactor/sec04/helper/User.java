package project.reactor.sec04.helper;

import lombok.Data;
import lombok.ToString;
import project.reactor.courseutil.Util;

@Data
@ToString
public class User {

	private int userId;
	private String name;

	public User(int userId) {
		this.userId = userId;
		this.name = Util.faker().name().fullName();
	}

}
