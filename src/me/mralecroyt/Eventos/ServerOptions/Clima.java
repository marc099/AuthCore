package me.mralecroyt.Eventos.ServerOptions;

import org.bukkit.event.weather.*;
import org.bukkit.event.*;
import org.bukkit.event.player.*;
import org.bukkit.scheduler.*;
import me.mralecroyt.administrador.*;
import org.bukkit.*;
import org.bukkit.configuration.file.*;
import java.util.*;
import me.mralecroyt.authcore.*;
import org.bukkit.plugin.*;

public class Clima implements Listener
{
    @EventHandler(priority = EventPriority.HIGHEST)
    public void DisableWeather(final WeatherChangeEvent e) {
        e.setCancelled(e.toWeatherState());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onRemoveByWorld(final PlayerChangedWorldEvent e) {
        e.getPlayer().getWorld().setStorm(false);
        e.getPlayer().getWorld().setThundering(false);
    }
}
