package project.reactor.sec02;

import java.util.List;

import project.reactor.courseutil.Util;
import project.reactor.sec02.helper.NameGenerator;
import reactor.core.publisher.Flux;

public class Lec07FluxVsList {

	public static void main(String[] args) {

		List<String> nameList = NameGenerator.getNameList(5);
		System.out.println(nameList);

		Flux<String> nameFlux = NameGenerator.getNameFlux(5);
		nameFlux.subscribe(Util.onNext());

	}

}
