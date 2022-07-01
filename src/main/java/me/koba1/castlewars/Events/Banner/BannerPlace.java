package me.koba1.castlewars.Events.Banner;

import me.koba1.castlewars.API.*;
import me.koba1.castlewars.Commands.MainCommand;
import me.koba1.castlewars.Files.Messages;
import me.koba1.castlewars.Main;
import me.koba1.castlewars.PlayerData.Getters;
import me.koba1.castlewars.PlayerData.Setters;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;


public class BannerPlace implements Listener {

    @EventHandler
    public void onBannerPlace(BlockPlaceEvent e) {
        if(!e.getPlayer().getWorld().getName().equalsIgnoreCase(ConfigAPI.world.getName())) return;
        if(e.getBlockPlaced() != null) {
            if(e.getBlockPlaced().getType().name().contains("BANNER")) {
                TeamAPI.Teams placeBannerOfTeam = (e.getBlockPlaced().getType().name().contains("BLUE") ? TeamAPI.Teams.BLUE : TeamAPI.Teams.RED);
                if(placeBannerOfTeam.equals(TeamAPI.Teams.BLUE)) {
                    if(Main.blue.contains(e.getPlayer())) {
                        if(e.getBlockPlaced().getLocation().equals(ConfigAPI.getBlueBanner())) {

                            String message = MainCommand.chatcolor(Messages.get().getString("banner_return"));
                            message = message
                                    .replace("%team%", "§9Blue")
                                    .replace("%player%", e.getPlayer().getName());
                            for(Player p : RegionAPI.getGamePlayers()) {
                                p.sendMessage(message);
                            }

                            // PlayerAPI.resetHat(e.getPlayer());
                            Setters.setPoints(e.getPlayer(), Getters.getPoints(e.getPlayer()) + 10);
                            ScoreboardAPI.refreshSB(e.getPlayer());
                            return;
                        }

                        e.setCancelled(true);
                        return;
                    }

                    if(e.getPlayer().hasPermission("castlewars.blockplace.bypass")) {
                        return;
                    }

                    e.setCancelled(true);

                    if(e.getBlockAgainst() != null) {
                        if(e.getBlockAgainst().getType() == Material.RED_BANNER) {
                            if (e.getBlockAgainst().getLocation().equals(ConfigAPI.getRedBanner())) {
                                // PlayerAPI.resetHat(e.getPlayer());
                                GameAPI_Ending.endGame(TeamAPI.Teams.RED);
                                return;
                            }
                        }
                    }

                    if(e.getBlockPlaced().getLocation().equals(ConfigAPI.getRedBanner())) {
                      //  PlayerAPI.resetHat(e.getPlayer());
                        GameAPI_Ending.endGame(TeamAPI.Teams.RED);
                    }
                } else { // RED
                    if(e.getPlayer().hasPermission("castlewars.blockplace.bypass")) {
                        return;
                    }

                    if(Main.red.contains(e.getPlayer())) {
                        if(e.getBlockPlaced().getLocation().equals(ConfigAPI.getRedBanner())) {

                            String message = MainCommand.chatcolor(Messages.get().getString("banner_return"));
                            message = message
                                    .replace("%team%", "§cRed")
                                    .replace("%player%", e.getPlayer().getName());
                            for(Player p : RegionAPI.getGamePlayers()) {
                                p.sendMessage(message);
                            }

                           // PlayerAPI.resetHat(e.getPlayer());
                            Setters.setPoints(e.getPlayer(), Getters.getPoints(e.getPlayer()) + 10);

                            ScoreboardAPI.refreshSB(e.getPlayer());
                            return;
                        }

                        e.setCancelled(true);
                        return;
                    }

                    e.setCancelled(true);

                    if(e.getBlockAgainst() != null) {
                        if(e.getBlockAgainst().getType() == Material.BLUE_BANNER) {
                            if (e.getBlockAgainst().getLocation().equals(ConfigAPI.getBlueBanner())) {
                                //PlayerAPI.resetHat(e.getPlayer());
                                GameAPI_Ending.endGame(TeamAPI.Teams.BLUE);
                                return;
                            }
                        }
                    }
                    if(e.getBlockPlaced().getLocation().equals(ConfigAPI.getBlueBanner())) {
                        //PlayerAPI.resetHat(e.getPlayer());
                        GameAPI_Ending.endGame(TeamAPI.Teams.BLUE);
                    }
                }
            }
        }
    }
}
