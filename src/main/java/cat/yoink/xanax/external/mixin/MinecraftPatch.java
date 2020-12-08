package cat.yoink.xanax.external.mixin;

import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public final class MinecraftPatch
{
    @Inject(method = "run", at = @At("HEAD"))
    public void run(CallbackInfo ci)
    {
        System.out.println("Injected!");
    }
}
