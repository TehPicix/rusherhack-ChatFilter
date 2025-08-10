package me.tehpicix.rusherhack.chatfilter;

import java.io.File;
import java.io.IOException;

import org.rusherhack.client.api.RusherHackAPI;
import org.rusherhack.client.api.plugin.Plugin;

public class Main extends Plugin {

	public static final File CONFIG_DIR = RusherHackAPI.getConfigPath().resolve("chatfilter").toFile();

	@Override
	public void onLoad() {

		final Module module = new Module();
		RusherHackAPI.getModuleManager().registerFeature(module);

		if (!CONFIG_DIR.exists()) {
			CONFIG_DIR.mkdirs();
			try {
				CONFIG_DIR.toPath().resolve("default.txt").toFile().createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onUnload() {
	}
}