package me.koba1.castlewars.Events;

import me.koba1.castlewars.API.ConfigAPI;
import me.koba1.castlewars.API.PlayerAPI;
import me.koba1.castlewars.API.TeamAPI;
import me.koba1.castlewars.Main;
import me.koba1.castlewars.PlayerData.Getters;
import me.koba1.castlewars.PlayerData.Setters;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;

public class JoinEvent implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Setters.setupPlayer(e.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Main.teams.remove(e.getPlayer());
        Main.blue.remove(e.getPlayer());
        Main.red.remove(e.getPlayer());

        if(e.getPlayer().getWorld().getName().equalsIgnoreCase(ConfigAPI.world.getName())) {
            e.getPlayer().getInventory().clear();

            if(Main.teams.containsKey(e.getPlayer())) {

                try {
                    if (PlayerAPI.getHaveBanner(e.getPlayer()).equals(TeamAPI.Teams.BLUE)) {
                        //PlayerAPI.resetHat(e.getPlayer());
                        if (e.getPlayer().getLocation().getBlock().getType() == Material.AIR) {
                            e.getPlayer().getLocation().getBlock().setType(Material.BLUE_BANNER);
                        } else {
                            getNearestBlock(e.getPlayer().getLocation().getBlock()).setType(Material.BLUE_BANNER);
                        }
                    } else {
                        if (e.getPlayer().getLocation().getBlock().getType() == Material.AIR) {
                            e.getPlayer().getLocation().getBlock().setType(Material.RED_BANNER);
                        } else {
                            getNearestBlock(e.getPlayer().getLocation().getBlock()).setType(Material.RED_BANNER);
                        }
                    }

                } catch (NullPointerException ex) {
                }

                if(Getters.enoughPoints(e.getPlayer(), 10)) {
                    Setters.setPoints(e.getPlayer(), 0);
                }
                Setters.setPoints(e.getPlayer(),Getters.getPoints(e.getPlayer()) - 10);
            }
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
