package net.plastoid501.ams.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.options.MouseSettingsScreen;
import net.minecraft.network.chat.Component;
import net.plastoid501.ams.AdvancedMouseSensitivity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Mixin(MouseSettingsScreen.class)
public class MouseOptionsScreenMixin {
    @Inject(method = "addOptions", at = @At(value = "RETURN"))
    private void postInit(CallbackInfo ci) {
        AbstractWidget widget = ((MouseSettingsScreen) (Object) this).list.findOption(Minecraft.getInstance().options.sensitivity());
        if (widget == null) {
            return;
        }
        widget.active = false;
        widget.setTooltip(Tooltip.create(Component.translatable("options.mouse.button.inactive")));
    }

    @Inject(method = "options", at = @At(value = "RETURN"), cancellable = true)
    private static void modifyOptions(Options gameOptions, CallbackInfoReturnable<OptionInstance<?>[]> cir) {
        List<OptionInstance<?>> options = new ArrayList<>(Arrays.stream(cir.getReturnValue()).toList());
        options.add(0, AdvancedMouseSensitivity.lockedHorizontal);
        options.add(1, AdvancedMouseSensitivity.lockedVertical);
        options.add(2, AdvancedMouseSensitivity.horizontalOption);
        options.add(3, AdvancedMouseSensitivity.verticalOption);
        cir.setReturnValue(options.toArray(new OptionInstance[options.size()]));
    }
}
