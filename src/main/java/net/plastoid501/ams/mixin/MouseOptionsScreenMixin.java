package net.plastoid501.ams.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.option.MouseOptionsScreen;
import net.minecraft.client.gui.widget.ButtonListWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.OptionButtonWidget;
import net.minecraft.client.option.Option;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.TranslatableText;
import net.plastoid501.ams.AdvancedMouseSensitivity;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Mixin(MouseOptionsScreen.class)
public class MouseOptionsScreenMixin {
    @Mutable @Shadow @Final private static Option[] OPTIONS;
    @Shadow public ButtonListWidget buttonList;
    @Unique private ClickableWidget widget;

    @Inject(method = "init", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/option/MouseOptionsScreen;addButton(Lnet/minecraft/client/gui/widget/ClickableWidget;)Lnet/minecraft/client/gui/widget/ClickableWidget;"))
    private void postInit(CallbackInfo ci) {
        for (ButtonListWidget.ButtonEntry buttonEntry : buttonList.children()) {
            for (ClickableWidget clickableWidget : buttonEntry.buttons) {
                for (Object object : ((TranslatableText) clickableWidget.getMessage()).getArgs()) {
                    if (object instanceof TranslatableText) {
                        TranslatableText text = (TranslatableText) object;
                        if (Objects.equals(text.getKey(), ((TranslatableText) Option.SENSITIVITY.getDisplayPrefix()).getKey())) {
                            this.widget = clickableWidget;
                            this.widget.active = false;
                            return;
                        }
                    }
                }
            }
        }
    }

    @Inject(method = "render", at = @At(value = "RETURN"))
    private void renderTooltip(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (this.widget != null && this.widget.isHovered()) {
            ((MouseOptionsScreen) (Object) this).renderOrderedTooltip(matrices, MinecraftClient.getInstance().textRenderer.wrapLines(new TranslatableText("options.mouse.button.inactive"), 170), mouseX, mouseY);
        }
    }

    @Inject(method = "<clinit>", at = @At(value = "RETURN"))
    private static void setOption(CallbackInfo ci) {
        List<Option> options = Arrays.stream(OPTIONS).collect(Collectors.toList());
        options.add(0, AdvancedMouseSensitivity.horizontalOption);
        options.add(1, AdvancedMouseSensitivity.verticalOption);
        OPTIONS = options.toArray(new Option[options.size()]);
    }

}
