package me.mralecroyt.Eventos.ScoreBoard;

import me.mralecroyt.authcore.CoreMain;
import org.bukkit.scheduler.*;
import org.bukkit.entity.*;
import java.io.*;
import org.bukkit.plugin.*;
import org.bukkit.configuration.file.*;
import be.maximvdw.placeholderapi.*;
import org.bukkit.*;
import java.util.*;
import org.bukkit.scoreboard.*;
import org.bukkit.event.*;
import org.bukkit.event.player.*;

public class BoardTask
{
    public final HashMap<Player, ScoreManager> Players;
    public static BoardTask getboard;
    public static FileConfiguration storage;
    public static File storageFile;

    public BoardTask() {
        this.Players = new HashMap<Player, ScoreManager>();
    }

    public static void load (final Plugin plugin) {
        BoardTask.storageFile = new File(plugin.getDataFolder(), "scoreboard.yml");
        if (!BoardTask.storageFile.exists()) {
            plugin.saveResource("scoreboard.yml", true);
            Bukkit.getConsoleSender().sendMessage(Color("Generando scoreboard.yml"));
        }
        else {
            Bukkit.getConsoleSender().sendMessage(Color("Cargando scoreboard.yml"));
        }
        BoardTask.storage = YamlConfiguration.loadConfiguration(BoardTask.storageFile);
    }

    public static void contentBoard(final Player p) {
        if (null == p) {
            return;
        }
        final HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
        final String translateAlternateColorCodes2 = ChatColor.translateAlternateColorCodes('&', BoardTask.storage.getString("Title"));
        int size = BoardTask.storage.getStringList("scoreboard-lines").size();
        for (String replaceText : BoardTask.storage.getStringList("scoreboard-lines")) {
            replaceText = replaceText.replace("%player%", p.getName());
            replaceText = replaceText.replace("%player-displayname%", p.getDisplayName());
            final String World = p.getWorld().getName();
            replaceText = replaceText.replaceAll("%world%", World);
            if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
                replaceText = CoreMain.setPlaceholders(p, replaceText);
            }
            replaceText = replaceText.replaceAll("%empty%", " ");
            replaceText = Color(replaceText);
            if (Bukkit.getPluginManager().isPluginEnabled("MVdWPlaceholderAPI")) {
                replaceText = PlaceholderAPI.replacePlaceholders(p, replaceText);
            }
            hashMap.put(fixDuplicates(hashMap, ChatColor.translateAlternateColorCodes('&', replaceText)), size);
            --size;
        }
        ScoreManager.scoredSidebar(p, translateAlternateColorCodes2, hashMap);
    }


    @EventHandler
    public void onJoin(final PlayerJoinEvent e) {
        final Player p = e.getPlayer();
        contentBoard(p);
    }

    private static String fixDuplicates(final HashMap<String, Integer> hashMap, String s) {
        while (hashMap.containsKey(s)) {
            s += "§r";
        }
        if (s.length() > 40) {
            s = s.substring(0, 39);
        }
        return s;
    }

    public static String Color(final String a) {
        return ChatColor.translateAlternateColorCodes('&', a);
    }

    static {
        BoardTask.storage = null;
        BoardTask.storageFile = null;
    }
}
