package com.github.subat0m1c.rawinput.mixin;

import com.github.subat0m1c.rawinput.RawInput;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MixinGuiClose {

    @Inject(method = "setIngameFocus", at = @At("HEAD"))
    void onClose(CallbackInfo ci) {
        RawInput.guiClosed();
    }
}
