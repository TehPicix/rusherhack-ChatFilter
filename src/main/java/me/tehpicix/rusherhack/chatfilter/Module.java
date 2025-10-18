package me.tehpicix.rusherhack.chatfilter;

import org.rusherhack.client.api.feature.module.ModuleCategory;
import org.rusherhack.client.api.feature.module.ToggleableModule;
import org.rusherhack.core.setting.BooleanSetting;

public class Module extends ToggleableModule {

	public static final BooleanSetting caseInsensitive = new BooleanSetting("CaseInsensitive", true);

	public static final BooleanSetting logBlockedMessages = new BooleanSetting("LogBlockedMessages", true);

	public Module() {
		super("ChatFilter", "Filter your game chat with a customizable regular expression", ModuleCategory.CLIENT);
		registerSettings(caseInsensitive, logBlockedMessages);
	}

	@Override
	public void onEnable() {

		// Create default config file and directories
		Main.createDefaults();

		// Load expressions from file
		Main.loadFiltersFromFile();
	}

	@Override
	public void onDisable() {

		// Clear loaded expressions
		Filter.expressions.clear();
	}
}