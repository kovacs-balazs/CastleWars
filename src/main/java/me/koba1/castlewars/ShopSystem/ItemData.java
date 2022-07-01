package me.koba1.castlewars.ShopSystem;

import me.koba1.castlewars.Main;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemData {

    public static int getPrice(ItemStack is) {
        return Main.itemSection.get(is).getInt("price");
    }
}

