package me.mralecroyt.Eventos.ServerOptions;

import org.bukkit.event.player.*;
import me.mralecroyt.administrador.*;
import org.bukkit.*;
import org.bukkit.configuration.file.*;
import org.bukkit.entity.*;
import java.util.*;
import org.bukkit.event.*;

public class Joinmessage implements Listener
{
    @EventHandler
    public void onjoin(final PlayerJoinEvent e) {
        final FileConfiguration c = ConfigAdmin.getConfigConfig();
        final Player p = e.getPlayer();
        for (final String string : c.getStringList("JoinMessage")) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', string).replace("%player%", p.getName()));
        }
    }
}
