package me.mralecroyt.gcaptcha;

import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;
import fr.xephi.authme.events.*;
import org.bukkit.potion.*;
import org.bukkit.scheduler.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import org.bukkit.event.*;
import java.util.logging.*;
import java.io.*;
import me.mralecroyt.authcore.*;

public class OthersEvents implements Listener
{

        private final CoreMain plugin;
        private static OthersEvents ins;

        public OthersEvents(final CoreMain plugin) {
            this.plugin = plugin;
        }


    public static OthersEvents getClase() {
        return OthersEvents.ins;
    }

    public static ItemStack captcha() {
        final ItemStack is11 = new ItemStack(Material.EMPTY_MAP, 1);
        final ItemMeta meta11 = is11.getItemMeta();
        meta11.setDisplayName("§c§lCAPTCHA §f(Click derecho)");
        is11.setItemMeta(meta11);
        return is11;
    }

    @EventHandler
    public void onJoin(LoginEvent e) {
        final Player p = e.getPlayer();
        if (CoreMain.storage.isSet("Nicks." + p.getName())) {
            this.sendToServer(p, CoreMain.getInstance().getConfig().getString("server"));
            return;
        }
        p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 10000000, 10));
        p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 1000000, 10));
        CoreMain.lock.add(p);
        if (!CoreMain.vls.containsKey(p.getName())) {
            CoreMain.vls.put(p.getName(), 0);
        }
        new BukkitRunnable() {
            int timer = 40;

            public void run() {
                if (!p.isOnline()) {
                    this.cancel();
                    return;
                }
                if (this.timer != 0) {
                    --this.timer;
                    return;
                }
                this.cancel();
                CoreMain.captcha.remove(p);
                CoreMain.lock.remove(p);
                for (final PotionEffect pe : p.getActivePotionEffects()) {
                    p.removePotionEffect(pe.getType());
                }
                if (CoreMain.vls.getOrDefault(p.getName(), 32) == 3) {
                    OthersEvents.kickPlayer(p, CoreMain.vls.get(p.getName()));
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "bungee ab blacklist add " + p.getAddress().getHostName());
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "bungee ab blacklist add " + p.getAddress().getHostName());
                    return;
                }
                if (CoreMain.vls.containsKey(p.getName())) {
                    CoreMain.vls.put(p.getName(), CoreMain.vls.get(p.getName()) + 1);
                }
                p.kickPlayer(EventManager.Color("&cEl tiempo del captcha se ha explirado: " + CoreMain.vls.getOrDefault(p.getName(), 32) + "/3"));
            }
        }.runTaskTimerAsynchronously(this.plugin, 0L, 20L);
    }

    public void sendToServer(final Player player, final String serverName) {
        final ByteArrayOutputStream b = new ByteArrayOutputStream();
        final DataOutputStream out = new DataOutputStream(b);
        try {
            out.writeUTF("Connect");
            out.writeUTF(serverName);
        }
        catch (IOException ex) {
            Logger.getLogger(CoreMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        player.sendPluginMessage(CoreMain.getInstance(), "BungeeCord", b.toByteArray());
    }

    public static void banPlayer(final Player p) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ban " + p.getName() + "Captcha Invalido: 3/3, Si esto es un error pide ayuda en nuestro discord: &adiscord.groyland.net");
    }

    public static void kickPlayer(final Player p, final Integer values) {
        p.kickPlayer(EventManager.Color("&cCaptcha Invalido: " + values + "/3"));
    }
}
