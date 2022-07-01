package me.koba1.castlewars.Events;

import me.koba1.castlewars.API.PlayerAPI;
import me.koba1.castlewars.API.RegionAPI;
import me.koba1.castlewars.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class DamageEvent implements Listener {

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        if(e.getDamager() instanceof Player) {
            if(e.getEntity() instanceof Player) {
                Player damager = (Player) e.getDamager();
                Player p = (Player) e.getEntity();

                if(!RegionAPI.inGameMap(p)) return;
                if(!RegionAPI.inGameMap(damager)) return;

                if(Main.blue.contains(damager) && Main.blue.contains(p)) {
                    e.setCancelled(true);
                    return;
                }

                if(Main.red.contains(damager) && Main.red.contains(p)) {
                    e.setCancelled(true);
                    return;
                }
            }
        }
    }
}
