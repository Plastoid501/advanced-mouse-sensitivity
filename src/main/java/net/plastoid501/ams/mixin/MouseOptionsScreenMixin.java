package net.plastoid501.ams.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.option.MouseOptionsScreen;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.Option;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.plastoid501.ams.AdvancedMouseSensitivity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Mixin(MouseOptionsScreen.class)
public class MouseOptionsScreenMixin {
    @Mutable @Shadow @Final private static Option[] OPTIONS;
    private ClickableWidget widget;

    @Inject(method = "init", at = @At(value = "RETURN"))
    private void postInit(CallbackInfo ci) {
        this.widget = ((MouseOptionsScreen) (Object) this).buttonList.getButtonFor(Option.SENSITIVITY);
        if (widget == null) {
            return;
        }
        this.widget.active = false;
    }

    @Inject(method = "render", at = @At(value = "RETURN"))
    private void renderTooltip(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (widget != null && widget.isHovered()) {
            ((MouseOptionsScreen) (Object) this).renderOrderedTooltip(matrices, MinecraftClient.getInstance().textRenderer.wrapLines(new TranslatableText("options.mouse.button.inactive"), 170), mouseX, mouseY);
        }
    }

    @Inject(method = "<clinit>", at = @At(value = "RETURN"))
    private static void setOption(CallbackInfo ci) {
        List<Option> options = new ArrayList<>(Arrays.stream(OPTIONS).toList());
        options.add(0, AdvancedMouseSensitivity.horizontalOption);
        options.add(1, AdvancedMouseSensitivity.verticalOption);
        OPTIONS = options.toArray(new Option[options.size()]);
    }

}
