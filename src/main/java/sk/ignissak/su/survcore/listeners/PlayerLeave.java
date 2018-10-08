package sk.ignissak.su.survcore.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import sk.ignissak.su.survcore.PlaytimeManager;
import sk.ignissak.su.survcore.SQLManager;
import sk.ignissak.su.survcore.utils.BukkitSerialization;

public class PlayerLeave implements Listener {

    PlaytimeManager ptm = new PlaytimeManager();
    SQLManager sql = new SQLManager();

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        Player p = (Player) e.getPlayer();

        if (p.hasPermission("nitro")) {
            e.setQuitMessage("§3" + p.getName() + " left the game");
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
