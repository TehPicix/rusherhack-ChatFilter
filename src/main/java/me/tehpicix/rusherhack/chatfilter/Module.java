package me.tehpicix.rusherhack.chatfilter;

import org.rusherhack.client.api.feature.module.ModuleCategory;
import org.rusherhack.client.api.feature.module.ToggleableModule;

public class Module extends ToggleableModule {
	
	public Module() {
		super("ChatFilter", "Filter your game chat with a customizable regular expression", ModuleCategory.CLIENT);
	}
	
	@Override
	public void onEnable() {
		Filter.createFile(Filter.file);
		Filter.loadExpressions(Filter.file);
	}
	
	@Override
	public void onDisable() {
	}
	
}