package me.koba1.castlewars.Commands;

import me.koba1.castlewars.API.ScoreboardAPI;
import me.koba1.castlewars.Files.Messages;
import me.koba1.castlewars.PlayerData.Setters;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Command_StatsClear {

    public Command_StatsClear(Player p, String[] args) {
        if(args.length == 1) {
            return;
        }

        if(args.length == 2) {
            if(!args[0].equalsIgnoreCase("clear")) return;
            if(!p.hasPermission("castlewars.admin.commands")) return;

            try {
                Player target = Bukkit.getPlayer(args[1]);

                Setters.setWins(target, 0);
                Setters.setDeaths(target, 0);
                Setters.setMatches(target, 0);
                Setters.setPoints(target, 0);
                Setters.setKills(target, 0);

                ScoreboardAPI.refreshSB(target);
                p.sendMessage(MainCommand.chatcolor(Messages.get().getString("clear_stats_admin")));
                target.sendMessage(MainCommand.chatcolor(Messages.get().getString("clear_stats_player")));
            } catch (NullPointerException ex) {
                Player target = Bukkit.getOfflinePlayer(args[1]).getPlayer();

                Setters.setWins(target, 0);
                Setters.setDeaths(target, 0);
                Setters.setMatches(target, 0);
                Setters.setPoints(target, 0);
                Setters.setKills(target, 0);

                //ScoreboardAPI.refreshSB(target);
                p.sendMessage(MainCommand.chatcolor(Messages.get().getString("clear_stats_admin")));
            }
        }
    }
}
