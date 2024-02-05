package project.reactor.sec01.assignment;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import reactor.core.publisher.Mono;

public class FileService {

	private static final Path PATH = Paths.get("src/main/resources/assignment/sec01");

	public static Mono<String> read(String filename) {
		return Mono.fromSupplier(() -> readFile(filename));
	}

	public static Mono<Void> write(String filename, String content) {
		return Mono.fromRunnable(() -> writeFile(filename, content));
	}

	public static Mono<Void> delete(String filename) {
		return Mono.fromRunnable(() -> deleteFile(filename));
	}

	private static String readFile(String filename) {
		try {
			return Files.readString(PATH.resolve(filename));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private static void writeFile(String filename, String content) {
		try {
			Files.writeString(PATH.resolve(filename), content);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private static void deleteFile(String filename) {
		try {
			Files.delete(PATH.resolve(filename));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
