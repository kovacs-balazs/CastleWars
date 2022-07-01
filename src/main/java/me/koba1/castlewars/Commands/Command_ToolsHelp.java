package me.koba1.castlewars.Commands;

import org.bukkit.entity.Player;

public class Command_ToolsHelp {

    public static void toolshelp(Player p) {
        p.sendMessage("§eStone Axe - §7break fences");
        p.sendMessage("§eStone Sword - §7Set banner location (left - blue | right - red)");
        p.sendMessage("§eDiamond Sword - §7Team spawn location (left - blue | right - red)");
        p.sendMessage("§eIron Shovel - §7Lobby spawn location");
        p.sendMessage("§eIron Sword - §7Spawn location after the game");
        p.sendMessage("§eIron Hoe - §7Lobby region select (left - first | right - second position)");
        p.sendMessage("§eStone Hoe - §7Spectator location");
        p.sendMessage("§eStone Axe - §7Map corner selection (left - first | right - second position)");
    }
}
