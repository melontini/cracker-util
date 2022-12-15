package me.melontini.crackerutil.mixin.item_group_helper;

import me.melontini.crackerutil.content.ItemGroupHelper;
import net.minecraft.item.ItemGroup;
import net.minecraft.resource.featuretoggle.FeatureSet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(ItemGroup.class)
public class ItemGroupMixin {
    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemGroup$EntryCollector;accept(Lnet/minecraft/resource/featuretoggle/FeatureSet;Lnet/minecraft/item/ItemGroup$Entries;Z)V", shift = At.Shift.BEFORE), locals = LocalCapture.CAPTURE_FAILSOFT, method = "updateEntries")
    private void cracker_util$injectEntries(FeatureSet enabledFeatures, boolean operatorEnabled, CallbackInfo ci, ItemGroup.EntriesImpl entriesImpl) {
        if (ItemGroupHelper.INJECTED_GROUPS.containsKey((ItemGroup) (Object) this)) {
            for (ItemGroupHelper.InjectEntries injectEntries : ItemGroupHelper.INJECTED_GROUPS.get((ItemGroup) (Object) this)) {
                injectEntries.inject(enabledFeatures, operatorEnabled, entriesImpl);
            }
        }
    }
}
