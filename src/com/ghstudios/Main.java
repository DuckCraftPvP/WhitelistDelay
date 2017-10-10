package com.ghstudios;

import org.bukkit.plugin.java.*;
import org.bukkit.plugin.*;
import com.ghstudios.core.*;
import org.bukkit.event.*;
import com.ghstudios.listener.*;
import com.ghstudios.commands.*;
import org.bukkit.command.*;
import org.bukkit.*;
import java.util.*;

public class Main extends JavaPlugin
{
    public static Plugin plugin;
    public static long waitTime;
    public static List<UUID> whitelistUUID;
    
    public void onDisable() {
        Main.plugin = null;
    }
    
    public void onEnable() {
        Config.setup(Main.plugin = (Plugin)this);
        final int wait = Config.config.getInt("WaitTime");
        Main.waitTime = System.currentTimeMillis() / 1000L + wait;
        registerEvents((Plugin)this, new PlayerEvents());
        this.loadCommands();
        final List<String> uuid = (List<String>)Config.config.getStringList("WhitelistedUUID");
        for (final String string : uuid) {
            Main.whitelistUUID.add(UUID.fromString(string));
        }
    }
    
    private void loadCommands() {
        this.getCommand("wd").setExecutor((CommandExecutor)new MainCommands());
    }
    
    private static void registerEvents(final Plugin plugin, final Listener... listeners) {
        for (final Listener listener : listeners) {
            Bukkit.getServer().getPluginManager().registerEvents(listener, plugin);
        }
    }
    
    static {
        Main.whitelistUUID = new ArrayList<UUID>();
    }
}
