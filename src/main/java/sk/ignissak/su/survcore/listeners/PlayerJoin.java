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
    /*ScoreboardManager manager = Bukkit.getScoreboardManager();
    Scoreboard board = manager.getNewScoreboard();*/

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = (Player) e.getPlayer();

        /*Team nitro = board.registerNewTeam("nitro");
        nitro.setPrefix("§3");
        Team player = board.registerNewTeam("player");

        if (API.isNitroPlayer(p)) {
            System.out.print("Davam do nitro");
            nitro.addPlayer(p);
        } else if (!API.isNitroPlayer(p)) {
            System.out.print("Davam do player");
            player.addPlayer(p);
        }
        p.setScoreboard(board);*/

        setupTablist(p);

        if (!sql.hasHomeData(p)) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "Hrac " + p.getName() + " bol zapisany do homes database.");
            sql.instertHomeData(p);
        }

        if (!sql.hasPlaytimeData(p)) {
            sql.insertPlaytimeData(p);
            Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "Hrac " + p.getName() + " byl zapsan do databaze playtimu.");
        }

        if (!sql.hasInventoryData(p)) {
            sql.insertInventoryData(p);
            Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "Hrac " + p.getName() + " byl zapsan do databaze inventory.");
        }

        if (p.hasPermission("admin")) {
            e.setJoinMessage("§c" + p.getName() + " joined the game");
        }

        if (p.hasPermission("nitro") && !p.hasPermission("admin")) {
            e.setJoinMessage("§3" + p.getName() + " joined the game");
        }

        sql.setPlaytimeJoinData(p, System.currentTimeMillis());
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
