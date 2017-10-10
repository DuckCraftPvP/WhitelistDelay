package com.ghstudios.listener;

import org.bukkit.event.player.*;
import com.ghstudios.*;
import com.ghstudios.core.*;
import com.ghstudios.util.*;
import java.util.*;
import org.bukkit.event.*;

public class PlayerEvents implements Listener
{
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerPreJoin(final AsyncPlayerPreLoginEvent event) {
        final long currentTime = System.currentTimeMillis() / 1000L;
        final UUID playerUUID = event.getUniqueId();
        if (currentTime <= Main.waitTime && !Main.whitelistUUID.contains(playerUUID)) {
            final int timeLeft = (int)(Main.waitTime - currentTime);
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_WHITELIST, TextUtil.colorize(Config.config.getString("WhitelistMessage").replaceAll("%time%", String.valueOf(timeLeft))));
        }
    }
}
