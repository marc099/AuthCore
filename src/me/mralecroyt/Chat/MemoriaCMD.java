package me.mralecroyt.Chat;

import me.mralecroyt.administrador.ConfigAdmin;
import org.bukkit.command.*;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;

public class MemoriaCMD implements CommandExecutor {
    final FileConfiguration c = ConfigAdmin.getConfigConfig();
    public boolean onCommand(final CommandSender cs, final Command cmd, final String label, final String[] args) {
        if (cmd.getLabel().equalsIgnoreCase("memoria")) {
            if (cs.hasPermission(c.getString("Memoria"))) {
                final Runtime runtime = Runtime.getRuntime();
                final long total = runtime.totalMemory();
                final long free = runtime.freeMemory();
                final long used = total - free;
                cs.sendMessage("§aMemoria total: " + ChatColor.WHITE + total / 1048576L + " MB");
                cs.sendMessage("§aMemoria en uso: " + ChatColor.WHITE+ used / 1048576L + " MB" + ChatColor.GREEN + " (" + ChatColor.AQUA + (int) (used / total * 100.0) + " %" + ChatColor.GREEN + ")");
                cs.sendMessage("§aMemoria libre: " + ChatColor.WHITE + free / 1048576L + " MB" + ChatColor.GREEN + " (" + ChatColor.AQUA + (int) (free / total * 100.0) + " %" + ChatColor.GREEN + ")");
            } else {
                cs.sendMessage("");
            }
        }
        return false;
    }
}