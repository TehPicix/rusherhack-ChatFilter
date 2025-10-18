package me.tehpicix.rusherhack.chatfilter;

import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import org.rusherhack.client.api.feature.command.Command;
import org.rusherhack.core.command.annotations.CommandExecutor;

public class ChatCommand extends Command {

	public ChatCommand() {
		super("ChatFilter", "Add chat filter expressions");
	}

	@CommandExecutor(subCommand = "add")
	@CommandExecutor.Argument("string")
	private String addToList(String string) {

		// Test valid regex
		try {
			java.util.regex.Pattern.compile(string);
		} catch (java.util.regex.PatternSyntaxException e) {
			return "Invalid regex pattern: " + e.getDescription();
		}

		// Add to expressions
		Filter.expressions.add(string);

		// Append to file
		try {
			Files.writeString(Main.CONFIG_DIR.resolve("filter.txt"), System.lineSeparator() + string, StandardOpenOption.APPEND);
		} catch (Exception e) {
			e.printStackTrace();
			return "Failed to add expression to file.";
		}

		return "Added " + string;
	}

	@CommandExecutor(subCommand = "remove")
	@CommandExecutor.Argument("string")
	private String removeFromList(String string) {

		// Remove from expressions
		if (!Filter.expressions.remove(string)) {
			return "Expression not found in list.";
		}

		// Rewrite file
		try {
			StringBuilder sb = new StringBuilder();
			for (String expr : Filter.expressions) {
				sb.append(expr).append(System.lineSeparator());
			}
			Files.writeString(Main.CONFIG_DIR.resolve("filter.txt"), sb.toString().trim());
		} catch (Exception e) {
			e.printStackTrace();
			return "Failed to remove expression from file.";
		}

		return "Removed " + string;
	}

	@CommandExecutor(subCommand = "list")
	private String listExpressions() {
		if (Filter.expressions.isEmpty()) {
			return "No expressions in the filter list.";
		}
		StringBuilder sb = new StringBuilder("Current filter expressions:");
		for (String expr : Filter.expressions) {
			sb.append(System.lineSeparator()).append("- ").append(expr);
		}
		return sb.toString();
	}
}