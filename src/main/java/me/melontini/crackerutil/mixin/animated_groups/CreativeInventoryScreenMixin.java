package me.melontini.crackerutil.mixin.animated_groups;

import me.melontini.crackerutil.interfaces.AnimatedItemGroup;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemGroup;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(CreativeInventoryScreen.class)
public abstract class CreativeInventoryScreenMixin {

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemGroup;getIcon()Lnet/minecraft/item/ItemStack;", shift = At.Shift.BEFORE), locals = LocalCapture.CAPTURE_FAILSOFT, method = "renderTabIcon", cancellable = true)
    private void cracker_util$drawGroupIcon(MatrixStack matrices, ItemGroup group, CallbackInfo ci, boolean bl, boolean bl2, int i, int j, int k, int l, int m) {
        if (group instanceof AnimatedItemGroup animatedItemGroup) {
            animatedItemGroup.animateIcon(matrices, (CreativeInventoryScreen) (Object) this, l, m);
            ci.cancel();
        }
    }
}
