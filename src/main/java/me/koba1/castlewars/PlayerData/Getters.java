package me.koba1.castlewars.PlayerData;

import me.koba1.castlewars.Files.PlayerData;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.yaml.snakeyaml.Yaml;

public class Getters {

    private static FileConfiguration config = PlayerData.get();

    public static int getKills(Player player) {
        try {
            return config.getInt("Players." + player.getUniqueId() + ".kills");
        } catch (NullPointerException ex) { return 0; }
    }

    public static int getDeaths(Player player) {
        try {
            return config.getInt("Players." + player.getUniqueId() + ".deaths");
        } catch (NullPointerException ex) { return 0; }
    }

    public static int getWins(Player player) {
        try {
            return config.getInt("Players." + player.getUniqueId() + ".wins");
        } catch (NullPointerException ex) { return 0; }
    }

    public static int getMatches(Player player) {
        try {
            return config.getInt("Players." + player.getUniqueId() + ".matches");
        } catch (NullPointerException ex) { return 0; }
    }

    public static int getPoints(Player player) {
        try {
            return config.getInt("Players." + player.getUniqueId() + ".points");
        } catch (NullPointerException ex) { return 0; }
    }

    public static boolean enoughPoints(Player player, int points) {
        if(getPoints(player) >= points) {
            return true;
        }

        return false;
    }
}
