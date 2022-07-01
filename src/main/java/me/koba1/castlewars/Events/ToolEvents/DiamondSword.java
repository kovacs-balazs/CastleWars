package me.koba1.castlewars.Events.ToolEvents;

import me.koba1.castlewars.API.ConfigAPI;
import me.koba1.castlewars.API.RegionAPI;
import me.koba1.castlewars.Commands.MainCommand;
import me.koba1.castlewars.Files.Messages;
import me.koba1.castlewars.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class DiamondSword implements Listener {

    private Main m = Main.getPlugin(Main.class);

    @EventHandler
    public void onStoneInteract(PlayerInteractEvent e) { // BANNER LOCATION
        if(!e.getPlayer().getWorld().getName().equalsIgnoreCase(ConfigAPI.world.getName())) return;
        if (e.getClickedBlock() == null) return;
        if (e.getClickedBlock().getType() == Material.AIR) return;
        if (!e.getPlayer().hasPermission("castlewars.usetools")) return;
        if (e.getItem() == null) return;
        if (e.getItem().getType() != Material.DIAMOND_SWORD) return;

        try {
            if (e.getAction() == Action.LEFT_CLICK_BLOCK) { // BLUE BANNER LOCATION
                m.getConfig().set("blue_spawn", RegionAPI.convertLocToStringYawPitch(e.getClickedBlock().getLocation().add(0, 1, 0)));
                m.saveConfig();
                e.getPlayer().sendMessage(MainCommand.chatcolor(Messages.get().getString("team_spawn_location").replace("%team%", "Blue")));
                e.setCancelled(true);
            }
            //
            else if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                m.getConfig().set("red_spawn", RegionAPI.convertLocToStringYawPitch(e.getClickedBlock().getLocation().add(0, 1, 0)));
                m.saveConfig();
                e.getPlayer().sendMessage(MainCommand.chatcolor(Messages.get().getString("team_spawn_location").replace("%team%", "Red")));
                e.setCancelled(true);
            }
        } catch (NullPointerException ex) {
            Bukkit.getLogger().severe("Cannot load message from messages.yml");
        }
    }
}
