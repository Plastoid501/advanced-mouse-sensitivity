package net.plastoid501.ams.mixin;

import net.minecraft.client.option.GameOptions;
import net.plastoid501.ams.AdvancedMouseSensitivity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameOptions.class)
public class GameOptionsMixin {
    @Inject(method = "accept", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/option/GameOptions$Visitor;accept(Ljava/lang/String;Lnet/minecraft/client/option/SimpleOption;)V", ordinal = 22, shift = At.Shift.AFTER))
    private void addOption(GameOptions.Visitor visitor, CallbackInfo ci) {
        visitor.accept("horizontalMouseSensitivity", AdvancedMouseSensitivity.horizontalOption);
        visitor.accept("verticalMouseSensitivity", AdvancedMouseSensitivity.verticalOption);
    }
}
