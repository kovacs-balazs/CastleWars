package me.koba1.castlewars;

import me.koba1.castlewars.API.GameAPI_Main;
import me.koba1.castlewars.API.MapAPI;
import me.koba1.castlewars.API.TeamAPI;
import me.koba1.castlewars.Commands.MainCommand;
import me.koba1.castlewars.Events.*;
import me.koba1.castlewars.Events.Banner.BannerInteraction;
import me.koba1.castlewars.Events.Banner.BannerPlace;
import me.koba1.castlewars.Events.ToolEvents.*;
import me.koba1.castlewars.Files.Kits;
import me.koba1.castlewars.Files.Messages;
import me.koba1.castlewars.Files.PlayerData;
import me.koba1.castlewars.Files.Shop;
import me.koba1.castlewars.ShopSystem.Events.InventoryClick;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

public final class Main extends JavaPlugin {

    public static HashMap<Player, TeamAPI.Teams> teams;
    public static HashMap<Player, HashMap<String, Integer>> playerCache;
    public static HashMap<ItemStack, ConfigurationSection> itemSection;
    public static ArrayList<Player> red;
    public static ArrayList<Player> blue;
    public static HashMap<String, Boolean> gameData;
    public static BukkitTask stopTimer;
    public static long gameStartAt;

    @Override
    public void onEnable() {
        red = new ArrayList();
        blue = new ArrayList();
        teams = new HashMap<>();
        itemSection = new HashMap<>();
        playerCache = new HashMap<>();
        gameData = new HashMap<>();
        gameStartAt = System.currentTimeMillis() / 1000;
        // Plugin startup logic

        GameAPI_Main.isRunning = false;
        //Messages.setup();

        if(!new File(getDataFolder() + "\\config.yml").exists())
            saveResource("config.yml", false);

        if(!new File(getDataFolder() + "\\messages.yml").exists())
            saveResource("messages.yml", false);

        if(!new File(getDataFolder() + "\\kits.yml").exists()) {
            saveResource("kits.yml", false);
        }

        if(!new File(getDataFolder() + "\\shop.yml").exists()) {
            saveResource("shop.yml", false);
        }

        Shop.setup();
        PlayerData.setup();
        Kits.setup();
        Messages.setup();
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        saveConfig();

        getServer().getPluginManager().registerEvents(new BlockBreak(), this);
        getServer().getPluginManager().registerEvents(new BannerInteraction(), this);
        getServer().getPluginManager().registerEvents(new BannerPlace(), this);
        getServer().getPluginManager().registerEvents(new DeathEvent(), this);
        getServer().getPluginManager().registerEvents(new StoneSword(), this);
        getServer().getPluginManager().registerEvents(new DiamondSword(), this);
        getServer().getPluginManager().registerEvents(new DamageEvent(), this);
        getServer().getPluginManager().registerEvents(new IronShovel(), this);
        getServer().getPluginManager().registerEvents(new InventoryClick(), this);
        getServer().getPluginManager().registerEvents(new IronSword(), this);
        getServer().getPluginManager().registerEvents(new JoinEvent(), this);
        getServer().getPluginManager().registerEvents(new IronHoe(), this);
        getServer().getPluginManager().registerEvents(new StoneHoe(), this);
        getServer().getPluginManager().registerEvents(new ArmorEquip(), this);
        getServer().getPluginManager().registerEvents(new ItemDropEvent(), this);
        getServer().getPluginManager().registerEvents(new LobbyInteraction(), this);
        getServer().getPluginManager().registerEvents(new SwitchItem(), this);
        getServer().getPluginManager().registerEvents(new StoneAxe(), this);
        getServer().getPluginManager().registerEvents(new BlockCommands(), this);
        getServer().getPluginManager().registerEvents(new ChatEvent(), this);

        getCommand("castlewars").setExecutor(new MainCommand());

        MapAPI.resetBlocks();

        GameAPI_Main.starting();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
