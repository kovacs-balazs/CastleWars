package me.koba1.castlewars.Events;

import me.koba1.castlewars.API.ConfigAPI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class ItemDropEvent implements Listener {

    @EventHandler
    public void onDropItem(PlayerDropItemEvent e) {
        if(!e.getPlayer().getWorld().getName().equalsIgnoreCase(ConfigAPI.world.getName())) return;
        if(e.getPlayer().hasPermission("castlewars.dropitem.bypass")) return;

        e.setCancelled(true);
    }
}
