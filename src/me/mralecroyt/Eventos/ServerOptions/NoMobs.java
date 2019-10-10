package me.mralecroyt.Eventos.ServerOptions;

import org.bukkit.event.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.player.*;
import org.bukkit.entity.*;
import java.util.*;

public class NoMobs implements Listener
{
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onRemoveMobs(final CreatureSpawnEvent e) {
        if (e.getSpawnReason() == CreatureSpawnEvent.SpawnReason.CUSTOM) {
            return;
        }
        if (e.getEntity().getType() != EntityType.PLAYER) {
            e.getEntity().remove();
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onRemoveMobsByWorld(final PlayerChangedWorldEvent e) {
        if (e.getPlayer().getWorld() == e.getFrom()) {
            return;
        }
        for (final Entity ent : e.getPlayer().getWorld().getEntities()) {
            if (ent.getType() == EntityType.PLAYER || ent.getType() == EntityType.PAINTING || ent.getType() == EntityType.ITEM_FRAME || ent.getType() == EntityType.ARMOR_STAND) {
                return;
            }
            ent.remove();
        }
    }
}
