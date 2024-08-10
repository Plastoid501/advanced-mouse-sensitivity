package net.plastoid501.ams.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.option.MouseOptionsScreen;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.plastoid501.ams.AdvancedMouseSensitivity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Mixin(MouseOptionsScreen.class)
public class MouseOptionsScreenMixin {
    private ClickableWidget widget;

    @Inject(method = "init", at = @At(value = "RETURN"))
    private void postInit(CallbackInfo ci) {
        this.widget = ((MouseOptionsScreen) (Object) this).buttonList.getButtonFor(MinecraftClient.getInstance().options.getMouseSensitivity());
        if (widget == null) {
            return;
        }
        this.widget.active = false;
    }

    @Inject(method = "getOptions", at = @At(value = "RETURN"), cancellable = true)
    private static void modifyOptions(GameOptions gameOptions, CallbackInfoReturnable<SimpleOption<?>[]> cir) {
        List<SimpleOption<?>> options = new ArrayList<>(Arrays.stream(cir.getReturnValue()).toList());
        options.add(0, AdvancedMouseSensitivity.horizontalOption);
        options.add(1, AdvancedMouseSensitivity.verticalOption);
        cir.setReturnValue(options.toArray(new SimpleOption[options.size()]));
    }

    @Inject(method = "render", at = @At(value = "RETURN"))
    private void renderTooltip(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (widget != null && widget.isHovered()) {
            ((MouseOptionsScreen) (Object) this).renderOrderedTooltip(matrices, MinecraftClient.getInstance().textRenderer.wrapLines(Text.translatable("options.mouse.button.inactive"), 170), mouseX, mouseY);
        }
    }

}
