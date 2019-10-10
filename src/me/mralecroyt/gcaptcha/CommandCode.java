package me.mralecroyt.gcaptcha;

import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.potion.*;
import java.util.logging.*;
import org.bukkit.*;
import me.mralecroyt.authcore.*;
import java.io.*;

public class CommandCode implements CommandExecutor
{
    public boolean onCommand(final CommandSender cs, final Command cmnd, final String string, final String[] strings) {
        if (!cmnd.getName().equalsIgnoreCase("code")) {
            return false;
        }
        if (!(cs instanceof Player)) {
            return true;
        }
        final Player p = (Player)cs;
        if (strings.length != 1) {
            p.sendMessage(EventManager.Color("&fUsa el siguiente formato: &a&l/code (codigo)"));
            return true;
        }
        if (strings[0].equals(CoreMain.captcha.get(p))) {
            CoreMain.captcha.remove(p);
            CoreMain.lock.remove(p);
            CoreMain.vls.remove(p.getName());
            for (final PotionEffect pe : p.getActivePotionEffects()) {
                p.removePotionEffect(pe.getType());
            }
            p.sendMessage(EventManager.Color("&aHas completado el captcha correctamente."));
            CoreMain.storage.set("Nicks." + p.getName(), true);
            try {
                CoreMain.storage.save(CoreMain.storageFile);
            }
            catch (IOException ex) {
                Logger.getLogger(CommandCode.class.getName()).log(Level.SEVERE, null, ex);
            }
            p.getInventory().clear();
            this.sendToServer(p, CoreMain.getInstance().getConfig().getString("server"));
            return true;
        }
        CoreMain.captcha.remove(p);
        CoreMain.lock.remove(p);
        for (final PotionEffect pe : p.getActivePotionEffects()) {
            p.removePotionEffect(pe.getType());
        }
        if (CoreMain.vls.getOrDefault(p.getName(), 32) == 3) {
            OthersEvents.kickPlayer(p, CoreMain.vls.get(p.getName()));
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "bungee ab blacklist add " + p.getAddress().getHostName());
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "bungee ab blacklist add " + p.getAddress().getHostName());
            return true;
        }
        CoreMain.vls.put(p.getName(), CoreMain.vls.get(p.getName()) + 1);
        OthersEvents.kickPlayer(p, CoreMain.vls.getOrDefault(p.getName(), 32));
        p.getInventory().clear();
        return true;
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
}
