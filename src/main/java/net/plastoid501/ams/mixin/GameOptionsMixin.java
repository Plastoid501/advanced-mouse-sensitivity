package net.plastoid501.ams.mixin;

import net.minecraft.client.Options;
import net.plastoid501.ams.AdvancedMouseSensitivity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Options.class)
public class GameOptionsMixin {
    @Inject(method = "processOptions", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Options$FieldAccess;process(Ljava/lang/String;Lnet/minecraft/client/OptionInstance;)V", ordinal = 18, shift = At.Shift.AFTER))
    private void addOption(Options.FieldAccess visitor, CallbackInfo ci) {
        visitor.process("horizontalMouseSensitivity", AdvancedMouseSensitivity.horizontalOption);
        visitor.process("verticalMouseSensitivity", AdvancedMouseSensitivity.verticalOption);
        visitor.process("lockedHorizontal", AdvancedMouseSensitivity.lockedHorizontal);
        visitor.process("lockedVertical", AdvancedMouseSensitivity.lockedVertical);
    }
}
