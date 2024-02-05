package project.reactor.sec01;

import project.reactor.courseutil.Util;
import project.reactor.sec01.assignment.FileService;

public class Lec09AssignmentDemo {

	public static void main(String[] args) {

		FileService.read("file01.txt")
				.subscribe(
						Util.onNext(),
						Util.onError(),
						Util.onComplete()
				);

		FileService.write("file03.txt", "This is file3")
				.subscribe(
						Util.onNext(),
						Util.onError(),
						Util.onComplete()
				);

		FileService.delete("file03.txt")
				.subscribe(
						Util.onNext(),
						Util.onError(),
						Util.onComplete()
				);

		// StringBuilder sb = new StringBuilder();
		// for (int i = 1; i <= 10000; i++) {
		// 	sb.append("line").append(i);
		// 	if (i != 10000) {
		// 		sb.append("\n");
		// 	}
		// }
		//
		// FileService.write("file01.txt", sb.toString())
		// 	.subscribe(Util.subscriber());

	}

}
