package sk.ignissak.su.survcore.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import sk.ignissak.su.survcore.*;

public class PlayerJoin implements Listener {

    SQLManager sql = new SQLManager();
    PlaytimeManager ptm = new PlaytimeManager();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = (Player) e.getPlayer();


        setupTablist(p);

        if (!sql.hasHomeData(p)) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "Hráč " + p.getName() + " bol zapísaný do home database.");
            sql.instertHomeData(p);
        }

        if (!sql.hasPlaytimeData(p)) {
            sql.insertPlaytimeData(p);
            Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "Hráč " + p.getName() + " bol zapísaný do playtime database.");
        }

        if (!sql.hasInventoryData(p)) {
            sql.insertInventoryData(p);
            Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "Hráč " + p.getName() + " bol zapísaný do inventory database.");
        }

        if (!sql.hasLogData(p.getName())) {
            sql.insertLogData(p);
            Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "Hráč " + p.getName() + " bol zapísaný do log database.");
        }

        if (p.hasPermission("admin")) {
            e.setJoinMessage("§c" + p.getName() + " joined the game");
        }

        if (p.hasPermission("nitro") && !p.hasPermission("admin")) {
            e.setJoinMessage("§3" + p.getName() + " joined the game");
        }

        sql.setPlaytimeJoinData(p, System.currentTimeMillis());
        sql.setLogJoin(p.getName(), System.currentTimeMillis());

        int id2 = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Core.getInstance(), new Runnable() {
            @Override
            public void run() {
                if (p.isOnline()) {
                    ptm.fetchData(p.getName());
                }
            }
        }, 0, 60 * 20);

    }

    public void setupTablist(Player p) {
        if (p.hasPermission("admin")) {
            p.setPlayerListName("§4§lA §f" + p.getName());
        } else if (p.hasPermission("nitro")) {
            p.setPlayerListName("§3§lN §f" + p.getName());
        }
        else {
            p.setPlayerListName("§f" + p.getName());
        }
    }
}
