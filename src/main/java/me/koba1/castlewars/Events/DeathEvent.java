package me.koba1.castlewars.Events;

import me.koba1.castlewars.API.*;
import me.koba1.castlewars.API.Kits.KitAPI;
import me.koba1.castlewars.Main;
import me.koba1.castlewars.PlayerData.Getters;
import me.koba1.castlewars.PlayerData.Setters;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

import javax.swing.plaf.synth.Region;
import java.util.Iterator;
import java.util.List;

public class DeathEvent implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerDeath(PlayerDeathEvent e) {
        if(!e.getPlayer().getWorld().getName().equalsIgnoreCase(ConfigAPI.world.getName())) return;
        e.setDeathMessage(null);
        e.setDroppedExp(0);

        try {
            List<ItemStack> list = e.getDrops();
            Iterator<ItemStack> i = list.iterator();
            while (i.hasNext()) {
                i.remove();
            }
        } catch (IllegalStateException ex) {
            //ex.printStackTrace();
        }

        e.getDrops().clear();

        if(e.getEntity().getKiller() == null) return;

        Player killer = e.getEntity().getKiller();
        Player entity = e.getEntity();

        try {
            if (PlayerAPI.getHaveBanner(entity).equals(TeamAPI.Teams.BLUE)) {
                //PlayerAPI.resetHat(e.getPlayer());
                if (entity.getLocation().getBlock().getType() == Material.AIR) {
                    entity.getLocation().getBlock().setType(Material.BLUE_BANNER);
                } else {
                    getNearestBlock(e.getPlayer().getLocation().getBlock()).setType(Material.BLUE_BANNER);
                }
            } else {
                if (entity.getLocation().getBlock().getType() == Material.AIR) {
                    entity.getLocation().getBlock().setType(Material.RED_BANNER);
                } else {
                    getNearestBlock(e.getPlayer().getLocation().getBlock()).setType(Material.RED_BANNER);
                }
            }

        } catch (NullPointerException ex) {
        }

        Setters.addKills(killer);
        Setters.addPoints(killer, 5);
        Setters.setPoints(entity, Getters.getPoints(entity) - 5);
        Setters.addDeaths(entity);

        if(RegionAPI.inGameMap(killer))
            ScoreboardAPI.refreshSB(killer);

        if(RegionAPI.inGameMap(entity))
            ScoreboardAPI.refreshSB(entity);
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
        if(Main.red.contains(e.getPlayer())) {
            e.setRespawnLocation(RegionAPI.getRedSpawn().add(0.5, 0, 0.5));
            KitAPI.loadRed(e.getPlayer());
        }
        else if(Main.blue.contains(e.getPlayer())) {
            e.setRespawnLocation(RegionAPI.getBlueSpawn().add(0.5, 0, 0.5));
            KitAPI.loadBlue(e.getPlayer());
        }
    }

    public static Block getNearestBlock(Block block) {
        if (block.getType().name().contains("SLAB") || block.getType().name().contains("STAIRS")) {
            return block.getLocation().add(0, 1, 0).getBlock();
        }

        BlockFace[] faces = {BlockFace.DOWN, BlockFace.UP, BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST};

        for(BlockFace face : faces) {
            Block b = block.getRelative(face);
            if(b.getType() == Material.AIR) {
                return b;
            }
        }

        return null;
    }
}
