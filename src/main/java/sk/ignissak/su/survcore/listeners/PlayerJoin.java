package sk.ignissak.su.survcore.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import sk.ignissak.su.survcore.*;

import java.util.List;
import java.util.Random;

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

        if (Core.getInstance().getSeason().equalsIgnoreCase("halloween")) { //hallowen update
            Random randomizer = new Random();
            List<String> list = Core.getInstance().halloweenJoins();
            String random = list.get(randomizer.nextInt(list.size()));
            if (p.hasPermission("admin")) {
                e.setJoinMessage("§c" + random.replace("%player%", p.getName()));
            } else if (p.hasPermission("management")) {
                e.setJoinMessage("§6" + random.replace("%player%", p.getName()));
            } else if (p.hasPermission("nitro")) {
                e.setJoinMessage("§3" + random.replace("%player%", p.getName()));
            } else {
                e.setJoinMessage("§e" + random.replace("%player%", p.getName()));
            }
        } else {
            if (p.hasPermission("admin")) {
                e.setJoinMessage("§c" + p.getName() + " joined the game");
            } else if (p.hasPermission("management")) {
                e.setJoinMessage("§6" + p.getName() + " joined the game");
            } else if (p.hasPermission("nitro")) {
                e.setJoinMessage("§3" + p.getName() + " joined the game");
            }
        }

        sql.setPlaytimeJoinData(p, System.currentTimeMillis());
        sql.setLogJoin(p.getName(), System.currentTimeMillis());

        for (Player pl : Core.vanished) {
            for (Player pp : Bukkit.getOnlinePlayers()) {
                pp.hidePlayer(pl);
            }
        }

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
        } else if (p.hasPermission("management")) {
            p.setPlayerListName("§6§lM §f" + p.getName());
        } else if (p.hasPermission("nitro")) {
            p.setPlayerListName("§3§lN §f" + p.getName());
        }
        else {
            p.setPlayerListName("§f" + p.getName());
        }
    }
}
