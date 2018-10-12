package sk.ignissak.su.survcore.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import sk.ignissak.su.survcore.Core;
import sk.ignissak.su.survcore.PlaytimeManager;
import sk.ignissak.su.survcore.SQLManager;
import sk.ignissak.su.survcore.utils.BukkitSerialization;

import java.util.List;
import java.util.Random;

public class PlayerLeave implements Listener {

    PlaytimeManager ptm = new PlaytimeManager();
    SQLManager sql = new SQLManager();

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        Player p = (Player) e.getPlayer();

        if (Core.getInstance().getSeason().equalsIgnoreCase("halloween")) { //hallowen update
            Random randomizer = new Random();
            List<String> list = Core.getInstance().halloweenQuits();
            String random = list.get(randomizer.nextInt(list.size()));
            if (p.hasPermission("admin")) {
                e.setQuitMessage("§c" + random.replace("%player%", p.getName()));
            }
            if (p.hasPermission("nitro") && !p.hasPermission("admin")) {
                e.setQuitMessage("§3" + random.replace("%player%", p.getName()));
            } else {
                e.setQuitMessage("§e" + random.replace("%player%", p.getName()));
            }
        } else {
            if (p.hasPermission("admin")) {
                e.setQuitMessage("§c" + p.getName() + " left the game");
            }
            if (p.hasPermission("nitro") && !p.hasPermission("admin")) {
                e.setQuitMessage("§3" + p.getName() + " left the game");
            }
        }

        ptm.fetchData(p.getName());
        sql.setInventory(p, BukkitSerialization.toBase64(p.getInventory()));
        sql.setLogQuit(p.getName(), System.currentTimeMillis());
    }

    @EventHandler
    public void onKick(PlayerKickEvent e) {
        Player p = (Player) e.getPlayer();
        sql.setLogQuit(p.getName(), System.currentTimeMillis());
    }
}
