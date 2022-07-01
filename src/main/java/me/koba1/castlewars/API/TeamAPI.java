package me.koba1.castlewars.API;

import me.koba1.castlewars.Commands.MainCommand;
import me.koba1.castlewars.Files.Messages;
import me.koba1.castlewars.Main;
import org.bukkit.entity.Player;

import java.util.Random;

public class TeamAPI {

    public enum Teams {
        BLUE,
        RED
    }

    public static void randomTeam(Player p) {
        Random rand = new Random();
        int random = rand.nextInt(1);
        if(random == 0) {
            if(!Main.teams.containsKey(p)) {
                Main.teams.put(p, Teams.BLUE);
                Main.blue.add(p);
            }
        } else {
            if(!Main.teams.containsKey(p)) {
                Main.teams.put(p, Teams.RED);
                Main.red.add(p);
            }
        }
    }

    public static void addToTeamPlayer(Player p, Teams teams) {
        Main.teams.put(p, teams);
        if(teams == Teams.BLUE) {
            Main.blue.add(p);
        }
        //
        else if(teams == Teams.RED) {
            Main.red.add(p);
        }
    }

    private static Main m = Main.getPlugin(Main.class);
    public static void addToTeam(Player p) {
        if (!(RegionAPI.getLobbyPlayers().size() >= m.getConfig().getInt("max_players"))) {
            if (Main.blue.size() >= m.getConfig().getInt("teams_limit") && Main.red.size() < m.getConfig().getInt("teams_limit")) {
                addToTeamPlayer(p, Teams.RED);
                p.teleport(ConfigAPI.getLobby());
                return;
            } else if (Main.red.size() >= m.getConfig().getInt("teams_limit") && Main.blue.size() < m.getConfig().getInt("teams_limit")) {
                addToTeamPlayer(p, Teams.BLUE);
                p.teleport(ConfigAPI.getLobby());
                return;
            }
        } else {
            p.sendMessage(MainCommand.chatcolor(Messages.get().getString("lobby_full")));
            return;
        }

        if(Main.blue.size() > Main.red.size()) {
            addToTeamPlayer(p, Teams.RED);
        } else if (Main.red.size() > Main.blue.size()) {
            addToTeamPlayer(p, Teams.BLUE);
        } else {
            randomTeam(p);
        }

        p.teleport(ConfigAPI.getLobby());
    }
}
