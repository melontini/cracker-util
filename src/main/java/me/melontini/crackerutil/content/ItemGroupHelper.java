package me.melontini.crackerutil.content;

import net.minecraft.item.ItemGroup;
import net.minecraft.resource.featuretoggle.FeatureSet;
import org.jetbrains.annotations.ApiStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
@ApiStatus.Experimental
public class ItemGroupHelper {
    public static final Map<ItemGroup, List<InjectEntries>> INJECTED_GROUPS = new ConcurrentHashMap<>();

    public static void addItemGroupInjection(ItemGroup group, InjectEntries injectEntries) {
        if (INJECTED_GROUPS.containsKey(group)) {
            if (!INJECTED_GROUPS.get(group).contains(injectEntries)) {
                INJECTED_GROUPS.get(group).add(injectEntries);
            }
        } else {
            INJECTED_GROUPS.computeIfAbsent(group, group1 -> new ArrayList<>()).add(injectEntries);
        }
    }

    @FunctionalInterface
    public interface InjectEntries {
        void inject(FeatureSet enabledFeatures, boolean operatorEnabled, ItemGroup.EntriesImpl entriesImpl);
    }
}
