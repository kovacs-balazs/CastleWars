package me.koba1.castlewars.Events;

import me.koba1.castlewars.API.ConfigAPI;
import me.koba1.castlewars.API.RegionAPI;
import me.koba1.castlewars.Commands.MainCommand;
import me.koba1.castlewars.Files.Messages;
import me.koba1.castlewars.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class BlockCommands implements Listener {

    private Main m = Main.getPlugin(Main.class);

    @EventHandler
    public void onPlayerPreprocessCommand(PlayerCommandPreprocessEvent e) {
        if(RegionAPI.getLobbyPlayers().contains(e.getPlayer())) {
            String[] args = e.getMessage().split(" ");

            if(!e.getPlayer().getWorld().getName().equalsIgnoreCase(ConfigAPI.world.getName())) return;

            if(RegionAPI.inGameMap(e.getPlayer())) {
                if (e.getPlayer().isOp()) return;
                if (e.getMessage().endsWith("leave")
                        && (e.getMessage().startsWith("/cw") || e.getMessage().startsWith("/castlew") || e.getMessage().startsWith("/cwars") || e.getMessage().startsWith("/castlewars"))) {
                    e.setCancelled(true);
                }
            }

            if (m.getConfig().getStringList("BlockedCommands").contains(args[0].replace("/", ""))) {
                e.setCancelled(true);
                e.getPlayer().sendMessage(MainCommand.chatcolor(Messages.get().getString("cant_use_this_cmd")));
            }
        }
    }
}
