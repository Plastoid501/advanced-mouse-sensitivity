package net.plastoid501.ams;

import net.fabricmc.api.ModInitializer;

import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.option.DoubleOption;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.Option;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AdvancedMouseSensitivity implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final String MOD_ID = "advanced-mouse-sensitivity";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static double horizontalSensitivity = 0.5;
	public static double verticalSensitivity = 0.5;

	public static final DoubleOption HORIZONTAL_SENSITIVITY = new DoubleOption("options.mouse.sensitivity.horizontal", 0.0, 1.0, 0.0F, (gameOptions) -> {
		return horizontalSensitivity;
	}, (gameOptions, mouseSensitivity) -> {
		horizontalSensitivity = mouseSensitivity;
	}, (gameOptions, option) -> {
		double d = option.getRatio(horizontalSensitivity);
		return option.getPercentLabel(2.0 * d);
	});
	public static final DoubleOption VERTICAL_SENSITIVITY = new DoubleOption("options.mouse.sensitivity.vertical", 0.0, 1.0, 0.0F, (gameOptions) -> {
		return verticalSensitivity;
	}, (gameOptions, mouseSensitivity) -> {
		verticalSensitivity = mouseSensitivity;
	}, (gameOptions, option) -> {
		double d = option.getRatio(verticalSensitivity);
		return option.getPercentLabel(2.0 * d);
	});

	public static Option horizontalOption = new Option("options.mouse.sensitivity.horizontal") {
		@Override
		public ClickableWidget createButton(GameOptions options, int x, int y, int width) {
			return HORIZONTAL_SENSITIVITY.createButton(options, x, y, width);
		}
	};
	public static Option verticalOption = new Option("options.mouse.sensitivity.vertical") {
		@Override
		public ClickableWidget createButton(GameOptions options, int x, int y, int width) {
			return VERTICAL_SENSITIVITY.createButton(options, x, y, width);
		}
	};


	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
	}


}