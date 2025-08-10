package me.tehpicix.rusherhack.chatfilter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.rusherhack.client.api.RusherHackAPI;

public class Filter {

	protected static List<String> expressions = new ArrayList<>();

	public static final File file = RusherHackAPI.getConfigPath().resolve("chatfilter").resolve("filter.txt").toFile();

	public static void createFile(File file) {
		if (!file.exists()) {
			try {
				file.getParentFile().mkdirs();
				file.createNewFile();
			} catch (java.io.IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void loadExpressions(File file) {
		try {
			expressions =
			    Files.readAllLines(file.toPath())
			        .stream()
			        .filter(line -> !line.isEmpty() && !line.startsWith("#"))
			        .map(String::trim)
			        .map(String::toLowerCase)
			        .toList();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static boolean match(String message) {

		for (String expression : expressions) {

			Pattern customRegex = Pattern.compile(expression);
			Matcher customMatch = customRegex.matcher(message.toLowerCase());

			if (customMatch.find()) return true;
		}

		return false;
	}
}
