package sk.ignissak.su.survcore.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;

public class BedListener implements Listener {

    public static int inBed;

    @EventHandler
    public void onBedListener(PlayerBedEnterEvent e) {
        Player p = (Player) e.getPlayer();

        inBed++;
        Bukkit.broadcastMessage("§fHráč §7" + p.getName() + " §fzaľahol do postele §7(" + inBed + "/" + Bukkit.getServer().getOnlinePlayers().size() + ")§f.");
    }

    @EventHandler
    public void onBedLeave(PlayerBedLeaveEvent e) {
        Player p = e.getPlayer();

        inBed--;
    }

    private String formatPercents(Double percent) {
        if (percent >= 66) {
            return "§a" + percent + "%";
        } else if (percent < 65.999) {
            return "§e" + percent + "%";
        } else if (percent < 33.999) {
            return "§c" + percent + "%";
        } else {
            return "§c" + percent + "%";
        }
    }
}
