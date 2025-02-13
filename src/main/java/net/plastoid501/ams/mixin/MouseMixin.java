package net.plastoid501.ams.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.tutorial.TutorialManager;
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

@Mixin(Mouse.class)
public class MouseMixin {
    @Unique private double xh;
    @Unique private double yh;
    @Unique private double xgdx;
    @Unique private double xhdx;
    @Unique private double ygdy;
    @Unique private double yhdy;

    @Shadow private double cursorDeltaX;
    @Shadow private double cursorDeltaY;

    @Shadow @Final private MinecraftClient client;

    @Inject(method = "updateMouse", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/option/GameOptions;getMouseSensitivity()Lnet/minecraft/client/option/SimpleOption;"))
    private void getCursorDelta(CallbackInfo ci) {
        double xf = AdvancedMouseSensitivity.horizontalOption.getValue() * 0.6000000238418579 + 0.20000000298023224;
        double xg = xf * xf * xf;
        xh = xg * 8.0;
        xgdx = this.cursorDeltaX * xg;
        xhdx = this.cursorDeltaX * xh;

        double yf = AdvancedMouseSensitivity.verticalOption.getValue() * 0.6000000238418579 + 0.20000000298023224;
        double yg = yf * yf * yf;
        yh = yg * 8.0;
        ygdy = this.cursorDeltaY * yg;
        yhdy = this.cursorDeltaY * yh;
    }

    @ModifyArgs(method = "updateMouse", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/SmoothUtil;smooth(DD)D", ordinal = 0))
    private void modifySmoothX(Args args, @Local(ordinal = 1) double dt) {
        args.set(0, xhdx);
        args.set(1, dt * xh);
    }

    @ModifyArgs(method = "updateMouse", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/SmoothUtil;smooth(DD)D", ordinal = 1))
    private void modifySmoothY(Args args, @Local(ordinal = 1) double dt) {
        args.set(0, yhdy);
        args.set(1, dt * yh);
    }

    @Redirect(method = "updateMouse", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/tutorial/TutorialManager;onUpdateMouse(DD)V"))
    private void modifyDeltaXY(TutorialManager tutorialManager, double deltaX, double deltaY) {
        boolean bl1 = AdvancedMouseSensitivity.lockedHorizontal.getValue();
        boolean bl2 = AdvancedMouseSensitivity.lockedVertical.getValue();
        if (bl1 && bl2) {
            return;
        }
        if (this.client.options.smoothCameraEnabled) {
            tutorialManager.onUpdateMouse(bl1 ? 0 : deltaX, bl2 ? 0 : deltaY);
        } else if (this.client.options.getPerspective().isFirstPerson() && this.client.player.isUsingSpyglass()) {
            tutorialManager.onUpdateMouse(bl1 ? 0 : xgdx, bl2 ? 0 : ygdy);
        } else {
            tutorialManager.onUpdateMouse(bl1 ? 0 : xhdx, bl2 ? 0 : yhdy);
        }
    }

    @Redirect(method = "updateMouse", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;changeLookDirection(DD)V"))
    private void modifyCursorDeltaXY(ClientPlayerEntity player, double cursorDeltaX, double cursorDeltaY) {
        boolean bl1 = AdvancedMouseSensitivity.lockedHorizontal.getValue();
        boolean bl2 = AdvancedMouseSensitivity.lockedVertical.getValue();
        if (bl1 && bl2) {
            return;
        }
        if (this.client.options.smoothCameraEnabled) {
            player.changeLookDirection(bl1 ? 0 : cursorDeltaX, bl2 ? 0 : cursorDeltaY);
        } else if (this.client.options.getPerspective().isFirstPerson() && this.client.player.isUsingSpyglass()) {
            player.changeLookDirection(bl1 ? 0 : xgdx, bl2 ? 0 : this.client.options.getInvertYMouse().getValue() ? -1.0 * ygdy : ygdy);
        } else {
            player.changeLookDirection(bl1 ? 0 : xhdx, bl2 ? 0 : this.client.options.getInvertYMouse().getValue() ? -1.0 * yhdy : yhdy);
        }
    }

}
