package me.koba1.castlewars.API;

import me.koba1.castlewars.Commands.MainCommand;
import me.koba1.castlewars.Files.Messages;
import me.koba1.castlewars.Main;
import me.koba1.castlewars.PlayerData.Getters;
import me.koba1.castlewars.PlayerData.Setters;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

public class GameAPI_Ending {

    public static void endGame() {
        for(Player player : Main.blue) {
            player.getInventory().clear();
            //Setters.setPoints(player, Getters.getPoints(player) + 50);
            //Setters.setMatches(player, Getters.getMatches(player) - 1);
//            Setters.addWins(player);
            ScoreboardAPI.refreshSB(player);
            player.teleport(RegionAPI.getSpawnLocation());
        }

        for(Player player : Main.red) {
            player.getInventory().clear();
            //Setters.setPoints(player, Getters.getPoints(player) + 50);
            //Setters.setMatches(player, Getters.getMatches(player) - 1);
//            Setters.addWins(player);
            ScoreboardAPI.refreshSB(player);
            player.teleport(RegionAPI.getSpawnLocation());
        }

        Bukkit.broadcastMessage(MainCommand.chatcolor(Messages.get().getString("game_end_broadcast")));
    }

    public static void endGame(TeamAPI.Teams winnerTeam) {
        if(winnerTeam.equals(TeamAPI.Teams.BLUE)) {
            for(Player player : Main.blue) {
                String title = MainCommand.chatcolor(Messages.get().getString("winner_title.title"));
                String subTitle = MainCommand.chatcolor(Messages.get().getString("winner_title.subtitle"));

                player.sendTitle(title, subTitle);

                player.getInventory().clear();
                Setters.setPoints(player, Getters.getPoints(player) + 50);

                Setters.addMatches(player);
                Setters.addWins(player);

                ScoreboardAPI.refreshSB(player);
                player.teleport(RegionAPI.getSpawnLocation());
            }

            for(Player player : Main.red) {
                String title = MainCommand.chatcolor(Messages.get().getString("defeat_title.title"));
                String subTitle = MainCommand.chatcolor(Messages.get().getString("defeat_title.subtitle"));

                player.sendTitle(title, subTitle);

                Setters.addMatches(player);

                player.getInventory().clear();
                player.teleport(RegionAPI.getSpawnLocation());
            }
            Bukkit.broadcastMessage(MainCommand.chatcolor(Messages.get().getString("game_end_broadcast")
                    .replace("%winner%", "§9Blue")
                    .replace("%loser%", "§cRed")));
        } else {
            for(Player player : Main.red) {
                String title = MainCommand.chatcolor(Messages.get().getString("winner_title.title"));
                String subTitle = MainCommand.chatcolor(Messages.get().getString("winner_title.subtitle"));

                player.sendTitle(title, subTitle);

                player.getInventory().clear();
                Setters.setPoints(player, Getters.getPoints(player) + 50);
                Setters.addMatches(player);
                Setters.addWins(player);
                ScoreboardAPI.refreshSB(player);
                player.teleport(RegionAPI.getSpawnLocation());
            }

            for(Player player : Main.blue) {
                String title = MainCommand.chatcolor(Messages.get().getString("defeat_title.title"));
                String subTitle = MainCommand.chatcolor(Messages.get().getString("defeat_title.subtitle"));

                player.sendTitle(title, subTitle);
                Setters.addMatches(player);
                player.getInventory().clear();
                player.teleport(RegionAPI.getSpawnLocation());
            }
            Bukkit.broadcastMessage(MainCommand.chatcolor(Messages.get().getString("game_end_broadcast")
                    .replace("%winner%", "§cRed")
                    .replace("%loser%", "§9Blue")));
        }
        GameAPI_Main.endGame();
    }
}
