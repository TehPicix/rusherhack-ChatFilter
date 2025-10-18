package me.tehpicix.rusherhack.chatfilter;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Filter {

	public static Set<String> expressions = new HashSet<>();

	public static boolean match(String message) {
		if (Main.module.isToggled() == false) return false;
		for (String expression : expressions) {
			Pattern customRegex = Pattern.compile(expression, Module.caseInsensitive.getValue() ? Pattern.CASE_INSENSITIVE : 0);
			Matcher customMatch = customRegex.matcher(message);
			if (customMatch.find()) {
				if (Module.logBlockedMessages.getValue()) System.out.println("[ChatFilter] Blocked message: " + message + " | Matched expression: " + expression);
				return true;
			}
		}
		return false;
	}
}
