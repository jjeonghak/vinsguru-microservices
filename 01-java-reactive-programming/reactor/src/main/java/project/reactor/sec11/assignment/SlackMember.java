package project.reactor.sec11.assignment;

import java.util.function.Consumer;

public class SlackMember {

	private String name;
	private Consumer<String> msgConsumer;

	public SlackMember(String name) {
		this.name = name;
	}

	String getName() {
		return name;
	}

	void receives(String msg) {
		System.out.println(msg);
	}

	public void says(String msg) {
		this.msgConsumer.accept(msg);
	}

	public void setMsgConsumer(Consumer<String> msgConsumer) {
		this.msgConsumer = msgConsumer;
	}
}
