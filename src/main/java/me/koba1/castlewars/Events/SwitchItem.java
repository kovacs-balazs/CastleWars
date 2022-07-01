package me.koba1.castlewars.Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;

public class SwitchItem implements Listener {

    @EventHandler
    public void onSwitch(PlayerItemHeldEvent e) {
        if(e.getPlayer().getInventory().getItem(e.getPreviousSlot()) != null) {
            if(e.getPlayer().getInventory().getItem(e.getPreviousSlot()).getType().name().contains("BANNER")) e.setCancelled(true);
        }
    }
}
