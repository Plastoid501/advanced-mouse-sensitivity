package net.plastoid501.ams.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.options.GameOptions;
import net.minecraft.nbt.CompoundTag;
import net.plastoid501.ams.AdvancedMouseSensitivity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.PrintWriter;

@Mixin(GameOptions.class)
public class GameOptionsMixin {
    @Inject(method = "load", at = @At(value = "FIELD", target = "Lnet/minecraft/client/options/GameOptions;keysAll:[Lnet/minecraft/client/options/KeyBinding;"))
    private void setOption(CallbackInfo ci, @Local(ordinal = 0) String string, @Local(ordinal = 1) CompoundTag nbtCompound2) {
        if ("horizontalMouseSensitivity".equals(string)) {
            AdvancedMouseSensitivity.horizontalSensitivity = GameOptions.parseFloat(nbtCompound2.getString(string));
        }

        if ("verticalMouseSensitivity".equals(string)) {
            AdvancedMouseSensitivity.verticalSensitivity = GameOptions.parseFloat(nbtCompound2.getString(string));
        }
    }

    @Inject(method = "write", at = @At(value = "INVOKE", target = "Ljava/io/PrintWriter;println(Ljava/lang/String;)V", ordinal = 20, shift = At.Shift.AFTER))
    private void addOption(CallbackInfo ci, @Local(ordinal = 0) PrintWriter printWriter) {
        printWriter.println("horizontalMouseSensitivity:" + AdvancedMouseSensitivity.horizontalSensitivity);
        printWriter.println("verticalMouseSensitivity:" + AdvancedMouseSensitivity.verticalSensitivity);
    }
}
