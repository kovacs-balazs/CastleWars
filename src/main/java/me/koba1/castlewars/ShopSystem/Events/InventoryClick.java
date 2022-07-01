package me.koba1.castlewars.ShopSystem.Events;

import me.koba1.castlewars.Commands.MainCommand;
import me.koba1.castlewars.Files.Messages;
import me.koba1.castlewars.Files.Shop;
import me.koba1.castlewars.Main;
import me.koba1.castlewars.PlayerData.Getters;
import me.koba1.castlewars.PlayerData.Setters;
import me.koba1.castlewars.ShopSystem.ItemData;
import me.koba1.castlewars.ShopSystem.SetupItems;
import me.koba1.castlewars.ShopSystem.ShopInventories;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class InventoryClick implements Listener {

    @EventHandler
    public void onInvClick(InventoryClickEvent e) {
        if(!e.getView().getTitle().equalsIgnoreCase(MainCommand.chatcolor(Shop.get().getString("page1_title"))))
            if(!e.getView().getTitle().equalsIgnoreCase(MainCommand.chatcolor(Shop.get().getString("page2_title"))))
                return;

        e.setCancelled(true);
        if(e.getCurrentItem() == null) return;
        if(e.getCurrentItem().getType() == null) return;
        if(e.getCurrentItem().getType() == Material.AIR) return;
        if(!(e.getWhoClicked() instanceof Player)) return;
        Player p = (Player) e.getWhoClicked();

        if(e.getCurrentItem().isSimilar(SetupItems.nextPage())) {
            p.openInventory(ShopInventories.page2(p));
            return;
        }
        //
        else if(e.getCurrentItem().isSimilar(SetupItems.previousPage())) {
            p.openInventory(ShopInventories.page1(p));
            return;
        }

        else if(e.getCurrentItem().isSimilar(SetupItems.leaveShop())) {
            p.closeInventory();
            return;
        }

        if(!Main.itemSection.containsKey(e.getCurrentItem())) return;

        if(Getters.enoughPoints(p, ItemData.getPrice(e.getCurrentItem()))) {
            if(!isFully(p)) {
                p.getInventory().addItem(withoutLore(e.getCurrentItem()));
                Setters.setPoints(p, Getters.getPoints(p) - ItemData.getPrice(e.getCurrentItem()));

                p.sendMessage(
                        MainCommand.chatcolor(Messages.get().getString("purchase"))
                                .replace("%item%", MainCommand.chatcolor(e.getCurrentItem().getType().name()))
                                .replace("%price%", MainCommand.chatcolor(String.valueOf(ItemData.getPrice(e.getCurrentItem()))))
                                .replace("%amount%", String.valueOf(e.getCurrentItem().getAmount()))
                );

                if(e.getView().getTitle().equalsIgnoreCase(MainCommand.chatcolor(Shop.get().getString("page1_title")))) {
                    p.openInventory(ShopInventories.page1(p));
                } else if(e.getView().getTitle().equalsIgnoreCase(MainCommand.chatcolor(Shop.get().getString("page2_title")))) {
                    p.openInventory(ShopInventories.page2(p));
                }
            } else
                p.sendMessage(MainCommand.chatcolor(Messages.get().getString("full_inventory")));
        } else
            p.sendMessage(MainCommand.chatcolor(Messages.get().getString("not_enough_money")));
    }

    public static ItemStack withoutLore(ItemStack item) {
        ItemStack is = item.clone();
        ItemMeta im = is.getItemMeta();

        int counter = 0;
        ArrayList<String> lore = new ArrayList<>();

        int price = Main.itemSection.get(item).getInt("price");

        for(String str : im.getLore()) {
            counter++;
            if(str.contains(price + "")) {
                continue;
            }

            if(counter == im.getLore().size() - 1) {
                if(ChatColor.stripColor(str).equalsIgnoreCase("")) {
                    break;
                }
            }

            lore.add(str);
        }

        im.setLore(lore);

        is.setItemMeta(im);
        return is;
    }

    public static boolean isFully(Player p) {
        for(ItemStack is : p.getInventory().getStorageContents()) {
            if(is == null)
                return false;
        }

        return true;
    }
}
