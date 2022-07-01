package me.koba1.castlewars.ShopSystem;

import me.koba1.castlewars.Files.Shop;
import me.koba1.castlewars.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ShopInventories {

    public static Inventory page1(Player p) {
        Main.itemSection.clear();
        Inventory inv = Bukkit.createInventory(null, 6 * 9, ChatColor.translateAlternateColorCodes('&', Shop.get().getString("page1_title")));

        for(int i = 45; i < 54; i++) {
            inv.setItem(i, blackGlass());
        }

        inv.setItem(45, SetupItems.loadSkull(p));
        inv.setItem(49, SetupItems.leaveShop());
        inv.setItem(53, SetupItems.nextPage());

        for(String section : Shop.get().getConfigurationSection("page_1").getKeys(false)) {
            inv.setItem(SetupItems.getSlot(
                    Shop.get().getConfigurationSection("page_1." + section)),
                    SetupItems.itemStack(Shop.get().getConfigurationSection("page_1." + section)));
        }

        return inv;
    }

    public static ItemStack blackGlass() {
        ItemStack is = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName("§5");
        is.setItemMeta(im);
        return is;
    }

    public static Inventory page2(Player p) {
        Main.itemSection.clear();
        Inventory inv = Bukkit.createInventory(null, 6 * 9, ChatColor.translateAlternateColorCodes('&', Shop.get().getString("page2_title")));

        for(int i = 45; i < 54; i++) {
            inv.setItem(i, blackGlass());
        }

        inv.setItem(53, SetupItems.loadSkull(p));
        inv.setItem(49, SetupItems.leaveShop());
        inv.setItem(45, SetupItems.previousPage());

        for(String section : Shop.get().getConfigurationSection("page_2").getKeys(false)) {
            inv.setItem(SetupItems.getSlot(
                    Shop.get().getConfigurationSection("page_2." + section)),
                    SetupItems.itemStack(Shop.get().getConfigurationSection("page_2." + section)));
        }

        return inv;
    }
}
