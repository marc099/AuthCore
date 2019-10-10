package me.mralecroyt.Eventos.ServerOptions;

import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.*;
import org.bukkit.plugin.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.*;
import org.bukkit.command.*;
import me.mralecroyt.authcore.CoreMain;

public class TimeKick implements Listener {


    @EventHandler
    public void joinTime(PlayerJoinEvent e) {
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(get(), new Runnable() {

            @Override
            public void run() {
                Player player = e.getPlayer();
                player.kickPlayer("§9§lLOGIN §8» §7¡El tiempo se ha excedido!");
            }
        }, 2400L);
    }

    public static CoreMain get() {
        return CoreMain.getInstance();
    }
}