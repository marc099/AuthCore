package me.mralecroyt.Eventos.ServerOptions;


import me.mralecroyt.authcore.*;
import org.bukkit.plugin.*;
import org.bukkit.configuration.file.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import org.bukkit.command.*;
import java.util.logging.*;
import java.io.*;
import org.bukkit.event.player.*;
import org.bukkit.event.*;

public class WorldSpawn implements CommandExecutor, Listener
{
    private final CoreMain plugin;
    public static FileConfiguration storage;
    public static File storageFile;

    public WorldSpawn(final CoreMain plugin) {
        this.plugin = plugin;
    }

    public static void SpawnManager(final Plugin plugin) {
        WorldSpawn.storageFile = new File(plugin.getDataFolder(), "spawn.yml");
        if (!WorldSpawn.storageFile.exists()) {
            plugin.saveResource("spawn.yml", false);
            CoreMain.getInstance().getLogger().info("Generando spawn.yml");
        }
        else {
            CoreMain.getInstance().getLogger().info("Cargando spawn.yml");
        }
        WorldSpawn.storage = (FileConfiguration)YamlConfiguration.loadConfiguration(WorldSpawn.storageFile);
    }

    public static void teleportJoinSpawn(final Player p) {
        final World w = Bukkit.getServer().getWorld(WorldSpawn.storage.getString("Spawn.world"));
        final float yaw = (float)WorldSpawn.storage.getDouble("Spawn.yaw");
        final float pitch = (float)WorldSpawn.storage.getDouble("Spawn.pitch");
        final double x = WorldSpawn.storage.getDouble("Spawn.x");
        final double y = WorldSpawn.storage.getDouble("Spawn.y");
        final double z = WorldSpawn.storage.getDouble("Spawn.z");
        final Location l = new Location(w, x, y, z);
        l.setYaw(yaw);
        p.getLocation().setPitch(pitch);
        p.teleport(l);
    }

    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (cmd.getName().equalsIgnoreCase("setspawn")) {
            final Player p = (Player)sender;
            if (sender.hasPermission("lc.setspawn")) {
                WorldSpawn.storage.set("Spawn.pitch", (Object)(double)p.getLocation().getPitch());
                WorldSpawn.storage.set("Spawn.yaw", (Object)(double)p.getLocation().getYaw());
                WorldSpawn.storage.set("Spawn.x", (Object)p.getLocation().getX());
                WorldSpawn.storage.set("Spawn.y", (Object)p.getLocation().getY());
                WorldSpawn.storage.set("Spawn.z", (Object)p.getLocation().getZ());
                WorldSpawn.storage.set("Spawn.world", (Object)p.getWorld().getName());
                try {
                    WorldSpawn.storage.save(WorldSpawn.storageFile);
                }
                catch (IOException ex) {
                    Logger.getLogger(WorldSpawn.class.getName()).log(Level.SEVERE, null, ex);
                }
                p.sendMessage(WorldSpawn.storage.getString("SetSpawnMsj").replace("&", "§"));
                return true;
            }
            return true;
        }
        else {
            if (cmd.getName().equalsIgnoreCase("spawn")) {
                final Player p = (Player)sender;
                final float yaw = (float)WorldSpawn.storage.getDouble("Spawn.yaw");
                final float pitch = (float)WorldSpawn.storage.getDouble("Spawn.pitch");
                final double x = WorldSpawn.storage.getDouble("Spawn.x");
                final double y = WorldSpawn.storage.getDouble("Spawn.y");
                final double z = WorldSpawn.storage.getDouble("Spawn.z");
                final String mundo = WorldSpawn.storage.getString("Spawn.world");
                final World w = Bukkit.getServer().getWorld(WorldSpawn.storage.getString("Spawn.world"));
                final Location l = new Location(w, x, y, z);
                l.setYaw(yaw);
                p.getLocation().setPitch(pitch);
                p.teleport(l);
                p.sendMessage(WorldSpawn.storage.getString("EntrySpawn").replace("&", "§"));
                return true;
            }
            return false;
        }
    }

    @EventHandler
    public void JoinSpawn(final PlayerJoinEvent e) {
        final Player p = e.getPlayer();
        if (WorldSpawn.storage.getDouble("Spawn.yaw") == 0.0 && WorldSpawn.storage.getDouble("Spawn.pitch") == 0.0 && WorldSpawn.storage.getDouble("Spawn.x") == 0.0 && WorldSpawn.storage.getDouble("Spawn.y") == 0.0 && WorldSpawn.storage.getDouble("Spawn.z") == 0.0 && WorldSpawn.storage.getString("Spawn.world") == null) {
            CoreMain.getInstance().getLogger().info("Spawn is not set!");
            return;
        }
        if (WorldSpawn.storage.getBoolean("JoinSpawn")) {
            teleportJoinSpawn(p);
        }
    }

    static {
        WorldSpawn.storage = null;
        WorldSpawn.storageFile = null;
    }
}
