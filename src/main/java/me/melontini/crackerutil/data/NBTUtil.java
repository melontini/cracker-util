package me.melontini.crackerutil.data;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.math.MathHelper;

public class NBTUtil {
    /**
     * Writes the items in an inventory to a NbtCompound.
     *
     * @param nbt          the NbtCompound to write the inventory to
     * @param inventory    the inventory to write to the NbtCompound
     * @return the NbtCompound with the inventory data written to it
     */
    public static NbtCompound writeInventoryToNbt(NbtCompound nbt, Inventory inventory) {
        NbtList nbtList = new NbtList();
        for (int i = 0; i < inventory.size(); ++i) {
            ItemStack itemStack = inventory.getStack(i);
            if (!itemStack.isEmpty()) {
                nbtList.add(itemStack.writeNbt(NbtBuilder.create().putByte("Slot", (byte) i).build()));
            }
        }
        nbt.put("Items", nbtList);
        return nbt;
    }

    /**
     * Reads the items in an inventory from a NbtCompound.
     *
     * @param nbt          the NbtCompound to read the inventory from
     * @param inventory    the inventory to read the data into
     */
    public static void readInventoryFromNbt(NbtCompound nbt, Inventory inventory) {
        if (nbt != null) if (nbt.getList("Items", NbtElement.COMPOUND_TYPE) != null) {
            NbtList nbtList = nbt.getList("Items", NbtElement.COMPOUND_TYPE);
            for (int i = 0; i < nbtList.size(); ++i) {
                NbtCompound nbtCompound = nbtList.getCompound(i);
                int j = nbtCompound.getByte("Slot") & 255;
                //noinspection ConstantConditions
                if (j >= 0 && j < inventory.size()) {
                    inventory.setStack(j, ItemStack.fromNbt(nbtCompound));
                }
            }
        }
    }

    public static int getInt(NbtCompound nbt, String name, int defaultValue) {
        if (nbt != null) {
            return nbt.getInt(name);
        }
        return defaultValue;
    }

    public static int getInt(NbtCompound nbt, String name, int min, int max) {
        if (nbt != null) {
            int i = nbt.getInt(name);
            return MathHelper.clamp(i, min, max);
        }
        return min;
    }

    public static float getFloat(NbtCompound nbt, String name, float defaultValue) {
        if (nbt != null) {
            return nbt.getFloat(name);
        }
        return defaultValue;
    }

    public static float getFloat(NbtCompound nbt, String name, float min, float max) {
        if (nbt != null) {
            float i = nbt.getFloat(name);
            return MathHelper.clamp(i, min, max);
        }
        return min;
    }

    public static double getDouble(NbtCompound nbt, String name, double defaultValue) {
        if (nbt != null) {
            return nbt.getDouble(name);
        }
        return defaultValue;
    }

    public static double getDouble(NbtCompound nbt, String name, double min, double max) {
        if (nbt != null) {
            double i = nbt.getDouble(name);
            return MathHelper.clamp(i, min, max);
        }
        return min;
    }

    public static byte getByte(NbtCompound nbt, String name, byte defaultValue) {
        if (nbt != null) {
            return nbt.getByte(name);
        }
        return defaultValue;
    }

    public static float getByte(NbtCompound nbt, String name, byte min, byte max) {
        if (nbt != null) {
            byte i = nbt.getByte(name);
            return MathHelper.clamp(i, min, max);
        }
        return min;
    }


    public static String getString(NbtCompound nbt, String name, String defaultValue) {
        if (nbt != null) {
            return nbt.getString(name);
        }
        return defaultValue;
    }
}
