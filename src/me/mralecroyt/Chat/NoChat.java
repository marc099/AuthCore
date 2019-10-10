package me.mralecroyt.Chat;

import org.bukkit.event.player.*;
import org.bukkit.event.*;

public class NoChat implements Listener
{
    @EventHandler
    public void onChat(final AsyncPlayerChatEvent e) {
        if (!e.isCancelled()) {
            e.setCancelled(true);
        }
    }
}
