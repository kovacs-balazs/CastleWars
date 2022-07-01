package me.koba1.castlewars.Events.Banner;

import me.koba1.castlewars.API.ConfigAPI;
import me.koba1.castlewars.API.PlayerAPI;
import me.koba1.castlewars.API.RegionAPI;
import me.koba1.castlewars.API.TeamAPI;
import me.koba1.castlewars.Commands.MainCommand;
import me.koba1.castlewars.Files.Messages;
import me.koba1.castlewars.Main;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class BannerInteraction implements Listener {

    @EventHandler
    public void onBannerInteraction(PlayerInteractEvent e) {
        if (e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (!e.getPlayer().getWorld().getName().equalsIgnoreCase(ConfigAPI.world.getName())) return;
        if (e.getClickedBlock() == null) return;
        if (!e.getClickedBlock().getType().name().contains("BANNER")) return;

        if (e.getPlayer().getInventory().contains(new ItemStack(Material.BLUE_BANNER)) || e.getPlayer().getInventory().contains(new ItemStack(Material.RED_BANNER))) {
            if(e.getClickedBlock().getLocation().equals(ConfigAPI.getBlueBanner()) && e.getClickedBlock().getType().name().contains("BLUE")) {
                if(Main.red.contains(e.getPlayer())) {
                    return;
                }
            }
            if(e.getClickedBlock().getLocation().equals(ConfigAPI.getRedBanner()) && e.getClickedBlock().getType().name().contains("RED")) {
                if (Main.blue.contains(e.getPlayer())) {
                    return;
                }
            }

            if(e.getClickedBlock().getLocation().equals(ConfigAPI.getBlueBanner()) && !e.getClickedBlock().getType().name().contains("BLUE")) {
                e.getPlayer().sendMessage(MainCommand.chatcolor(Messages.get().getString("you_have_banner")));
            }

            if(e.getClickedBlock().getLocation().equals(ConfigAPI.getRedBanner()) && !e.getClickedBlock().getType().name().contains("RED")) {
                e.getPlayer().sendMessage(MainCommand.chatcolor(Messages.get().getString("you_have_banner")));
            }

            return;
        }

        if(Main.blue.contains(e.getPlayer()) && e.getClickedBlock().getType().name().contains("BLUE") && e.getClickedBlock().getLocation().equals(ConfigAPI.getBlueBanner())) {
            return;
        }
        else if(Main.red.contains(e.getPlayer()) && e.getClickedBlock().getType().name().contains("RED") && e.getClickedBlock().getLocation().equals(ConfigAPI.getRedBanner())) {
            return;
        }

        if(Main.red.contains(e.getPlayer()) || Main.blue.contains(e.getPlayer())) {
            String name = e.getClickedBlock().getType().name();
            int firstEmpty = e.getPlayer().getInventory().firstEmpty();
            e.getPlayer().getInventory().setItem(firstEmpty, new ItemStack(e.getClickedBlock().getType(), 1));
            e.getPlayer().getInventory().setHeldItemSlot(firstEmpty);
            e.getClickedBlock().setType(Material.AIR);

            String message = MainCommand.chatcolor(Messages.get().getString("banner_stole"));

            if (Main.red.contains(e.getPlayer())) {
                if (name.contains("RED")) {
                    //PlayerAPI.bannerHat(e.getPlayer(), TeamAPI.Teams.RED);
                    message = MainCommand.chatcolor(Messages.get().getString("banner_pickup")
                            .replace("%team%", "§cRed"));
                } else {
                    //PlayerAPI.bannerHat(e.getPlayer(), TeamAPI.Teams.BLUE);
                    message = message
                            .replace("%stolen_team%", "§cRed");
                    for(Player player : Main.blue)
                        player.sendMessage(message);
                    return;
                }
            }
            //
            else if (Main.blue.contains(e.getPlayer())) {
                if (name.contains("BLUE")) {
                    //PlayerAPI.bannerHat(e.getPlayer(), TeamAPI.Teams.BLUE);
                    message = MainCommand.chatcolor(Messages.get().getString("banner_pickup")
                            .replace("%team%", "§9Blue"));
                } else {
                    message = message
                            .replace("%stolen_team%", "§9Blue");
                    for(Player player : Main.red)
                        player.sendMessage(message);
                    return;
                    //PlayerAPI.bannerHat(e.getPlayer(), TeamAPI.Teams.RED);
                }

            }
            for (Player p : RegionAPI.getGamePlayers()) {
                p.sendMessage(message);
            }
        }
    }
}
