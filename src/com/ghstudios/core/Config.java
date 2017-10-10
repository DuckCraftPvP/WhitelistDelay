package com.ghstudios.core;

import org.bukkit.configuration.file.*;
import java.io.*;
import org.bukkit.configuration.*;
import org.bukkit.plugin.*;

public class Config
{
    static Config instance;
    static Plugin p;
    public static FileConfiguration config;
    static File cfile;
    
    public static Config getInstance() {
        return Config.instance;
    }
    
    public static void setup(final Plugin p) {
        Config.cfile = new File(p.getDataFolder(), "config.yml");
        if (!Config.cfile.exists()) {
            Config.cfile.getParentFile().mkdirs();
            p.saveResource("config.yml", false);
        }
        Config.config = (FileConfiguration)new YamlConfiguration();
        try {
            Config.config.load(Config.cfile);
            Config.config = (FileConfiguration)YamlConfiguration.loadConfiguration(Config.cfile);
            Config.config.options().copyDefaults(true);
            p.saveDefaultConfig();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void copy(final InputStream in, final File file) {
        try {
            final OutputStream out = new FileOutputStream(file);
            final byte[] buf = new byte[63];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            out.close();
            in.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void saveConfig() {
        try {
            Config.config.save(Config.cfile);
        }
        catch (IOException ex) {}
    }
    
    public static void reloadConfigFile() {
        if (Config.config == null) {
            Config.cfile = new File(Config.p.getDataFolder(), "config.yml");
        }
        Config.config = (FileConfiguration)YamlConfiguration.loadConfiguration(Config.cfile);
        try {
            final InputStream defConfigStream = Config.p.getResource("config.yml");
            if (defConfigStream != null) {
                @SuppressWarnings("deprecation")
				final YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
                Config.config.setDefaults((Configuration)defConfig);
            }
        }
        catch (NullPointerException ex) {}
    }
    
    public PluginDescriptionFile getDesc() {
        return Config.p.getDescription();
    }
    
    static {
        Config.instance = new Config();
    }
}
