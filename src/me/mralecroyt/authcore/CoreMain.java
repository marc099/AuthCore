package me.mralecroyt.authcore;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.PlaceholderAPIPlugin;
import me.mralecroyt.Chat.MemoriaCMD;
import me.mralecroyt.Chat.NoChat;
import me.mralecroyt.Eventos.ScoreBoard.BoardTask;
import me.mralecroyt.Eventos.ServerOptions.*;
import me.mralecroyt.administrador.ConfigAdmin;
import me.mralecroyt.gcaptcha.CommandCode;
import me.mralecroyt.gcaptcha.EventManager;
import me.mralecroyt.gcaptcha.ManagerTask;
import me.mralecroyt.gcaptcha.OthersEvents;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;



public class CoreMain extends JavaPlugin implements Listener {
    public static CoreMain instance;
    public EventManager em;
    public static String PREFIX;
    PluginManager pm;
    public static Permission perms;
    public Chat chat;
    public Economy econ;
    private ConfigAdmin cm;
    PluginDescriptionFile pdffile;
    public String version;
    public String nombre;
    public final Boolean papi;
    private final Boolean mvdw;
    public static List<Player> lock;
    public static HashMap<Player, String> captcha;
    public static HashMap<String, Integer> vls;
    public static FileConfiguration storage;
    public static File storageFile;


    public void onEnable() {
        final ConsoleCommandSender cs = this.getServer().getConsoleSender();
        cs.sendMessage("#######################################");
        cs.sendMessage("[AuthCore] Cargando configs....");
        this.loadConfigs(this);
        this.loadComandos();
        cs.sendMessage("Cargando Eventos...");
        this.loadEventos();
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        this.getServer().getPluginManager().registerEvents(new WorldSpawn(this), this);
        cs.sendMessage("Cargando plugin....");
        this.em.init();
        this.deletefiles2();
        final ManagerTask mt = new ManagerTask();
        mt.runTaskTimerAsynchronously(this, 60L, 60L);
        WorldSpawn.SpawnManager(this);
        this.getServer().getPluginManager().registerEvents(new OthersEvents(this), this);
        this.getCommand("setspawn").setExecutor(new WorldSpawn(this));
        BoardTask.load(this);
        new BukkitRunnable() {
            public void run() {
                for (final Player p : Bukkit.getOnlinePlayers()) {
                    BoardTask.contentBoard(p);
                }
            }
        }.runTaskTimerAsynchronously(this, 0L, (long)(20 * BoardTask.storage.getInt("Time-Update")));
        for (final World world : Bukkit.getWorlds()) {
            world.setStorm(false);
            world.setThundering(false);
            this.getLogger().log(Level.INFO, "Version: {0} Activated", getInstance().getDescription().getVersion());
            if (Bukkit.getPluginManager().isPluginEnabled("Vault")) {
                Bukkit.getServer().getLogger().log(Level.INFO, "Vault Hooked version: {0}", Bukkit.getPluginManager().getPlugin("Vault").getDescription().getVersion());
            }
            else {
                Bukkit.getServer().getLogger().log(Level.INFO, "This Plugin requires Vault");
                Bukkit.getServer().getLogger().log(Level.INFO, "Vault Download: https://dev.bukkit.org/projects/vault");
            }
            if (this.papi) {
                if (Bukkit.getPluginManager().isPluginEnabled("PlaceHolderAPI")) {
                    Bukkit.getServer().getLogger().log(Level.INFO, "Hooked PlaceholderAPI version: {0}", PlaceholderAPIPlugin.getInstance().getDescription().getVersion());
                }
                else {
                    Bukkit.getServer().getLogger().log(Level.INFO, "PlaceholderAPI Download: https://www.spigotmc.org/resources/placeholderapi.6245/");
                }
            }
            else {
                Bukkit.getServer().getLogger().log(Level.INFO, "PlaceholderAPI desactived in config.yml");
            }
            if (this.mvdw) {
                if (Bukkit.getPluginManager().isPluginEnabled("MVdWPlaceholderAPI")) {
                    Bukkit.getServer().getLogger().log(Level.INFO, "Hooked MVdWPlaceholderAPI version {0}", Bukkit.getPluginManager().getPlugin("MVdWPlaceholderAPI").getDescription().getVersion());
                }
            }
            else {
                Bukkit.getServer().getLogger().log(Level.INFO, "MVdWPlaceholderAPI desactived in config.yml");
            }
            this.getConfig().options().copyDefaults(true);
            this.saveDefaultConfig();
            this.reloadConfig();
        }
        cs.sendMessage("Completado...." + this.getDescription().getVersion());
        cs.sendMessage("[AuthCore] Plugin by MrAlecroYT");
        cs.sendMessage("#######################################");
    }



