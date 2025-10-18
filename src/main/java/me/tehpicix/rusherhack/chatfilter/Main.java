package me.tehpicix.rusherhack.chatfilter;

import java.nio.file.Files;
import java.nio.file.Path;
import org.rusherhack.client.api.RusherHackAPI;
import org.rusherhack.client.api.plugin.Plugin;

public class Main extends Plugin {

	public static final Module module = new Module();

	public static final ChatCommand command = new ChatCommand();

	public static final Path CONFIG_DIR = RusherHackAPI.getConfigPath().resolve("chatfilter");

	private static final String[] banner = {
	    "# Chat Filter Configuration File",
	    "#",
	    "# Add your custom regular expressions below.",
	    "# Lines starting with '#' are treated as comments and ignored.",
	    "# Empty lines are also ignored.",
	    "#",
	    "# Example expressions can be found at: https://github.com/TehPicix/rusherhack-ChatFilter/wiki",
	};

	@Override
	public void onLoad() {

		// Create default config file and directories
		createDefaults();

		// Load expressions from file
		loadFiltersFromFile();

		// Load module
		RusherHackAPI.getModuleManager().registerFeature(module);

		// Load command
		RusherHackAPI.getCommandManager().registerFeature(command);
	}

	/**
	 * Create default config file and directories
	 * if they do not exist
	 */
	public static void createDefaults() {
		try {
			if (!CONFIG_DIR.toFile().exists()) {
				CONFIG_DIR.toFile().mkdirs();
			}
			if (!CONFIG_DIR.resolve("filter.txt").toFile().exists()) {
				CONFIG_DIR.resolve("filter.txt").toFile().createNewFile();
				Files.writeString(CONFIG_DIR.resolve("filter.txt"), String.join(System.lineSeparator(), banner));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Load filter expressions from file
	 */
	public static void loadFiltersFromFile() {
		try {
			Files.readAllLines(CONFIG_DIR.resolve("filter.txt"))
			    .stream()
			    .map(String::trim)
			    .filter(line -> !line.isEmpty() && !line.startsWith("#"))
			    .forEach(Filter.expressions::add);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onUnload() {
	}
}