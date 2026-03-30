package net.plastoid501.ams.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.tutorial.Tutorial;
import net.plastoid501.ams.AdvancedMouseSensitivity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(MouseHandler.class)
public class MouseMixin {
    @Unique private double xh;
    @Unique private double yh;
    @Unique private double xgdx;
    @Unique private double xhdx;
    @Unique private double ygdy;
    @Unique private double yhdy;

    @Shadow private double accumulatedDX;
    @Shadow private double accumulatedDY;

    @Shadow @Final private Minecraft minecraft;

    @Inject(method = "turnPlayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/OptionInstance;get()Ljava/lang/Object;"))
    private void getCursorDelta(CallbackInfo ci) {
        double xf = AdvancedMouseSensitivity.horizontalOption.get() * 0.6000000238418579 + 0.20000000298023224;
        double xg = xf * xf * xf;
        xh = xg * 8.0;
        xgdx = this.accumulatedDX * xg;
        xhdx = this.accumulatedDX * xh;

        double yf = AdvancedMouseSensitivity.verticalOption.get() * 0.6000000238418579 + 0.20000000298023224;
        double yg = yf * yf * yf;
        yh = yg * 8.0;
        ygdy = this.accumulatedDY * yg;
        yhdy = this.accumulatedDY * yh;
    }

    @ModifyArgs(method = "turnPlayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/SmoothDouble;getNewDeltaValue(DD)D", ordinal = 0))
    private void modifySmoothX(Args args, @Local(ordinal = 1) double dt) {
        args.set(0, xhdx);
        args.set(1, dt * xh);
    }

    @ModifyArgs(method = "turnPlayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/SmoothDouble;getNewDeltaValue(DD)D", ordinal = 1))
    private void modifySmoothY(Args args, @Local(ordinal = 1) double dt) {
        args.set(0, yhdy);
        args.set(1, dt * yh);
    }

    @Redirect(method = "turnPlayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/tutorial/Tutorial;onMouse(DD)V"))
    private void modifyDeltaXY(Tutorial tutorialManager, double deltaX, double deltaY) {
        boolean bl1 = AdvancedMouseSensitivity.lockedHorizontal.get();
        boolean bl2 = AdvancedMouseSensitivity.lockedVertical.get();
        if (bl1 && bl2) {
            return;
        }
        if (this.minecraft.options.smoothCamera) {
            tutorialManager.onMouse(bl1 ? 0 : deltaX, bl2 ? 0 : deltaY);
        } else if (this.minecraft.options.getCameraType().isFirstPerson() && this.minecraft.player.isScoping()) {
            tutorialManager.onMouse(bl1 ? 0 : xgdx, bl2 ? 0 : ygdy);
        } else {
            tutorialManager.onMouse(bl1 ? 0 : xhdx, bl2 ? 0 : yhdy);
        }
    }

    @Redirect(method = "turnPlayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;turn(DD)V"))
    private void modifyCursorDeltaXY(LocalPlayer player, double cursorDeltaX, double cursorDeltaY) {
        boolean bl1 = AdvancedMouseSensitivity.lockedHorizontal.get();
        boolean bl2 = AdvancedMouseSensitivity.lockedVertical.get();
        if (bl1 && bl2) {
            return;
        }
        if (this.minecraft.options.smoothCamera) {
            player.turn(bl1 ? 0 : cursorDeltaX, bl2 ? 0 : cursorDeltaY);
        } else if (this.minecraft.options.getCameraType().isFirstPerson() && this.minecraft.player.isScoping()) {
            player.turn(bl1 ? 0 : xgdx, bl2 ? 0 : this.minecraft.options.invertMouseY().get() ? -1.0 * ygdy : ygdy);
        } else {
            player.turn(bl1 ? 0 : xhdx, bl2 ? 0 : this.minecraft.options.invertMouseY().get() ? -1.0 * yhdy : yhdy);
        }
    }

}
