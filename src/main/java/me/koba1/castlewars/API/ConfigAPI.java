package me.koba1.castlewars.API;

import me.koba1.castlewars.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;

public class ConfigAPI {

    private static Main m = Main.getPlugin(Main.class);
    public static final World world = Bukkit.getWorld(m.getConfig().getString("world"));

    private static final String[] blueBannerLoc = m.getConfig().getString("blue_banner.Location").split(" ");
    private static final String[] redBannerLoc = m.getConfig().getString("red_banner.Location").split(" ");
    private static final String[] lobby = m.getConfig().getString("lobby_teleport_location").split(" ");

    public static Location getBlueBanner() {
        Location loc = new Location(world,
                Integer.parseInt(blueBannerLoc[0]),
                Integer.parseInt(blueBannerLoc[1]),
                Integer.parseInt(blueBannerLoc[2]));
        return loc;
    }

    public static Location getRedBanner() {
        Location loc = new Location(world,
                Integer.parseInt(redBannerLoc[0]),
                Integer.parseInt(redBannerLoc[1]),
                Integer.parseInt(redBannerLoc[2]));
        return loc;
    }

    public static Location getLobby() {
        try {
            Location loc = new Location(world,
                    Integer.parseInt(lobby[0]),
                    Integer.parseInt(lobby[1]),
                    Integer.parseInt(lobby[2]),
                    Integer.parseInt(lobby[3]),
                    Integer.parseInt(lobby[4]));
            return loc.add(0.5, 0, 0.5);
        } catch (NullPointerException e) {
            Bukkit.getLogger().severe("Can't load lobby location from config!");
            return null;
        }
    }

    public static Integer[] convert(String configString) {
        String[] string = m.getConfig().getString(configString).split(" ");
        ArrayList<Integer> list = new ArrayList<>();
        for(String str : string) {
            list.add(Integer.parseInt(str));
        }

        return list.toArray(new Integer[list.size()]);
    }
}
