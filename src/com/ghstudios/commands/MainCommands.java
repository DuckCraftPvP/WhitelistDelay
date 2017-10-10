package com.ghstudios.commands;

import org.bukkit.command.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import com.ghstudios.core.Config;
import java.util.*;
import com.ghstudios.util.*;

public class MainCommands implements CommandExecutor
{
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (cmd.getName().equalsIgnoreCase("wd")) {
            if (sender.hasPermission("whitelist.commands")) {
                if (args.length == 0 || args.length > 2) {
                    this.helpMessage(sender);
                }
                else if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("add")) {
                        this.sendMessage(sender, "&7Usage: &f/wd add <online player>");
                    }
                    else if (args[0].equalsIgnoreCase("remove")) {
                        this.sendMessage(sender, "&7Usage: &f/wd remove <online player>");
                    }
                    else if (args[0].equalsIgnoreCase("time")) {
                        this.sendMessage(sender, "&7Usage: &f/wd time <integer>");
                    }
                    else {
                        this.helpMessage(sender);
                    }
                }
                else if (args.length == 2) {
                    if (args[0].equalsIgnoreCase("add") || args[0].equalsIgnoreCase("remove")) {
                        if (sender.getServer().getPlayer(args[1]) == null) {
                            this.sendMessage(sender, "&cPlayer not found.");
                            return true;
                        }
                        final Player targetPlayer = Bukkit.getServer().getPlayer(args[1]);
                        final List<String> uuid = (List<String>)Config.config.getStringList("WhitelistedUUID");
                        if (args[0].equalsIgnoreCase("add")) {
                            uuid.add(targetPlayer.getUniqueId().toString());
                            Config.config.set("WhitelistedUUID", (Object)uuid);
                            Config.saveConfig();
                            this.sendMessage(sender, "&aAdded " + targetPlayer.getName() + ": " + targetPlayer.getUniqueId().toString());
                        }
                        else {
                            uuid.remove(targetPlayer.getUniqueId().toString());
                            Config.config.set("WhitelistedUUID", (Object)uuid);
                            Config.saveConfig();
                            this.sendMessage(sender, "&cRemoved " + targetPlayer.getName() + ": " + targetPlayer.getUniqueId().toString());
                        }
                        return true;
                    }
                    else {
                        if (args[0].equalsIgnoreCase("time")) {
                            Config.config.set("WaitTime", (Object)Integer.valueOf(args[1]));
                            Config.saveConfig();
                            this.sendMessage(sender, "&aSet time to: " + args[1]);
                            return true;
                        }
                        this.helpMessage(sender);
                    }
                }
            }
            else {
                final String permissionMessage = Config.config.getString("PermissionDenied");
                this.sendMessage(sender, permissionMessage);
            }
            return true;
        }
        return false;
    }
    
    private void helpMessage(final CommandSender sender) {
        this.sendMessage(sender, "&f/wd &7- Shows help menu");
        this.sendMessage(sender, "&f/wd add <player> &7- Adds player UUID to bypass whitelist");
        this.sendMessage(sender, "&f/wd remove <player> &7- Removes player UUID to bypass whitelist");
        this.sendMessage(sender, "&f/wd time <integer> &7- Sets the length of whitelist (seconds)");
    }
    
    private void sendMessage(final CommandSender sender, final String message) {
        sender.sendMessage(TextUtil.colorize(message));
    }
}
