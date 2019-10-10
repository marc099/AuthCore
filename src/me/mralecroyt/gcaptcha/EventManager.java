package me.mralecroyt.gcaptcha;

import fr.xephi.authme.api.v3.AuthMeApi;
import me.mralecroyt.authcore.CoreMain;
import org.bukkit.plugin.*;
import org.bukkit.event.*;
import org.bukkit.entity.*;
import org.bukkit.event.entity.*;
import org.bukkit.event.block.*;
import org.bukkit.event.player.*;
import org.bukkit.*;

public class EventManager implements Listener
{
    private final CoreMain plugin;

    public EventManager(final CoreMain plugin) {
        this.plugin = plugin;
    }

    public void init() {
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
    }

    @EventHandler(ignoreCancelled = true)
    public void onItemSpawn(final ItemSpawnEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onChat(final AsyncPlayerChatEvent e) {
        final Player p = e.getPlayer();
        e.setCancelled(true);
    }

    @EventHandler
    public void onDrop(final PlayerDropItemEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onEntity(final EntityDamageEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onDamage(final EntityDamageByEntityEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onBreak(final BlockBreakEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onPlace(final BlockPlaceEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onJoinEvent(PlayerJoinEvent e) {
        final Player p = e.getPlayer();
        p.getInventory().clear();
        CoreMain.captcha.put(p, GetCaptcha.getSaltString());
    }

    @EventHandler
    public void onInterac(final PlayerInteractEvent e) {
        final Player p = e.getPlayer();
        e.setCancelled(true);
    }

    @EventHandler
    public void onJoinEv(PlayerJoinEvent e) {
        e.setJoinMessage(null);
        this.plugin.getServer().getOnlinePlayers().stream().map(players -> {
            players.hidePlayer(e.getPlayer());
            return players;
        }).forEach(players -> e.getPlayer().hidePlayer(players));
    }

    @EventHandler
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent e) {
        final Player p = e.getPlayer();
        if (AuthMeApi.getInstance().isAuthenticated(p) && CoreMain.lock.contains(p)) {
            final String cmd = e.getMessage();
            if (!cmd.toLowerCase().startsWith("/code")) {
                e.setCancelled(true);
            }
        }
    }

    public static String Color(final String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}
