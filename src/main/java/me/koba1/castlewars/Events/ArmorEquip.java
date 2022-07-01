package me.koba1.castlewars.Events;

import me.koba1.castlewars.API.RegionAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

public class ArmorEquip implements Listener {

    @EventHandler
    public void onEquip(InventoryClickEvent e) {
        if(e.getSlotType() == InventoryType.SlotType.ARMOR) {
            if(e.getWhoClicked() instanceof Player) {
                Player p = (Player) e.getWhoClicked();
                if(RegionAPI.inGameMap(p)) {
                    e.setCancelled(true);
                }
            }
        }
    }
}
