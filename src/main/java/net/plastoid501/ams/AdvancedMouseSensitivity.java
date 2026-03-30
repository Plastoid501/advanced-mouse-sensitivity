package net.plastoid501.ams;

import net.fabricmc.api.ModInitializer;

import net.minecraft.client.OptionInstance;
import net.minecraft.client.Options;
import net.minecraft.network.chat.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AdvancedMouseSensitivity implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final String MOD_ID = "advanced-mouse-sensitivity";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static OptionInstance<Double> horizontalOption = new OptionInstance<>(
			"options.mouse.sensitivity.horizontal",
            OptionInstance.noTooltip(),
			(optionText, value) -> Options.percentValueLabel(Component.translatable("options.mouse.sensitivity.horizontal"), 2.0 * value),
            OptionInstance.UnitDouble.INSTANCE,
			0.5,
			(value) -> {
			}
	);
	public static OptionInstance<Double> verticalOption = new OptionInstance<>(
			"options.mouse.sensitivity.vertical",
            OptionInstance.noTooltip(),
			(optionText, value) -> Options.percentValueLabel(Component.translatable("options.mouse.sensitivity.vertical"), 2.0 * value),
            OptionInstance.UnitDouble.INSTANCE,
			0.5,
			(value) -> {
			}
	);

	public static OptionInstance<Boolean> lockedHorizontal = OptionInstance.createBoolean("options.mouse.sensitivity.lockedHorizontal", false);
	public static OptionInstance<Boolean> lockedVertical = OptionInstance.createBoolean("options.mouse.sensitivity.lockedVertical", false);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
	}


}