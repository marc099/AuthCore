package me.mralecroyt.gcaptcha;

import fr.xephi.authme.api.v3.AuthMeApi;
import me.mralecroyt.authcore.*;
import net.md_5.bungee.api.chat.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class ManagerTask extends BukkitRunnable implements Listener
{

    public void run() {
        for (final Player p : Bukkit.getOnlinePlayers()) {
            if (AuthMeApi.getInstance().isAuthenticated(p) && CoreMain.lock.contains(p)) {
                Send(p);
            }
        }
    }

    public static void Send(final Player p) {
        final TextComponent text1 = new TextComponent(Color("&a&lCLICK AQUI PARA COPIAR EL CODIGO."));
        text1.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§a/code " + CoreMain.captcha.get(p)).create()));
        text1.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/code " + CoreMain.captcha.get(p)));
        p.sendMessage("   ");
        p.sendMessage("   ");
        p.sendMessage(Color("&f&lPor favor resuelve el &a&lCAPTCHA &f&lantes de entrar al"));
        p.sendMessage(Color("&f&lservidor, ejecuta el siguiente &acomando con el codigo&f:"));
        p.sendMessage("   ");
        p.spigot().sendMessage(new BaseComponent[] { text1 });
        p.sendMessage("   ");
        p.sendMessage(Color("&c&lNo tendras que ejecutar esto de nuevo."));
        p.sendMessage("   ");
        p.sendMessage("   ");
    }

    public static String Color(final String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}
