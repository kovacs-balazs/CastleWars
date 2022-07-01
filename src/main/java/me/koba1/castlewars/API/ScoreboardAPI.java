package me.koba1.castlewars.API;

import me.koba1.castlewars.Main;
import me.koba1.castlewars.PlayerData.Getters;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ScoreboardAPI {

    private static Main m = Main.getPlugin(Main.class);

    private static List<String> lines() {
        List<String> returnString = new LinkedList<>();
        List<String> sb = m.getConfig().getStringList("Scoreboard.lines");

        Collections.reverse(sb);

        for(String str : sb) {
            returnString.add(ChatColor.translateAlternateColorCodes('&', str));
        }
        return sb;
    }

    private static String replace(Player p, String str) {
        return str
                .replace("%kills%", Getters.getKills(p) + "")
                .replace("%deaths%", Getters.getDeaths(p) + "")
                .replace("%wins%", Getters.getWins(p) + "")
                .replace("%losses%", (Getters.getMatches(p) - 1 - Getters.getWins(p)) + "")
                .replace("%points%", Getters.getPoints(p) + "");
    }

    public static void refreshSB(Player p) {
        Scoreboard sb = Bukkit.getScoreboardManager().getNewScoreboard();

        Objective obj = sb.registerNewObjective("Something", "dummy");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);

        obj.setDisplayName(ChatColor.translateAlternateColorCodes('&', m.getConfig().getString("Scoreboard.title")));

        int counter = 0;

        for(String line : lines()) {
            obj.getScore(ChatColor.translateAlternateColorCodes('&', replace(p, line))).setScore(counter);
            counter++;
        }

        p.setScoreboard(sb);
    }
}
