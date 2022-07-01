package me.koba1.castlewars.PlayerData;

import me.koba1.castlewars.Files.PlayerData;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class Setters {

    private static FileConfiguration config = PlayerData.get();

    public static void setupPlayer(Player player) {
        if(config.contains("Players." + player.getUniqueId())) return;

        config.set("Players." + player.getUniqueId() + ".kills", 0);
        config.set("Players." + player.getUniqueId() + ".deaths", 0);
        config.set("Players." + player.getUniqueId() + ".wins", 0);
        config.set("Players." + player.getUniqueId() + ".points", 0);
        config.set("Players." + player.getUniqueId() + ".matches", 0);
        PlayerData.save();
    }

    public static void setKills(Player player, int kills) {
        config.set("Players." + player.getUniqueId() + ".kills", kills);
        PlayerData.save();
    }

    public static void setDeaths(Player player, int deaths) {
        config.set("Players." + player.getUniqueId() + ".deaths", deaths);
        PlayerData.save();
    }

    public static void setWins(Player player, int wins) {
        config.set("Players." + player.getUniqueId() + ".wins", wins);
        PlayerData.save();
    }

    public static void setMatches(Player player, int matches) {
        config.set("Players." + player.getUniqueId() + ".matches", matches);
        PlayerData.save();
    }

    public static void setPoints(Player player, int points) {
        config.set("Players." + player.getUniqueId() + ".points", points);
        PlayerData.save();
    }

    public static void addWins(Player player) {
        setWins(player, Getters.getWins(player) + 1);
    }

    public static void addKills(Player player) {
        setKills(player, Getters.getKills(player) + 1);
    }

    public static void addDeaths(Player player) {
        setDeaths(player, Getters.getDeaths(player) + 1);
    }

    public static void addMatches(Player player) {
        setMatches(player, Getters.getMatches(player) + 1);
    }

    public static void addPoints(Player player) {
        setPoints(player, Getters.getPoints(player) + 1);
    }

    public static void addPoints(Player player, int amount) {
        setPoints(player, Getters.getPoints(player) + amount);
    }
}
