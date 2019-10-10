package me.mralecroyt.Listener;

import org.bukkit.entity.*;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.*;

public class PlaceHoldersApi
{
    public static String placeholder(final String message, final Player p) {
        final String msg = message.replaceAll("%online-players%", String.valueOf(Bukkit.getServer().getOnlinePlayers().size())).replaceAll("%max-players%", String.valueOf(Bukkit.getServer().getMaxPlayers())).replaceAll("&", "§").replaceAll("%player-world%", p.getWorld().getName()).replaceAll("%player-x%", String.valueOf(p.getLocation().getBlockX())).replaceAll("%player-y%", String.valueOf(p.getLocation().getBlockY())).replaceAll("%player-z%", String.valueOf(p.getLocation().getBlockZ())).replaceAll("%ram-max%", String.valueOf(Runtime.getRuntime().maxMemory() / 1048576L)).replaceAll("%ram-used%", String.valueOf(Runtime.getRuntime().maxMemory() - Runtime.getRuntime().freeMemory() / 1048576L)).replaceAll("%ram-free%", String.valueOf(Runtime.getRuntime().freeMemory() / 1048576L)).replaceAll("%online-world%", String.valueOf(p.getWorld().getPlayers().size())).replaceAll("%player-name%", p.getName()).replaceAll("%player-displayname%", p.getDisplayName()).replaceAll("%player-ping%", String.valueOf(((CraftPlayer)p).getHandle().ping));
        return msg;
    }
}

