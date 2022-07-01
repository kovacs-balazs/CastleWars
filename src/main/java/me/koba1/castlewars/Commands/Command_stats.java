package me.koba1.castlewars.Commands;

import me.koba1.castlewars.Files.Messages;
import me.koba1.castlewars.PlayerData.Getters;
import org.bukkit.entity.Player;

import java.util.List;

public class Command_stats {

    public Command_stats(Player p, String[] args) {
        if(args.length == 1) {
            if(args[0].equalsIgnoreCase("stats")) {
                List<String> list = Messages.get().getStringList("statistics");
                String message = "";
                for(String str : list) {

                    message += "\n" + str;
/*                    p.sendMessage(MainCommand.chatcolor(str)
                            .replace("%player%", p.getName())
                            .replace("%points%", WrapperMongoDB.getPoints(p) + "")
                            .replace("%kills%", WrapperMongoDB.getKills(p) + "")
                            .replace("%deaths%", WrapperMongoDB.getDeaths(p) + "")
                            .replace("%wins%", WrapperMongoDB.getWins(p) + "")
                            .replace("%matches%", WrapperMongoDB.getMatches(p) + ""));*/
                }

                p.sendMessage(MainCommand.chatcolor(message)
                        .replaceFirst("\n", "")
                        .replace("%player%", p.getName())
                        .replace("%points%", Getters.getPoints(p) + "")
                        .replace("%kills%", Getters.getKills(p) + "")
                        .replace("%deaths%", Getters.getDeaths(p) + "")
                        .replace("%wins%", Getters.getWins(p) + "")
                        .replace("%matches%", Getters.getMatches(p) + ""));
            }
        }

        else if(args.length == 2) {
            if(args[0].equalsIgnoreCase("stats")) {

                if(!p.hasPermission("castlewars.admin.commands")) return;

                Player target = Command_Points.getPlayer(args[1]);
                if(target == null) {
                    p.sendMessage(MainCommand.chatcolor(Messages.get().getString("target_not_found")));
                    return;
                }

                List<String> list = Messages.get().getStringList("statistics");
                String message = "";
                for(String str : list) {
                    message += "\n" + str;
                }

                p.sendMessage(MainCommand.chatcolor(message)
                        .replaceFirst("\n", "")
                        .replace("%player%", p.getName())
                        .replace("%points%", Getters.getPoints(target) + "")
                        .replace("%kills%", Getters.getKills(target) + "")
                        .replace("%deaths%", Getters.getDeaths(target) + "")
                        .replace("%wins%", Getters.getWins(target) + "")
                        .replace("%matches%", Getters.getMatches(target) + ""));
            }
        }
    }
}
