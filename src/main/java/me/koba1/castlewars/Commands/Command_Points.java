package me.koba1.castlewars.Commands;

import me.koba1.castlewars.API.RegionAPI;
import me.koba1.castlewars.API.ScoreboardAPI;
import me.koba1.castlewars.Files.Messages;
import me.koba1.castlewars.PlayerData.Getters;
import me.koba1.castlewars.PlayerData.Setters;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class Command_Points {

    public Command_Points(Player p, String[] args) {
        if (args.length == 1) {
            return;
        }

        if(args.length == 3) {
            if(args[0].equalsIgnoreCase("setpoints")) {
                if(!p.hasPermission("castlewars.admin.commands")) return;

                Player target = getPlayer(args[1]);
                if(target == null) {
                    p.sendMessage(MainCommand.chatcolor(Messages.get().getString("target_not_found")));
                    return;
                }

                if(args[2].matches("^[0-9]+$")) {

                    Setters.setPoints(target, Integer.parseInt(args[2]));

                    if(RegionAPI.inMap(target))
                        ScoreboardAPI.refreshSB(target);

                    p.sendMessage(MainCommand.chatcolor(Messages.get().getString("setpoints_admin")
                            .replace("%player%", target.getName())
                            .replace("%amount%", args[2])));

                    target.sendMessage(MainCommand.chatcolor(Messages.get().getString("setpoints_player")
                            .replace("%player%", target.getName())
                            .replace("%amount%", args[2])
                            .replace("%admin%", p.getName()))
                    );
                }
            }
            //
            else if(args[0].equalsIgnoreCase("givepoints")) {
                if(!p.hasPermission("castlewars.admin.commands")) return;

                Player target = getPlayer(args[1]);

                if(target == null) {
                    p.sendMessage(MainCommand.chatcolor(Messages.get().getString("target_not_found")));
                    return;
                }

                if(args[2].matches("^[0-9]+$")) {
                    Setters.setPoints(target, Getters.getPoints(target) + Integer.parseInt(args[2]));

                    if(RegionAPI.inMap(target))
                        ScoreboardAPI.refreshSB(target);

                    p.sendMessage(MainCommand.chatcolor(Messages.get().getString("setpoints_admin")
                            .replace("%player%", target.getName())
                            .replace("%amount%", args[2])));

                    target.sendMessage(MainCommand.chatcolor(Messages.get().getString("setpoints_player")
                            .replace("%player%", target.getName())
                            .replace("%amount%", args[2])
                            .replace("%admin%", p.getName()))
                    );
                }
            }
            //
            else if (args[0].equalsIgnoreCase("takepoints")) {
                if(!p.hasPermission("castlewars.admin.commands")) return;

                Player target = getPlayer(args[1]);
                if(target == null) {
                    p.sendMessage(MainCommand.chatcolor(Messages.get().getString("target_not_found")));
                    return;
                }

                if(args[2].matches("^[0-9]+$")) {
                    if (Getters.enoughPoints(target, Integer.parseInt(args[2]))) {
                        Setters.setPoints(target, Getters.getPoints(target) - Integer.parseInt(args[2]));

                        if(RegionAPI.inMap(target))
                            ScoreboardAPI.refreshSB(target);

                        p.sendMessage(MainCommand.chatcolor(Messages.get().getString("takepoints_admin")
                                .replace("%player%", target.getName())
                                .replace("%amount%", args[2])));

                        target.sendMessage(MainCommand.chatcolor(Messages.get().getString("takepoints_player")
                                .replace("%player%", target.getName())
                                .replace("%amount%", args[2])
                                .replace("%admin%", p.getName()))
                        );
                    } else {

                        p.sendMessage(MainCommand.chatcolor(Messages.get().getString("takepoints_admin")
                                .replace("%player%", target.getName())
                                .replace("%amount%", String.valueOf(Getters.getPoints(target)))));

                        target.sendMessage(MainCommand.chatcolor(Messages.get().getString("takepoints_player")
                                .replace("%player%", target.getName())
                                .replace("%amount%", String.valueOf(Getters.getPoints(target)))
                                .replace("%admin%", p.getName())));

                        Setters.setPoints(target, 0);

                        if(RegionAPI.inMap(target))
                            ScoreboardAPI.refreshSB(target);
                    }
                }
            }
        }
    }

    public static Player getPlayer(String name) {
        Player player = Bukkit.getPlayer(name);
        if (player == null) {
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(name);
            if (offlinePlayer != null) {
                player = offlinePlayer.getPlayer();
            }
        }

        return player;
    }
}
