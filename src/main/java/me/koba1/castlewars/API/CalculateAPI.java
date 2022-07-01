package me.koba1.castlewars.API;

import me.koba1.castlewars.Main;

public class CalculateAPI {

    private static Main m = Main.getPlugin(Main.class);

    public static final int minimum_players = m.getConfig().getInt("minimum_players");

    public static int getMoreNeed() {
        int lobbys = RegionAPI.getLobbyPlayers().size();
        if(lobbys < minimum_players) {
            return minimum_players - lobbys;
        }
        return -1;
    }
}
