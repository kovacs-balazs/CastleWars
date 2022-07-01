package me.koba1.castlewars.Commands;

import me.koba1.castlewars.Files.Kits;
import me.koba1.castlewars.Files.Messages;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Command_kits {

    public static void kits(Player p, String[] args) {
        if(args.length == 2) {
            if(args[0].equalsIgnoreCase("createkit")) {
                if(args[1].equalsIgnoreCase("blue")) {
                    setToConfig("blue", p.getInventory().getContents());
                    p.sendMessage(MainCommand.chatcolor(Messages.get().getString("saved_kit")));
                }
                //
                else if(args[1].equalsIgnoreCase("red")) {
                    setToConfig("red", p.getInventory().getContents());
                    p.sendMessage(MainCommand.chatcolor(Messages.get().getString("saved_kit")));
                }
            }
            //
            else if(args[0].equalsIgnoreCase("removekit")) {
                if(args[1].equalsIgnoreCase("blue")) {
                    removeConfig("blue");
                    p.sendMessage(MainCommand.chatcolor(Messages.get().getString("remove_kit")));
                }
                //
                else if(args[1].equalsIgnoreCase("red")) {
                    removeConfig("red");
                    p.sendMessage(MainCommand.chatcolor(Messages.get().getString("remove_kit")));
                }
            }
        }
    }

    public static void setToConfig(String team, ItemStack[] itemStacks) {
        Kits.get().set("Kits." + team, itemStacks);
        Kits.save();
    }

    public static void removeConfig(String team) {
        Kits.get().set("Kits." + team, null);
        Kits.save();
    }
}
