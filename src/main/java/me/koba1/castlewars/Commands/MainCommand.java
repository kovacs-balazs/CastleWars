package me.koba1.castlewars.Commands;

import me.koba1.castlewars.API.*;
import me.koba1.castlewars.API.Kits.KitAPI;
import me.koba1.castlewars.Files.Kits;
import me.koba1.castlewars.Files.Messages;
import me.koba1.castlewars.Files.Shop;
import me.koba1.castlewars.Main;
import me.koba1.castlewars.ShopSystem.ShopInventories;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class MainCommand implements CommandExecutor, TabCompleter {
    private Main m = Main.getPlugin(Main.class);

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if(cmd.getName().equalsIgnoreCase("castlewars")) {
            if(sender instanceof Player) {
                Player p = (Player) sender;
                Command_kits.kits(p, args);
                new Command_stats(p, args);
                new Command_StatsClear(p, args);
                new Command_Points(p, args);
                if(args.length == 0) {
                }
                //
                else if(args.length == 1) {
                    if(args[0].equalsIgnoreCase("play")) {
                        if (RegionAPI.inLobby(p)) {
                            p.sendMessage(chatcolor(Messages.get().getString("in_lobby")));
                            return false;
                        }

                        if (GameAPI_Main.isGame(p)) {
                            p.sendMessage(chatcolor(Messages.get().getString("in_game")));
                            return false;
                        }

                        if(GameAPI_Main.isRunning) {
                            p.sendMessage(chatcolor(Messages.get().getString("game_running")));
                            return false;
                        }

                        if (RegionAPI.getLobbyPlayers().size() >= m.getConfig().getInt("max_players")) {
                            p.sendMessage(chatcolor(Messages.get().getString("lobby_full")));
                            return false;
                        }

                        //p.teleport(ConfigAPI.getLobby());
                        p.getInventory().clear();
                        TeamAPI.addToTeam(p);

                        if (Main.red.contains(p))
                            KitAPI.loadRed(p);
                        else if (Main.blue.contains(p))
                            KitAPI.loadBlue(p);

                        if (RegionAPI.getLobbyPlayers().size() < CalculateAPI.minimum_players) {
                            for (Player player : RegionAPI.getLobbyPlayers()) {
                                player.sendMessage(chatcolor(Messages.get().getString("join_message_need_more")
                                        .replace("%player%", p.getName())
                                        .replace("%number%", String.valueOf(RegionAPI.getLobbyPlayers().size()))
                                        .replace("%max%", m.getConfig().getString("max_players"))
                                        .replace("%need_players%", String.valueOf(CalculateAPI.getMoreNeed()))));
                            }
                            return false;
                        }

                        for (Player player : RegionAPI.getLobbyPlayers()) {
                            player.sendMessage(chatcolor(Messages.get().getString("join_message")
                                    .replace("%number%", String.valueOf(RegionAPI.getLobbyPlayers().size()))
                                    .replace("%max%", m.getConfig().getString("max_players"))
                                    .replace("%player%", p.getName())));
                        }

                        ScoreboardAPI.refreshSB(p);
                    }
                    //
                    else if(args[0].equalsIgnoreCase("tools")) {
                        if(!p.hasPermission("castlewars.usetools")) {
                            return false;
                        }

                        Command_ToolsHelp.toolshelp(p);
                    }
                    //
//                    else if(args[0].equalsIgnoreCase("spectate")) {
//                        if(RegionAPI.inLobby(p)) {
//                            p.sendMessage(chatcolor(Messages.get().getString("in_lobby")));
//                            return false;
//                        }
//                        if(GameAPI_Main.isGame(p)) {
//                            p.sendMessage(chatcolor(Messages.get().getString("in_game")));
//                            return false;
//                        }
//                        RegionAPI.teleportToSpectate(p);
//                        p.sendMessage(chatcolor(Messages.get().getString("spectate_tp")));
//                        return false;
//                    }
                    //
                    else if(args[0].equalsIgnoreCase("reload")) {
                        if(p.isOp()) {
                            Messages.reload();
                            Kits.reload();
                            Shop.reload();
                            Main.getPlugin(Main.class).reloadConfig();
                            p.sendMessage("§aConfigs reloaded!");
                        }
                    }
                    //
                    else if(args[0].equalsIgnoreCase("leave")) {
                        if(RegionAPI.getLobbyPlayers().contains(p)) {
                            p.getInventory().clear();
                            RegionAPI.kickPlayer(p);
                            //p.teleport(RegionAPI.getSpawnLocation().add(0.5, 0, 0.5));

//                            Main.teams.remove(p);
//                            Main.blue.remove(p);
//                            Main.red.remove(p);

                            RegionAPI.sendMessageToLobby(chatcolor(Messages.get().getString("leave_message")
                                    .replace("%player%", p.getName())));
                            return false;
                        }

                        if(!Main.teams.containsKey(p)) {
                            p.sendMessage(chatcolor(Messages.get().getString("not_in_lobby_game")));
                            return false;
                        }
                    }
                    //
//                    else if(args[0].equalsIgnoreCase("start")) {
//                        GameAPI_Main.starting();
//                        return false;
//                    }
                    //
                } else if (args.length == 2) {
                    if(args[0].equalsIgnoreCase("shop")) {
                        if(args[1].equals("ONLY_npc")) {
                            p.openInventory(ShopInventories.page1(p));
                        }
                    }
                }
            }
        }
        return false;
    }

    public static String chatcolor(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if(cmd.getName().equalsIgnoreCase("castlewars")) {
            if(args.length == 1) {
                if(sender.hasPermission("castlewars.admin.commands")) {
                    return Arrays.asList("play", "leave", "spectate", "setpoints", "givepoints", "takepoints");
                }
                return Arrays.asList(
                        "play",
                        "leave",
                        "spectate"
                );
            }
        }
        return null;
    }
}
