package net.plastoid501.ams;

import net.fabricmc.api.ModInitializer;

import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AdvancedMouseSensitivity implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final String MOD_ID = "advanced-mouse-sensitivity";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static SimpleOption<Double> horizontalOption = new SimpleOption<>(
			"options.mouse.sensitivity.horizontal",
			SimpleOption.emptyTooltip(),
			(optionText, value) -> GameOptions.getPercentValueText(Text.translatable("options.mouse.sensitivity.horizontal"), 2.0 * value),
			SimpleOption.DoubleSliderCallbacks.INSTANCE,
			0.5,
			(value) -> {
			}
	);
	public static SimpleOption<Double> verticalOption = new SimpleOption<>(
			"options.mouse.sensitivity.vertical",
			SimpleOption.emptyTooltip(),
			(optionText, value) -> GameOptions.getPercentValueText(Text.translatable("options.mouse.sensitivity.vertical"), 2.0 * value),
			SimpleOption.DoubleSliderCallbacks.INSTANCE,
			0.5,
			(value) -> {
			}
	);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

	}


}