package net.plastoid501.ams;

import net.fabricmc.api.ModInitializer;

import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.options.DoubleOption;
import net.minecraft.client.options.GameOptions;
import net.minecraft.client.options.Option;
import net.minecraft.text.MutableText;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AdvancedMouseSensitivity implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final String MOD_ID = "advanced-mouse-sensitivity";
	public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

	public static double horizontalSensitivity = 0.5;
	public static double verticalSensitivity = 0.5;

	public static final DoubleOption HORIZONTAL_SENSITIVITY = new DoubleOption("options.mouse.sensitivity.horizontal", 0.0, 1.0, 0.0F, (gameOptions) -> {
		return horizontalSensitivity;
	}, (gameOptions, mouseSensitivity) -> {
		horizontalSensitivity = mouseSensitivity;
	}, (gameOptions, option) -> {
		double d = option.getRatio(horizontalSensitivity);
		MutableText mutableText = option.getDisplayPrefix();
		return mutableText.append((int)(d * 200.0) + "%");
	});
	public static final DoubleOption VERTICAL_SENSITIVITY = new DoubleOption("options.mouse.sensitivity.vertical", 0.0, 1.0, 0.0F, (gameOptions) -> {
		return verticalSensitivity;
	}, (gameOptions, mouseSensitivity) -> {
		verticalSensitivity = mouseSensitivity;
	}, (gameOptions, option) -> {
		double d = option.getRatio(verticalSensitivity);
		MutableText mutableText = option.getDisplayPrefix();
		return mutableText.append((int)(d * 200.0) + "%");
	});

	public static Option horizontalOption = new Option("options.mouse.sensitivity.horizontal") {
		@Override
		public AbstractButtonWidget createButton(GameOptions options, int x, int y, int width) {
			return HORIZONTAL_SENSITIVITY.createButton(options, x, y, width);
		}
	};
	public static Option verticalOption = new Option("options.mouse.sensitivity.vertical") {
		@Override
		public AbstractButtonWidget createButton(GameOptions options, int x, int y, int width) {
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