    public static String setPlaceholders(Player p, String s) {
        s = PlaceholderAPI.setPlaceholders(p, s);
        s = s.replace("%player%", p.getName());
        s = s.replace("%player-displayname%", p.getDisplayName());
        return s;
    }

    private void deleteFiles(final File directory) {
        if (directory.exists()) {
            final File[] files = directory.listFiles();
            if (files != null) {
                for (final File file : files) {
                    file.delete();
                }
            }
        }
    }

    private void deleteFile(final File file) {
        if (file.exists()) {
            file.delete();
        }
    }

    public void deletefiles2() {
        this.deleteFile(new File("AuthLobby/uid.dat"));
        this.deleteFile(new File("AuthLobby/level.dat_old"));
        this.deleteFile(new File("AuthLobby/data/scoreboard.dat"));
    }

    private void loadComandos() {
        this.getCommand("memoria").setExecutor(new MemoriaCMD());
        this.getCommand("code").setExecutor(new CommandCode());
    }


    public void onDisable() {
        for (final Player pl : Bukkit.getOnlinePlayers()) {
            }
        }

    private void loadEventos() {
        final PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvents(new Worldguard(), this);
        pm.registerEvents(new Clima(), this);
        pm.registerEvents(new NoMobs(), this);
        pm.registerEvents(new AuthFullJoin(), this);
        pm.registerEvents(new Utils(), this);
        pm.registerEvents(new NoChat(), this);
        pm.registerEvents(new Joinmessage(), this);
        pm.registerEvents(new VoidSpawn(), this);
        pm.registerEvents(new TimeKick(), this);
    }

    public static CoreMain getInstance() {
        return CoreMain.instance;
    }

    private void loadConfigs(final Plugin plugin) {
        (this.cm = new ConfigAdmin()).createConfigConfig();
        CoreMain.storageFile = new File(plugin.getDataFolder(), "data.yml");
        if (!CoreMain.storageFile.exists()) {
            plugin.saveResource("data.yml", true);
            Bukkit.getConsoleSender().sendMessage(EventManager.Color("Generando data.yml"));
        }
        else {
            Bukkit.getConsoleSender().sendMessage(EventManager.Color("Cargando data.yml"));
        }
        CoreMain.storage = YamlConfiguration.loadConfiguration(CoreMain.storageFile);
    }
    public static CoreMain get() {
        return getInstance();
    }
    public CoreMain() {

        CoreMain.instance = this;
        this.em = new EventManager(this);
        this.papi = this.getConfig().getBoolean("PlaceholderAPI");
        this.mvdw = this.getConfig().getBoolean("MVdWPlaceholderAPI");
        this.chat = null;
        this.econ = null;
    }



    static {
        CoreMain.PREFIX = "";
        CoreMain.lock = new ArrayList<Player>();
        CoreMain.captcha = new HashMap<Player, String>();
        CoreMain.vls = new HashMap<String, Integer>();
        CoreMain.storage = null;
        CoreMain.storageFile = null;
    }



    private String fixColors(final String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static String chatColors(final String message) {
        return ChatColor.translateAlternateColorCodes('&', message.replace("\\\\n", "\n").replace("\\n", "\n").replace("&nl", "\n"));
    }
}
