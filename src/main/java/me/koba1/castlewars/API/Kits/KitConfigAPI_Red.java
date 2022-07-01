package me.koba1.castlewars.API.Kits;

import me.koba1.castlewars.Files.Kits;
import me.koba1.castlewars.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class KitConfigAPI_Red {

    public static LinkedHashMap<ItemStack, Integer> itemStacks;
    public static ItemStack[] armors;

    private static Main m = Main.getPlugin(Main.class);

    public static void readRedKit() {
        itemStacks = new LinkedHashMap<>();
        armors = new ItemStack[4];

        outer: for(String str : Kits.get().getStringList("kits.red.items")) {
            try {
                String item = str.split(" ")[0];
                String amount = str.split(" ")[1];

                ItemStack is = new ItemStack(Material.valueOf(item.toUpperCase()), Integer.parseInt(amount));
                ItemMeta im = is.getItemMeta();

                int slot = -1;
                inner: for (String splitted : str.split(" ")) {
                    try {
                        String key = splitted.split(":")[0];
                        String value = splitted.split(":")[1];

                        try {
                            if (Enchantment.getByKey(NamespacedKey.minecraft(splitted.split(":")[0].toLowerCase())) != null) {
                                im.addEnchant(Enchantment.getByKey(NamespacedKey.minecraft(splitted.split(":")[0].toLowerCase())), Integer.parseInt(splitted.split(":")[1]), true);
                            }
                        } catch(IllegalArgumentException ex) {
                            ex.printStackTrace();
                        }

                        if(key.equalsIgnoreCase("unbreakable")) {
                            if(value.equalsIgnoreCase("true")) {
                                im.setUnbreakable(true);
                            }
                        }

                        if (key.equalsIgnoreCase("name"))
                            im.setDisplayName(ChatColor.translateAlternateColorCodes('&', value));

                        if (key.equalsIgnoreCase("lore")) {
                            ArrayList<String> lore = new ArrayList();

                            for(String lores : value.split("\\|")) {
                                lore.add(ChatColor.translateAlternateColorCodes('&', lores));
                            }

                            im.setLore(lore);
                        }
                        is.setItemMeta(im);

                        if (key.equalsIgnoreCase("slot"))
                            slot = Integer.parseInt(value);
                    } catch (ArrayIndexOutOfBoundsException ex) {
                        continue inner;
                    }
                }
                itemStacks.put(is, slot);
            } catch (NullPointerException ex) {
                Bukkit.getLogger().severe("Cannot load blue kits from config");
            }
        }

        outer: for(String str : Kits.get().getStringList("kits.red.armor")) {
            try {
                String item = str.split(" ")[0];
                String amount = str.split(" ")[1];

                ItemStack is = new ItemStack(Material.valueOf(item.toUpperCase()), Integer.parseInt(amount));
                LeatherArmorMeta im = (LeatherArmorMeta) is.getItemMeta();
                inner: for (String splitted : str.split(" ")) {

                    try {
                        String key = splitted.split(":")[0];
                        String value = splitted.split(":")[1];
                        String[] rgbColors = value.split(",");

                        if(key.equalsIgnoreCase("unbreakable")) {
                            if(value.equalsIgnoreCase("true")) {
                                im.setUnbreakable(true);
                            }
                        }

                        if (key.equalsIgnoreCase("color"))
                            im.setColor(org.bukkit.Color.fromRGB(Integer.parseInt(rgbColors[0]), Integer.parseInt(rgbColors[1]), Integer.parseInt(rgbColors[2])));

                        try {
                            if (Enchantment.getByKey(NamespacedKey.minecraft(splitted.split(":")[0].toLowerCase())) != null) {
                                im.addEnchant(Enchantment.getByKey(NamespacedKey.minecraft(splitted.split(":")[0].toLowerCase())), Integer.parseInt(splitted.split(":")[1]), true);
                            }
                        } catch(IllegalArgumentException ex) {
                            ex.printStackTrace();
                        }

                        is.setItemMeta(im);

                        if (is.getType().name().contains("HELMET"))
                            armors[3] = is;
                        else if (is.getType().name().contains("CHESTPLATE"))
                            armors[2] = is;
                        else if (is.getType().name().contains("LEGGINGS"))
                            armors[1] = is;
                        else if (is.getType().name().contains("BOOTS"))
                            armors[0] = is;
                    } catch (ArrayIndexOutOfBoundsException ex) {
                        continue inner;
                    }
                }
            } catch (NullPointerException ex) {
                Bukkit.getLogger().severe("Cannot load blue kits from config");
            }
        }
    }
}
