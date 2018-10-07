package sk.ignissak.su.survcore.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class PlayerLoginEvent implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onLogin(org.bukkit.event.player.PlayerLoginEvent e) {
        Player p = (Player) e.getPlayer();

        if (!p.isWhitelisted() && !p.hasPermission("admin")) {
            e.disallow(org.bukkit.event.player.PlayerLoginEvent.Result.KICK_OTHER, "§cNie si clen Survive Universe, nemas pristup na server.\n§7Nabor je na adrese §ahttps://goo.gl/4av9kD§7.");
            return;
        }

        if (Bukkit.getServer().getOnlinePlayers().size() == 15 && !p.hasPermission("nitro")) {
            e.disallow(org.bukkit.event.player.PlayerLoginEvent.Result.KICK_OTHER, "§cServer je momentalne pristupny (5 rezervovanych slotu) jenom hracum s §3§lNITRO§c.\n§7Pro zakoupeni Nitra napis ignissovi na Discord.");
            return;
        }

        if (Bukkit.getServer().getOnlinePlayers().size() == 20) {
            e.disallow(org.bukkit.event.player.PlayerLoginEvent.Result.KICK_FULL, "§cServer je momentalne plny.");
            return;
        }
    }
}
