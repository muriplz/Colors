package dev.muriplz.mixins;

import com.hypixel.hytale.server.core.HytaleServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = HytaleServer.class, remap = false)
public class ExampleMixin {

    @Inject(
            method = "boot",
            at = @At("RETURN")
    )
    private void onDoneSetup(CallbackInfo ci) {
        System.out.println("=========== =========================");
        System.out.println("¡Hello World from Mixin!");
        System.out.println("====================================");
    }
}
