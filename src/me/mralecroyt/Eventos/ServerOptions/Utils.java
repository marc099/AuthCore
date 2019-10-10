package me.mralecroyt.Eventos.ServerOptions;


import org.bukkit.event.player.*;
import me.mralecroyt.administrador.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.*;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.configuration.file.*;
import org.bukkit.entity.*;
import java.lang.reflect.*;
import org.bukkit.event.*;
import me.mralecroyt.Listener.*;

public class Utils implements Listener {
    @EventHandler
    public void onTabJoin(final PlayerJoinEvent e) {
        final FileConfiguration c = ConfigAdmin.getConfigConfig();
        final Player p = e.getPlayer();
        final PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter();
        final Object header = new ChatComponentText(PlaceHoldersApi.placeholder(c.getString("Online.Arriba"), p));
        final Object footer = new ChatComponentText(PlaceHoldersApi.placeholder(c.getString("Online.Abajo"), p));
        try {
            final Field a = packet.getClass().getDeclaredField("a");
            a.setAccessible(true);
            final Field b = packet.getClass().getDeclaredField("b");
            b.setAccessible(true);
            a.set(packet, header);
            b.set(packet, footer);
            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
        } catch (NoSuchFieldException | IllegalAccessException ex2) {
            final ReflectiveOperationException ex;
            final ReflectiveOperationException e2 = ex2;
            e2.printStackTrace();
        }
    }
}