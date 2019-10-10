package me.mralecroyt.Eventos.ServerOptions;

import org.bukkit.event.player.*;
import me.mralecroyt.administrador.*;
import org.bukkit.configuration.file.*;
import org.bukkit.event.*;

public class AuthFullJoin implements Listener
{
    @EventHandler
    public void onLogin(final PlayerLoginEvent e) {
        final FileConfiguration c = ConfigAdmin.getConfigConfig();
        if (e.getResult().equals(PlayerLoginEvent.Result.KICK_FULL) && e.getPlayer().hasPermission("join.ranks")) {
            e.allow();
        }
    }
}
