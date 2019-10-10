package me.mralecroyt.administrador;

import me.mralecroyt.authcore.*;
import org.bukkit.configuration.file.*;
import java.io.*;
import org.bukkit.configuration.*;

public class ConfigAdmin {
    private CoreMain plugin;
    private static File configFile;
    private static FileConfiguration config;

    public ConfigAdmin() {
        this.plugin = CoreMain.getInstance();
    }

    public void createConfigConfig() {
        if (!this.plugin.getDataFolder().exists()) {
            this.plugin.getDataFolder().mkdirs();
        }
        ConfigAdmin.configFile = new File(this.plugin.getDataFolder(), "config.yml");
        if (!ConfigAdmin.configFile.exists()) {
            ConfigAdmin.configFile.getParentFile().mkdirs();
            this.plugin.saveResource("config.yml", false);
        }
        ConfigAdmin.config = (FileConfiguration) new YamlConfiguration();
        try {
            ConfigAdmin.config.load(ConfigAdmin.configFile);
        } catch (IOException | InvalidConfigurationException ex2) {
            final Exception ex;
            final Exception e = ex2;
            e.printStackTrace();
        }
    }

    public static FileConfiguration getConfigConfig() {
        return ConfigAdmin.config;
    }

    public static void saveConfigConfig() {
        try {
            ConfigAdmin.config.save(ConfigAdmin.configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void reloadConfigConfig() {
        ConfigAdmin.config = (FileConfiguration) YamlConfiguration.loadConfiguration(ConfigAdmin.configFile);
    }

}

