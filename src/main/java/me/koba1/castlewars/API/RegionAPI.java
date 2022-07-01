package me.koba1.castlewars.API;

import me.koba1.castlewars.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class RegionAPI {
    private static Main m = Main.getPlugin(Main.class);

    public static ArrayList<Player> getLobbyPlayers() {
        ArrayList<Player> players = new ArrayList<>();

        for(Player player : ConfigAPI.world.getPlayers()) {
            if(inLobby(player)) {
                players.add(player);
            }
        }

        return players;
    }

    public static ArrayList<Player> getGamePlayers() {
        ArrayList<Player> players = new ArrayList<>();

        for(Player player : ConfigAPI.world.getPlayers()) {
            if(inGameMap(player)) {
                players.add(player);
            }
        }

        return players;
    }

    public static void kickPlayer(Player p) {
        try {
            String[] locString = m.getConfig().getString("spawn_location").split(" ");
            Integer[] loc = getInts(locString);
            Location loction = getSpawnLocation();

            p.teleport(loction.add(0.5, 0, 0.5));

            Main.teams.remove(p);
            Main.blue.remove(p);
            Main.red.remove(p);
        } catch (NullPointerException ex) {
            Bukkit.getLogger().severe("Cannot load spawn location from config!");
        }
    }

    public static boolean inMap(Player player) {
        if(player.getWorld().getName().equalsIgnoreCase(ConfigAPI.world.getName()))
            return true;

        return false;
    }

    public static boolean inLobby(Player player) {
        String[] Sloc1 = m.getConfig().getString("lobby_location_1").split(" ");
        String[] Sloc2 = m.getConfig().getString("lobby_location_2").split(" ");

        Integer[] loc1 = getInts(Sloc1);
        Integer[] loc2 = getInts(Sloc2);

        int maxX = Math.max(loc1[0], loc2[0]);
        int maxY = Math.max(loc1[1], loc2[1]);
        int maxZ = Math.max(loc1[2], loc2[2]);

        int minX = Math.min(loc1[0], loc2[0]);
        int minY = Math.min(loc1[1], loc2[1]);
        int minZ = Math.min(loc1[2], loc2[2]);

        int playerX = player.getLocation().getBlockX();
        int playerY = player.getLocation().getBlockY();
        int playerZ = player.getLocation().getBlockZ();

        if(playerX >= minX && playerX <= maxX) {
            if(playerY >= minY && playerY <= maxY) {
                if(playerZ >= minZ && playerZ <= maxZ) {
                    return true;
                }
            }
        }

        return false;
    }



    public static boolean inGameMap(Player player) {
        String[] Sloc1 = m.getConfig().getString("map_location_1").split(" ");
        String[] Sloc2 = m.getConfig().getString("map_location_2").split(" ");

        Integer[] loc1 = getInts(Sloc1);
        Integer[] loc2 = getInts(Sloc2);

        int maxX = Math.max(loc1[0], loc2[0]);
        int maxY = Math.max(loc1[1], loc2[1]);
        int maxZ = Math.max(loc1[2], loc2[2]);

        int minX = Math.min(loc1[0], loc2[0]);
        int minY = Math.min(loc1[1], loc2[1]);
        int minZ = Math.min(loc1[2], loc2[2]);

        int playerX = player.getLocation().getBlockX();
        int playerY = player.getLocation().getBlockY();
        int playerZ = player.getLocation().getBlockZ();

        if(playerX >= minX && playerX <= maxX) {
            if(playerY >= minY && playerY <= maxY) {
                if(playerZ >= minZ && playerZ <= maxZ) {
                    return true;
                }
            }
        }

        return false;
    }

    public static void sendMessageToLobby(String message) {
        for(Player player : getLobbyPlayers()) {
            player.sendMessage(message);
        }
    }

    public static void sendMessageToGame(String message) {
        for(Player player : getGamePlayers()) {
            player.sendMessage(message);
        }
    }

    public static Integer[] getInts(String[] string) {
        ArrayList<Integer> list = new ArrayList<>();
        for(String str : string) {
            list.add(Integer.parseInt(str));
        }

        return list.toArray(new Integer[list.size()]);
    }

    public static Location getBlueSpawn() {
        try {
            Integer[] locs = ConfigAPI.convert("blue_spawn");
            return new Location(
                    ConfigAPI.world,
                    locs[0],
                    locs[1],
                    locs[2],
                    locs[3],
                    locs[4]
            );
        } catch (NullPointerException ex) {
            Bukkit.getLogger().severe("Cannot load blue spawn location from config!");
        }
        return null;
    }

    public static Location getRedSpawn() {
        try {
            Integer[] locs = ConfigAPI.convert("red_spawn");
            return new Location(
                    ConfigAPI.world,
                    locs[0],
                    locs[1],
                    locs[2],
                    locs[3],
                    locs[4]
            );
        } catch (NullPointerException ex) {
            Bukkit.getLogger().severe("Cannot load red spawn location from config!");
        }

        return null;
    }

    public static void teleportToSpectate(Player p) {
        p.teleport(getSpectateLocation().add(0.5, 0, 0.5));
    }

    public static Location getSpectateLocation() {
        try {
            Integer[] locs = ConfigAPI.convert("spectator_location");
            return new Location(
                    ConfigAPI.world,
                    locs[0],
                    locs[1],
                    locs[2],
                    locs[3],
                    locs[4]
            );
        } catch (NullPointerException ex) {
            Bukkit.getLogger().severe("Cannot load spectator location from config!");
        }

        return null;
    }

    public static Location getSpawnLocation() {
        try {
            Integer[] locs = ConfigAPI.convert("spawn_location");
            return new Location(
                    ConfigAPI.world,
                    locs[0],
                    locs[1],
                    locs[2],
                    locs[3],
                    locs[4]
            );
        } catch (NullPointerException ex) {
            Bukkit.getLogger().severe("Cannot load spawn location from config!");
        }

        return null;
    }

    public static String convertLocToString(Location loc) {
        return loc.getBlockX() + " " + loc.getBlockY() + " " + loc.getBlockZ();
    }

    public static String convertLocToStringYawPitch(Location loc) {
        return loc.getBlockX() + " " + loc.getBlockY() + " " + loc.getBlockZ() + " " + (int) loc.getYaw() + " " + (int) loc.getPitch();
    }
}
