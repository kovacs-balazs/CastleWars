package me.koba1.castlewars.Events;

import me.koba1.castlewars.API.ConfigAPI;
import me.koba1.castlewars.API.RegionAPI;
import me.koba1.castlewars.Files.Messages;
import me.koba1.castlewars.Main;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatEvent implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(AsyncPlayerChatEvent e) {
        if(!e.getPlayer().getWorld().getName().equalsIgnoreCase(ConfigAPI.world.getName())) return;
        if(!RegionAPI.inGameMap(e.getPlayer())) return;

        if(Main.blue.contains(e.getPlayer())) {
            e.setCancelled(true);
            for(Player p : RegionAPI.getGamePlayers()) {
                p.sendMessage(
                        ChatColor.translateAlternateColorCodes('&', Messages.get().getString("chat_design")
                                .replace("%team%", "&9Blue")
                                .replace("%player%", e.getPlayer().getName())
                                .replace("%message%", e.getMessage()))
                );
            }
        }
        //
        else if(Main.red.contains(e.getPlayer())) {
            e.setCancelled(true);
            for(Player p : RegionAPI.getGamePlayers()) {
                p.sendMessage(
                        ChatColor.translateAlternateColorCodes('&', Messages.get().getString("chat_design")
                                .replace("%team%", "&cRed")
                                .replace("%player%", e.getPlayer().getName())
                                .replace("%message%", e.getMessage()))
                );
            }
        }
    }
}
