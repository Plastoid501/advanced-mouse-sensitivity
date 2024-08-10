package net.plastoid501.ams.mixin;

import net.minecraft.client.option.GameOptions;
import net.plastoid501.ams.AdvancedMouseSensitivity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameOptions.class)
public class GameOptionsMixin {
    @Inject(method = "accept", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/option/GameOptions$Visitor;visitDouble(Ljava/lang/String;D)D", ordinal = 0, shift = At.Shift.AFTER))
    private void addOption(GameOptions.Visitor visitor, CallbackInfo ci) {
        AdvancedMouseSensitivity.horizontalSensitivity = visitor.visitDouble("horizontalSensitivity", AdvancedMouseSensitivity.horizontalSensitivity);
        AdvancedMouseSensitivity.verticalSensitivity = visitor.visitDouble("verticalSensitivity", AdvancedMouseSensitivity.verticalSensitivity);
    }
}
