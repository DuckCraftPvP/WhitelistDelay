package com.ghstudios.util;

import org.bukkit.*;

public class TextUtil
{
    public static String colorize(final String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
