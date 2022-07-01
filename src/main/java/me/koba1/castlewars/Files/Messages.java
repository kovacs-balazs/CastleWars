package me.koba1.castlewars.Files;

import me.koba1.castlewars.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Messages {

    private static File cfg;
    private static FileConfiguration file;

    public static Main m = Main.getPlugin(Main.class);

    public static void setup() {
        cfg = new File(m.getDataFolder(), "messages.yml"); //
        if(!cfg.exists()) {
            try {
                cfg.createNewFile();
            } catch (IOException e) {
                // owww
            }
        }
        file = YamlConfiguration.loadConfiguration(cfg);
    }

    public static FileConfiguration get() {
        return file;
    }

    public static void save() {
        try {
            file.save(cfg);
        } catch (IOException e) {
            System.out.println("Can't save language file");
        }
    }

    public static void reload() {
        file = YamlConfiguration.loadConfiguration(cfg);
    }
}
