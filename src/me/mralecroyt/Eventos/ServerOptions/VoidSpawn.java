package me.mralecroyt.Eventos.ServerOptions;


import org.bukkit.event.entity.*;
import me.mralecroyt.authcore.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;

public class VoidSpawn implements Listener
{
    @EventHandler
    public void onDamage(final EntityDamageEvent e) {
        if (CoreMain.getInstance().getConfig().getBoolean("VoidSpawn") && e.getEntity() instanceof Player && e.getEntity().getType().equals((Object)EntityType.PLAYER)) {
            if (e.getCause().equals((Object)EntityDamageEvent.DamageCause.VOID)) {
                final Player p = (Player)e.getEntity();
                WorldSpawn.teleportJoinSpawn(p);
                e.setDamage(0.0);
                e.setCancelled(true);
            }
            else {
                e.setCancelled(true);
            }
        }
    }
}
