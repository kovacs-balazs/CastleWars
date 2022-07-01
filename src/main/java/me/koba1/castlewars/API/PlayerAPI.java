package me.koba1.castlewars.API;

import me.koba1.castlewars.API.Kits.KitAPI;
import me.koba1.castlewars.API.Kits.KitConfigAPI_Blue;
import me.koba1.castlewars.API.Kits.KitConfigAPI_Red;
import me.koba1.castlewars.Files.Messages;
import me.koba1.castlewars.Main;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class PlayerAPI {

    public static void sendActionBar(Player p, String time) {
        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(
                ChatColor.translateAlternateColorCodes('&', Messages.get().getString("actionbar_starts").replace("%time%", time))));
    }

    public static void sendActionBarNeed(Player p, String need) {
        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(
                ChatColor.translateAlternateColorCodes('&', Messages.get().getString("need_more_players").replace("%players%", need))));
    }

    public static void resetActionBar(Player p) {
        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(
                ChatColor.translateAlternateColorCodes('&', "§5 ")));
    }

    public static TeamAPI.Teams getHaveBanner(Player p) {
        for(ItemStack is : p.getInventory().getStorageContents()) {
            if(is == null) continue;

            if(is.getType() == Material.BLUE_BANNER) return TeamAPI.Teams.BLUE;
            if(is.getType() == Material.RED_BANNER) return TeamAPI.Teams.RED;
        }

        return null;
    }

//    public static void bannerHat(Player player, TeamAPI.Teams team) {
//        if(team == TeamAPI.Teams.BLUE) {
//            player.getInventory().setHelmet(new ItemStack(Material.BLUE_BANNER));
//        } else if(team == TeamAPI.Teams.RED) {
//            player.getInventory().setHelmet(new ItemStack(Material.RED_BANNER));
//        }
//    }
//
//    public static void resetHat(Player player) {
//        if(Main.teams.get(player) == TeamAPI.Teams.BLUE) {
//            KitConfigAPI_Blue.readBlueKit();
//            ItemStack helmet = KitConfigAPI_Blue.armors[3];
//            player.getInventory().setHelmet(helmet);
//        } else {
//            KitConfigAPI_Red.readRedKit();
//            ItemStack helmet = KitConfigAPI_Red.armors[3];
//            player.getInventory().setHelmet(helmet);
//        }
//    }
}
