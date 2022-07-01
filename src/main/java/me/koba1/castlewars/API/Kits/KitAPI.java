package me.koba1.castlewars.API.Kits;

import me.koba1.castlewars.Main;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class KitAPI {

    public static void loadBlue() {
        KitConfigAPI_Blue.readBlueKit();
        for (Player player : Main.blue) {
            player.getInventory().clear();
            for (Map.Entry<ItemStack, Integer> is : KitConfigAPI_Blue.itemStacks.entrySet()) {
                if(is.getValue() == -1) {
                    player.getInventory().addItem(is.getKey());
                } else {
                    player.getInventory().setItem(is.getValue(), is.getKey());
                }
            }

            player.getInventory().setArmorContents(KitConfigAPI_Blue.armors);
        }
    }

    public static void loadRed() {
        KitConfigAPI_Red.readRedKit();
        for (Player player : Main.red) {
            player.getInventory().clear();
            for (Map.Entry<ItemStack, Integer> is : KitConfigAPI_Red.itemStacks.entrySet()) {
                if(is.getValue() == -1) {
                    player.getInventory().addItem(is.getKey());
                } else {
                    player.getInventory().setItem(is.getValue(), is.getKey());
                }
            }
            player.getInventory().setArmorContents(KitConfigAPI_Red.armors);
        }
    }

    public static void loadBlue(Player player) {
        KitConfigAPI_Blue.readBlueKit();
        player.getInventory().clear();
        for (Map.Entry<ItemStack, Integer> is : KitConfigAPI_Blue.itemStacks.entrySet()) {
            if(is.getValue() == -1) {
                player.getInventory().addItem(is.getKey());
            } else {
                player.getInventory().setItem(is.getValue(), is.getKey());
            }
        }

        player.getInventory().setArmorContents(KitConfigAPI_Blue.armors);
    }

    public static void loadRed(Player player) {
        KitConfigAPI_Red.readRedKit();
        player.getInventory().clear();
        for (Map.Entry<ItemStack, Integer> is : KitConfigAPI_Red.itemStacks.entrySet()) {
            if(is.getValue() == -1) {
                player.getInventory().addItem(is.getKey());
            } else {
                player.getInventory().setItem(is.getValue(), is.getKey());
            }
        }
        player.getInventory().setArmorContents(KitConfigAPI_Red.armors);
    }
}
