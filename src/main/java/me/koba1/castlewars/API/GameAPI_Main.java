package me.koba1.castlewars.API;

import me.koba1.castlewars.API.Kits.KitAPI;
import me.koba1.castlewars.Commands.MainCommand;
import me.koba1.castlewars.Files.Messages;
import me.koba1.castlewars.Main;
import me.koba1.castlewars.PlayerData.Getters;
import me.koba1.castlewars.PlayerData.Setters;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class GameAPI_Main {

    public static boolean isRunning;

    public static void setupGame() {
        MapAPI.placeBannerBlue();
        MapAPI.placeBannerRed();
    }

    public static void preStart() {
        Main.gameData.put("prestart", true);
    }

    public static void startGame() {
        setupGame();
//        for(Player p : RegionAPI.getLobbyPlayers()) {
//            TeamAPI.addToTeam(p);
//        }
        Main.gameData.put("prestart", false);
        Main.gameData.put("start", true);

        teleportsTeams();

        isRunning = true;

        KitAPI.loadBlue();
        KitAPI.loadRed();
    }

    public static void endGame() {
        Main.gameData.put("prestart", false);
        Main.gameData.put("start", false);

        Main.teams.clear();
        Main.blue.clear();
        Main.red.clear();

        isRunning = false;
        starting();
    }

    private static Main m = Main.getPlugin(Main.class);

    public static void startTimer() {
        Main.stopTimer.cancel();
        Main.stopTimer = new BukkitRunnable() {
            int time = m.getConfig().getInt("game_stop") * 60;
            @Override
            public void run() {
                long unixTime = System.currentTimeMillis() / 1000;
                int different = (int) (unixTime - Main.gameStartAt);
                if(different > time) {
                    GameAPI_Ending.endGame();
                }
            }
        }.runTaskTimer(m, 0L, 20L);
    }

    public static void starting() {
        Main.gameStartAt = (System.currentTimeMillis() / 1000);
        new BukkitRunnable() {
            @Override
            public void run() {
                if(!isRunning) {
                    if (RegionAPI.getLobbyPlayers().size() >= CalculateAPI.minimum_players) {
                        int different = (int) (System.currentTimeMillis() / 1000 - Main.gameStartAt);
                        if (different >= m.getConfig().getInt("game_start")) {
                            for (Player lobbysPlayer : RegionAPI.getLobbyPlayers()) {
                                PlayerAPI.resetActionBar(lobbysPlayer);
                                lobbysPlayer.sendMessage(MainCommand.chatcolor(Messages.get().getString("start_game")));
                            }
                            startGame();
                            cancel();
                        } else {
                            for (Player lobbysPlayer : RegionAPI.getLobbyPlayers()) {
                                PlayerAPI.sendActionBar(lobbysPlayer, String.valueOf((m.getConfig().getInt("game_start") - different)));
                            }
                        }
                    } else {
                        for (Player lobbyPlayers : RegionAPI.getLobbyPlayers()) {
                            PlayerAPI.sendActionBarNeed(lobbyPlayers, String.valueOf(CalculateAPI.getMoreNeed()));
                        }
                        Main.gameStartAt = (System.currentTimeMillis() / 1000);
                    }
                }
            }
        }.runTaskTimer(Main.getPlugin(Main.class), 0L, 20L);
    }

    public static boolean isGame(Player p) {
        if(Main.teams.containsKey(p)) return true;
        return false;
    }

    public static void teleportBlue(Player p) {
        p.teleport(RegionAPI.getBlueSpawn().add(0.5, 0, 0.5));
    }

    public static void teleportRed(Player p) {
        p.teleport(RegionAPI.getRedSpawn().add(0.5, 0, 0.5));
    }

    public static void teleportsTeams() {
        for(Player lobbyPlayer : RegionAPI.getLobbyPlayers()) {

            if(Main.teams.containsKey(lobbyPlayer)) {
                TeamAPI.Teams team = Main.teams.get(lobbyPlayer);
                if(team == TeamAPI.Teams.BLUE)
                    teleportBlue(lobbyPlayer);
                else if(team == TeamAPI.Teams.RED)
                    teleportRed(lobbyPlayer);
                else
                    RegionAPI.kickPlayer(lobbyPlayer);
            }

            //Setters.addMatches(lobbyPlayer);
            ScoreboardAPI.refreshSB(lobbyPlayer);
        }
    }
}
