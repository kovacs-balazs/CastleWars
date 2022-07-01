package me.koba1.castlewars.Events.ToolEvents;

import me.koba1.castlewars.API.ConfigAPI;
import me.koba1.castlewars.API.RegionAPI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class LobbyInteraction implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if(!e.getPlayer().getWorld().getName().equalsIgnoreCase(ConfigAPI.world.getName())) return;
        if(RegionAPI.getLobbyPlayers().contains(e.getPlayer())) e.setCancelled(true);
    }
}
