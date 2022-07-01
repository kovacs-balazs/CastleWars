package me.koba1.castlewars.Events;

import me.koba1.castlewars.API.ConfigAPI;
import me.koba1.castlewars.Main;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.MultipleFacing;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class BlockBreak implements Listener {

    private Main m = Main.getPlugin(Main.class);

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        if(e.getPlayer().hasPermission("castlewars.blockbreak.bypass") || e.getPlayer().isOp()) {
            return;
        }
        if(e.getPlayer().getItemInHand() == null) {
            e.setCancelled(true);
            return;
        }

        if(e.getPlayer().getItemInHand().getType() != Material.WOODEN_AXE) {
            e.setCancelled(true);
            return;
        }
        if(e.getPlayer().getWorld().getName().equals(ConfigAPI.world.getName())) {
            if(e.getBlock() == null) return;
            if(e.getBlock().getType() == Material.SPRUCE_FENCE) {
                Block block = e.getBlock();
                e.setDropItems(false);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        block.setType(Material.SPRUCE_FENCE);
                        BlockData data = block.getBlockData();
                        if(data instanceof MultipleFacing) {
                            for(BlockFace face : ((MultipleFacing) data).getAllowedFaces()) {
                                Block fence = block.getWorld().getBlockAt(block.getLocation().add(face.getDirection()));
                                if (fence != null) {
                                    if (fence.getType() != Material.AIR) {
                                        if (fence.getType() != Material.SPRUCE_FENCE) {
                                            ((MultipleFacing) data).setFace(face, true);
                                        }
                                    }
                                }
                            }
                        }
                        block.setBlockData(data);
                        block.getState().update(false, false);
                    }
                }.runTaskLater(m, m.getConfig().getInt("fence_rebuild") * 20L);
            }
        }
    }
}
