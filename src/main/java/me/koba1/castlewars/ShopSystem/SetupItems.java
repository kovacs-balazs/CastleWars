package me.koba1.castlewars.ShopSystem;

import me.koba1.castlewars.Files.Shop;
import me.koba1.castlewars.Main;
import me.koba1.castlewars.PlayerData.Getters;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;

public class SetupItems {

    public static ItemStack itemStack(ConfigurationSection section) {
        try {
            String item = section.getString("material");
            String amount = section.getString("amount");

            ItemStack is = new ItemStack(Material.valueOf(item.toUpperCase()), Integer.parseInt(amount));

            ItemMeta im = is.getItemMeta();

            List<String> enchantments = section.getStringList("enchantments");
            try {
                for (String str : enchantments) {
                    if (Enchantment.getByKey(NamespacedKey.minecraft(str.split(":")[0].toLowerCase())) != null) {
                        im.addEnchant(Enchantment.getByKey(NamespacedKey.minecraft(str.split(":")[0].toLowerCase())),
                                Integer.parseInt(str.split(":")[1]), true);
                    }
                }
            } catch (IllegalArgumentException ex) {
                ex.printStackTrace();
            }

            im.setDisplayName(ChatColor.translateAlternateColorCodes('&', section.getString("name")));

            List<String> lore = section.getStringList("lore");
            ArrayList<String> returnLore = new ArrayList<>();

            for (String lores : lore) {
                returnLore.add(ChatColor.translateAlternateColorCodes('&', lores));
            }

            im.setLore(returnLore);

            is.setItemMeta(im);

            Main.itemSection.put(is, section);
            return is;
        } catch (NullPointerException ex) {
            Bukkit.getLogger().severe("Cannot shop from shop.yml file!");
        }

        return null;
    }

    public static ItemStack loadSkull(Player p) {
        ItemStack is = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta im = (SkullMeta) is.getItemMeta();
        im.setOwningPlayer(p);
        im.setDisplayName(ChatColor.translateAlternateColorCodes('&', Shop.get().getString("skull_item.name").replace("%name%", p.getName())));

        ArrayList list = new ArrayList<>();
        for(String str : Shop.get().getStringList("skull_item.lore")) {
            list.add(ChatColor.translateAlternateColorCodes('&', str).replace("%coins%", String.valueOf(Getters.getPoints(p))));
        }

        im.setLore(list);
        is.setItemMeta(im);
        return is;
    }

    public static ItemStack previousPage() {
        ItemStack is = new ItemStack(Material.PAPER, 1);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(ChatColor.translateAlternateColorCodes('&', Shop.get().getString("previous_page.name")));

        ArrayList list = new ArrayList<>();
        for(String str : Shop.get().getStringList("previous_page.lore")) {
            list.add(ChatColor.translateAlternateColorCodes('&', str));
        }

        im.setLore(list);
        is.setItemMeta(im);
        return is;
    }

    public static ItemStack nextPage() {
        ItemStack is = new ItemStack(Material.PAPER, 1);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(ChatColor.translateAlternateColorCodes('&', Shop.get().getString("next_page.name")));

        ArrayList list = new ArrayList<>();
        for(String str : Shop.get().getStringList("next_page.lore")) {
            list.add(ChatColor.translateAlternateColorCodes('&', str));
        }

        im.setLore(list);
        is.setItemMeta(im);
        return is;
    }

    public static ItemStack leaveShop() {
        ItemStack is = new ItemStack(Material.COMPASS, 1);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(ChatColor.translateAlternateColorCodes('&', Shop.get().getString("leave_shop_item.name")));

        ArrayList list = new ArrayList<>();
        for(String str : Shop.get().getStringList("leave_shop_item.lore")) {
            list.add(ChatColor.translateAlternateColorCodes('&', str));
        }

        im.setLore(list);
        is.setItemMeta(im);
        return is;
    }

    public static int getSlot(ConfigurationSection section) {
        return section.getInt("slot");
    }
}
