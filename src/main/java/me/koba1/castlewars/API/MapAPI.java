package me.koba1.castlewars.API;

import me.koba1.castlewars.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Rotatable;

public class MapAPI {

    private static Main m = Main.getPlugin(Main.class);
    public static final World world = Bukkit.getWorld(m.getConfig().getString("world"));

    private static final Material redBanner = Material.RED_BANNER;
    private static final Material blueBanner = Material.BLUE_BANNER;
    private static final String blueFacing = m.getConfig().getString("blue_banner.Facing");
    private static final String redFacing = m.getConfig().getString("red_banner.Facing");

    public static void resetBlocks() {
        Integer[] ints1 = RegionAPI.getInts(m.getConfig().getString("map_location_1").split(" "));
        Integer[] ints2 = RegionAPI.getInts(m.getConfig().getString("map_location_2").split(" "));
        int maxX = Math.max(ints1[0], ints2[0]);
        int maxY = Math.max(ints1[1], ints2[1]);
        int maxZ = Math.max(ints1[2], ints2[2]);

        int minX = Math.min(ints1[0], ints2[0]);
        int minY = Math.min(ints1[1], ints2[1]);
        int minZ = Math.min(ints1[2], ints2[2]);

        for(int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    Block block = world.getBlockAt(x, y, z);
                    if(block.getType().name().contains("BANNER")) {
                        block.setType(Material.AIR);
                    }
                }
            }
        }

        placeBannerRed();
        placeBannerBlue();
    }

    public static void placeBannerRed() {
        Block block = ConfigAPI.getRedBanner().getBlock();
        block.setType(redBanner);

        BlockData bd = block.getBlockData();

        if(bd instanceof Rotatable) {
            Rotatable rot = (Rotatable) bd;
            rot.setRotation(BlockFace.valueOf(redFacing.toUpperCase()));
            block.setBlockData(rot);
        }
    }

    public static void placeBannerRed(Location location) {
        Block block = location.getBlock();
        block.setType(redBanner);
    }

    public static void placeBannerBlue() {
        Block block = ConfigAPI.getBlueBanner().getBlock();
        block.setType(blueBanner);

        BlockData bd = block.getBlockData();

        if(bd instanceof Rotatable) {
            Rotatable rot = (Rotatable) bd;
            rot.setRotation(BlockFace.valueOf(blueFacing.toUpperCase()));
            block.setBlockData(rot);
        }
    }

    public static void placeBannerBlue(Location location) {
        Block block = location.getBlock();
        block.setType(blueBanner);
    }
}
