package me.mralecroyt.Eventos.ServerOptions;

import me.mralecroyt.administrador.*;
import org.bukkit.configuration.file.*;
import java.util.*;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.event.*;
import org.bukkit.entity.minecart.*;
import org.bukkit.inventory.*;
import org.bukkit.event.block.*;
import org.bukkit.event.inventory.*;
import org.bukkit.event.vehicle.*;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.*;
import org.bukkit.entity.*;

public class Worldguard implements Listener {
    @EventHandler
    public void onJoinWorldProtect(final PlayerJoinEvent e) {
        final FileConfiguration c = ConfigAdmin.getConfigConfig();
        if (c.getStringList("Mundo") != null || !c.getStringList("Mundo").isEmpty()) {
            final List<String> worldsList = (List<String>) c.getStringList("Mundo");
            if (worldsList.contains(e.getPlayer().getWorld().getName())) {
                if (!worldsList.contains(e.getPlayer().getWorld().getName())) {
                    return;
                }
                e.getPlayer().setMaxHealth((double) c.getInt("Protecciones.LobbyOptions.Max-Vida"));
                e.getPlayer().setHealth((double) c.getInt("Protecciones.LobbyOptions.Max-Vida"));
                e.getPlayer().setFoodLevel(20);
                if (!c.getBoolean("Protecciones.LobbyOptions.MobSpawn")) {
                    for (final Entity ent : e.getPlayer().getWorld().getEntities()) {
                        if (ent.getType() == EntityType.PLAYER) {
                            return;
                        }
                        ent.remove();
                    }
                }
                if (!c.getBoolean("Protecciones.LobbyOptions.Fuego")) {
                    final World w = e.getPlayer().getWorld();
                    Chunk[] loadedChunks;
                    for (int length = (loadedChunks = w.getLoadedChunks()).length, i = 0; i < length; ++i) {
                        final Chunk chunk = loadedChunks[i];
                        final int cx = chunk.getX() << 4;
                        final int cz = chunk.getZ() << 4;
                        for (int x = cx; x < cx + 16; ++x) {
                            for (int z = cz; z < cz + 16; ++z) {
                                for (int y = 0; y < 128; ++y) {
                                    final Block b = w.getBlockAt(x, y, z);
                                    if (b.getType() == Material.FIRE) {
                                        b.setType(Material.AIR);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onChangeWorldProtect(final PlayerChangedWorldEvent e) {
        final FileConfiguration c = ConfigAdmin.getConfigConfig();
        if (c.getStringList("Mundo") != null || !c.getStringList("Mundo").isEmpty()) {
            final List<String> worldsList = (List<String>) c.getStringList("Mundo");
            if (worldsList.contains(e.getPlayer().getWorld().getName())) {
                if (!worldsList.contains(e.getPlayer().getWorld().getName())) {
                    return;
                }
                e.getPlayer().setMaxHealth((double) c.getInt("Protecciones.LobbyOptions.Max-Vida"));
                e.getPlayer().setHealth((double) c.getInt("Protecciones.LobbyOptions.Max-Vida"));
                e.getPlayer().setFoodLevel(20);
                if (!c.getBoolean("Protecciones.LobbyOptions.MobSpawn")) {
                    for (final Entity ent : e.getPlayer().getWorld().getEntities()) {
                        if (ent instanceof Player) {
                            return;
                        }
                        ent.remove();
                    }
                }
                if (!c.getBoolean("Protecciones.LobbyOptions.Fuego")) {
                    final World w = e.getPlayer().getWorld();
                    Chunk[] loadedChunks;
                    for (int length = (loadedChunks = w.getLoadedChunks()).length, i = 0; i < length; ++i) {
                        final Chunk chunk = loadedChunks[i];
                        final int cx = chunk.getX() << 4;
                        final int cz = chunk.getZ() << 4;
                        for (int x = cx; x < cx + 16; ++x) {
                            for (int z = cz; z < cz + 16; ++z) {
                                for (int y = 0; y < 128; ++y) {
                                    final Block b = w.getBlockAt(x, y, z);
                                    if (b.getType() == Material.FIRE) {
                                        b.setType(Material.AIR);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onTntExplosion(final EntityExplodeEvent e) {
        final FileConfiguration c = ConfigAdmin.getConfigConfig();
        if (c.getStringList("Mundo") == null && c.getStringList("Mundo").isEmpty()) {
            return;
        }
        final List<String> worldsList = (List<String>) c.getStringList("Mundo");
        if (!worldsList.contains(e.getEntity().getWorld().getName())) {
            return;
        }
        if (!c.getBoolean("Protecciones.LobbyOptions.AntiTNT")) {
            return;
        }
        if (e.getEntity().getType() == EntityType.PRIMED_TNT) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onTntDamage(final EntityDamageByEntityEvent e) {
        final FileConfiguration c = ConfigAdmin.getConfigConfig();
        if (c.getStringList("Mundo") == null && c.getStringList("Mundo").isEmpty()) {
            return;
        }
        final List<String> worldsList = (List<String>) c.getStringList("Mundo");
        if (!worldsList.contains(e.getEntity().getWorld().getName())) {
            return;
        }
        if (!c.getBoolean("Protecciones.LobbyOptions.AntiTNT")) {
            return;
        }
        if (e.getDamager() instanceof TNTPrimed) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onFallDamage(final EntityDamageEvent e) {
        final FileConfiguration c = ConfigAdmin.getConfigConfig();
        if (c.getStringList("Mundo") == null && c.getStringList("Mundo").isEmpty()) {
            return;
        }
        final List<String> worldsList = (List<String>) c.getStringList("Mundo");
        if (!worldsList.contains(e.getEntity().getWorld().getName())) {
            return;
        }
        if (c.getBoolean("Protecciones.PlayersOptions.FallDamage")) {
            return;
        }
        if (e.getEntity() instanceof Player) {
            if (e.getCause() == EntityDamageEvent.DamageCause.FALL) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPvpDamage(final EntityDamageByEntityEvent e) {
        final FileConfiguration c = ConfigAdmin.getConfigConfig();
        if (c.getStringList("Mundo") == null && c.getStringList("Mundo").isEmpty()) {
            return;
        }
        final List<String> worldsList = (List<String>) c.getStringList("Mundo");
        if (!worldsList.contains(e.getEntity().getWorld().getName())) {
            return;
        }
        if (c.getBoolean("Protecciones.PlayersOptions.PvP")) {
            return;
        }
        if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onMobDamage(final EntityDamageByEntityEvent e) {
        final FileConfiguration c = ConfigAdmin.getConfigConfig();
        if (c.getStringList("Mundo") == null && c.getStringList("Mundo").isEmpty()) {
            return;
        }
        final List<String> worldsList = (List<String>) c.getStringList("Mundo");
        if (!worldsList.contains(e.getEntity().getWorld().getName())) {
            return;
        }
        if (c.getBoolean("Protecciones.PlayersOptions.NoMobDamage")) {
            return;
        }
        if (!(e.getDamager() instanceof Player)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onBuild(final BlockPlaceEvent e) {
        final FileConfiguration c = ConfigAdmin.getConfigConfig();
        if (c.getStringList("Mundo") == null && c.getStringList("Mundo").isEmpty()) {
            return;
        }
        if (e.getPlayer().isOp() || e.getPlayer().hasPermission("hc.prote.build")) {
            return;
        }
        final List<String> worldsList = (List<String>) c.getStringList("Mundo");
        if (!worldsList.contains(e.getPlayer().getWorld().getName())) {
            return;
        }
        if (c.getBoolean("Protecciones.PlayersOptions.Construir")) {
            return;
        }
        final Player p = e.getPlayer();
        if (e.getBlock().getType() == Material.FIRE) {
            return;
        }
        if (e.getBlock().getType() == Material.WATER) {
            return;
        }
        if (e.getBlock().getType() == Material.LAVA) {
            return;
        }
        if (e.getPlayer().getLocation().subtract(0.0, 1.0, 0.0).getBlock().getType() == Material.AIR) {
            p.teleport(p.getLocation().subtract(0.0, 0.5, 0.0));
        }
        e.setCancelled(true);
    }

    @EventHandler
    public void onBreak(final BlockBreakEvent e) {
        final FileConfiguration c = ConfigAdmin.getConfigConfig();
        if (c.getStringList("Mundo") == null && c.getStringList("Mundo").isEmpty()) {
            return;
        }
        if (e.getPlayer().isOp() || e.getPlayer().hasPermission("hc.prote.break")) {
            return;
        }
        final List<String> worldsList = (List<String>) c.getStringList("Mundo");
        if (!worldsList.contains(e.getPlayer().getWorld().getName())) {
            return;
        }
        if (!c.getBoolean("Protecciones.PlayersOptions.Destruir")) {
            final Player p = e.getPlayer();
            if (e.getPlayer().getLocation().subtract(0.0, 1.0, 0.0).getBlock().getType() == Material.AIR) {
                p.teleport(p.getLocation().subtract(0.0, 0.5, 0.0));
            }
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onItemDrop(final PlayerDropItemEvent e) {
        final FileConfiguration c = ConfigAdmin.getConfigConfig();
        if (c.getStringList("Mundo") == null && c.getStringList("Mundo").isEmpty()) {
            return;
        }
        final List<String> worldsList = (List<String>) c.getStringList("Mundo");
        if (!worldsList.contains(e.getPlayer().getWorld().getName())) {
            return;
        }
        if (!c.getBoolean("Protecciones.PlayersOptions.ItemDrop")) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onItemPickup(final PlayerPickupItemEvent e) {
        final FileConfiguration c = ConfigAdmin.getConfigConfig();
        if (c.getStringList("Mundo") == null && c.getStringList("Mundo").isEmpty()) {
            return;
        }
        if (e.getPlayer().isOp() || e.getPlayer().hasPermission("hc.prote.pickup")) {
            return;
        }
        final List<String> worldsList = (List<String>) c.getStringList("Mundo");
        if (!worldsList.contains(e.getPlayer().getWorld().getName())) {
            return;
        }
        if (!c.getBoolean("Protecciones.PlayersOptions.ItemPickUp")) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onMobSpawn(final CreatureSpawnEvent e) {
        final FileConfiguration c = ConfigAdmin.getConfigConfig();
        if (c.getStringList("Mundo") == null && c.getStringList("Mundo").isEmpty()) {
            return;
        }
        final List<String> worldsList = (List<String>) c.getStringList("Mundo");
        if (!worldsList.contains(e.getEntity().getWorld().getName())) {
            return;
        }
        if (c.getBoolean("Protecciones.LobbyOptions.MobSpawn")) {
            return;
        }
        if (e.getSpawnReason() == CreatureSpawnEvent.SpawnReason.CUSTOM) {
            return;
        }
        e.setCancelled(true);
    }

    @EventHandler
    public void onUseEnderpearl(final ProjectileLaunchEvent e) {
        final FileConfiguration c = ConfigAdmin.getConfigConfig();
        if (c.getStringList("Mundo") == null && c.getStringList("Mundo").isEmpty()) {
            return;
        }
        final List<String> worldsList = (List<String>) c.getStringList("Mundo");
        if (!worldsList.contains(e.getEntity().getWorld().getName())) {
            return;
        }
        if (c.getBoolean("Protecciones.PlayersOptions.UseEnderP")) {
            return;
        }
        if (e.getEntity().getType() == EntityType.ENDER_PEARL) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onSleep(final PlayerBedEnterEvent e) {
        final FileConfiguration c = ConfigAdmin.getConfigConfig();
        if (c.getStringList("Mundo") == null && c.getStringList("Mundo").isEmpty()) {
            return;
        }
        final List<String> worldsList = (List<String>) c.getStringList("Mundo");
        if (!worldsList.contains(e.getPlayer().getWorld().getName())) {
            return;
        }
        if (!c.getBoolean("Protecciones.PlayersOptions.Dormir")) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onFire(final BlockBurnEvent e) {
        final FileConfiguration c = ConfigAdmin.getConfigConfig();
        if (c.getStringList("Mundo") == null && c.getStringList("Mundo").isEmpty()) {
            return;
        }
        final List<String> worldsList = (List<String>) c.getStringList("Mundo");
        if (!worldsList.contains(e.getBlock().getWorld().getName())) {
            return;
        }
        if (!c.getBoolean("Protecciones.LobbyOptions.Fuego")) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onEncendFire(final PlayerInteractEvent e) {
        final FileConfiguration c = ConfigAdmin.getConfigConfig();
        if (c.getStringList("Mundo") == null && c.getStringList("Mundo").isEmpty()) {
            return;
        }
        final List<String> worldsList = (List<String>) c.getStringList("Mundo");
        if (!worldsList.contains(e.getPlayer().getWorld().getName())) {
            return;
        }
        if (c.getBoolean("Protecciones.LobbyOptions.Fuego")) {
            return;
        }
        if (e.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }
        final ItemStack item = e.getPlayer().getItemInHand();
        if (item.getType() == Material.FLINT_AND_STEEL || item.getType() == Material.FIREBALL || item.getType() == Material.FIRE) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlaceLava(final PlayerBucketEmptyEvent e) {
        final FileConfiguration c = ConfigAdmin.getConfigConfig();
        if (c.getStringList("Mundo") == null && c.getStringList("Mundo").isEmpty()) {
            return;
        }
        final List<String> worldsList = (List<String>) c.getStringList("Mundo");
        if (!worldsList.contains(e.getPlayer().getWorld().getName())) {
            return;
        }
        if (c.getBoolean("Protecciones.LobbyOptions.Lava")) {
            return;
        }
        if (e.getBucket() == Material.LAVA_BUCKET) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlaceWater(final PlayerBucketEmptyEvent e) {
        final FileConfiguration c = ConfigAdmin.getConfigConfig();
        if (c.getStringList("Mundo") == null && c.getStringList("Mundo").isEmpty()) {
            return;
        }
        final List<String> worldsList = (List<String>) c.getStringList("Mundo");
        if (!worldsList.contains(e.getPlayer().getWorld().getName())) {
            return;
        }
        if (c.getBoolean("Protecciones.LobbyOptions.Lava")) {
            return;
        }
        if (e.getBucket() == Material.WATER_BUCKET) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onLavaFlow(final BlockFromToEvent e) {
        final FileConfiguration c = ConfigAdmin.getConfigConfig();
        if (c.getStringList("Mundo") == null && c.getStringList("Mundo").isEmpty()) {
            return;
        }
        final List<String> worldsList = (List<String>) c.getStringList("Mundo");
        if (!worldsList.contains(e.getBlock().getWorld().getName())) {
            return;
        }
        if (c.getBoolean("Protecciones.LobbyOptions.Lava-Correr")) {
            return;
        }
        if (e.getBlock().getType() == Material.LAVA || e.getBlock().getType() == Material.STATIONARY_LAVA) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onWaterFlow(final BlockFromToEvent e) {
        final FileConfiguration c = ConfigAdmin.getConfigConfig();
        if (c.getStringList("Mundo") == null && c.getStringList("Mundo").isEmpty()) {
            return;
        }
        final List<String> worldsList = (List<String>) c.getStringList("Mundo");
        if (!worldsList.contains(e.getBlock().getWorld().getName())) {
            return;
        }
        if (c.getBoolean("Protecciones.LobbyOptions.Agua-Correr")) {
            return;
        }
        if (e.getBlock().getType() == Material.WATER || e.getBlock().getType() == Material.STATIONARY_WATER) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onUse(final PlayerInteractEvent e) {
        final FileConfiguration c = ConfigAdmin.getConfigConfig();
        if (c.getStringList("Mundo") == null && c.getStringList("Mundo").isEmpty()) {
            return;
        }
        if (e.getPlayer().isOp() || e.getPlayer().hasPermission("hc.use.use")) {
            return;
        }
        final List<String> worldsList = (List<String>) c.getStringList("Mundo");
        if (!worldsList.contains(e.getPlayer().getWorld().getName())) {
            return;
        }
        if (!c.getBoolean("Protecciones.PlayersOptions.Usar")) {
            final Block b = e.getClickedBlock();
            if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (b.getType() != Material.CHEST && b.getType() != Material.STONE_BUTTON && b.getType() != Material.WOOD_BUTTON && b.getType() != Material.ACACIA_DOOR && b.getType() != Material.BIRCH_DOOR && b.getType() != Material.DARK_OAK_DOOR && b.getType() != Material.JUNGLE_DOOR && b.getType() != Material.SPRUCE_DOOR && b.getType() != Material.TRAP_DOOR && b.getType() != Material.WOOD_DOOR && b.getType() != Material.WOODEN_DOOR && b.getType() != Material.LEVER) {
                    return;
                }
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onOpenInventories(final InventoryOpenEvent e) {
        final FileConfiguration c = ConfigAdmin.getConfigConfig();
        if (c.getStringList("Mundo") == null && c.getStringList("Mundo").isEmpty()) {
            return;
        }
        final List<String> worldsList = (List<String>) c.getStringList("Mundo");
        if (!worldsList.contains(e.getPlayer().getWorld().getName())) {
            return;
        }
        if (c.getBoolean("Protecciones.PlayersOptions.Usar")) {
            return;
        }
        final InventoryType type = e.getInventory().getType();
        if (type == InventoryType.ANVIL || type == InventoryType.BEACON || type == InventoryType.BREWING || type == InventoryType.CRAFTING || type == InventoryType.DISPENSER || type == InventoryType.DROPPER || type == InventoryType.ENCHANTING || type == InventoryType.WORKBENCH) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onHunger(final FoodLevelChangeEvent e) {
        final FileConfiguration c = ConfigAdmin.getConfigConfig();
        if (c.getStringList("Mundo") == null && c.getStringList("Mundo").isEmpty()) {
            return;
        }
        final List<String> worldsList = (List<String>) c.getStringList("Mundo");
        if (!worldsList.contains(e.getEntity().getWorld().getName())) {
            return;
        }
        if (!c.getBoolean("Protecciones.PlayersOptions.Hambre")) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onArmorStandPrevent(final PlayerArmorStandManipulateEvent e) {
        final FileConfiguration c = ConfigAdmin.getConfigConfig();
        if (c.getConfigurationSection("Mundo") == null && c.getConfigurationSection("Mundo").getValues(true).isEmpty()) {
            return;
        }
        if (e.getPlayer().isOp() || e.getPlayer().hasPermission("hc.prote.armorstand")) {
            return;
        }
        final List<String> worldsList = (List<String>) c.getStringList("Mundo");
        if (!worldsList.contains(e.getPlayer().getWorld().getName())) {
            return;
        }
        if (c.getBoolean("Protecciones.LobbyOptions.ArmorProtect")) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onHitArmorStand(final EntityDamageEvent e) {
        final FileConfiguration c = ConfigAdmin.getConfigConfig();
        if (c.getStringList("Mundo") == null && c.getStringList("Mundo").isEmpty()) {
            return;
        }
        final List<String> worldsList = (List<String>) c.getStringList("Mundo");
        if (!worldsList.contains(e.getEntity().getWorld().getName())) {
            return;
        }
        if (!c.getBoolean("Protecciones.LobbyOptions.ArmorProtect")) {
            return;
        }
        if (!(e.getEntity() instanceof ArmorStand)) {
            return;
        }
        e.setCancelled(true);
    }

    @EventHandler
    public void onDrown(final EntityDamageEvent e) {
        final FileConfiguration c = ConfigAdmin.getConfigConfig();
        if (c.getStringList("Mundo") == null && c.getStringList("Mundo").isEmpty()) {
            return;
        }
        final List<String> worldsList = (List<String>) c.getStringList("Mundo");
        if (!worldsList.contains(e.getEntity().getWorld().getName())) {
            return;
        }
        if (c.getBoolean("Protecciones.PlayersOptions.Ahogar")) {
            return;
        }
        if (!(e.getEntity() instanceof Player)) {
            return;
        }
        if (e.getCause() == EntityDamageEvent.DamageCause.DROWNING) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onDamage(final EntityDamageEvent e) {
        final FileConfiguration c = ConfigAdmin.getConfigConfig();
        if (c.getStringList("Mundo") == null && c.getStringList("Mundo").isEmpty()) {
            return;
        }
        final List<String> worldsList = (List<String>) c.getStringList("Mundo");
        if (!worldsList.contains(e.getEntity().getWorld().getName())) {
            return;
        }
        if (c.getBoolean("Protecciones.PlayersOptions.Ahogar")) {
            return;
        }
        if (!(e.getEntity() instanceof Player)) {
            return;
        }
        e.setCancelled(true);
    }

    @EventHandler
    public void onRemoveDefaultMessages(final PlayerJoinEvent e) {
        final FileConfiguration c = ConfigAdmin.getConfigConfig();
        if (c.getBoolean("AntiJoinMessage.Enable")) {
            e.setJoinMessage((String) null);
        }
    }

    @EventHandler
    public void onRemoveDefaultMessages(final PlayerQuitEvent e) {
        final FileConfiguration c = ConfigAdmin.getConfigConfig();
        if (c.getBoolean("AntiJoinMessage.Enable")) {
            e.setQuitMessage((String) null);
        }
    }
}
