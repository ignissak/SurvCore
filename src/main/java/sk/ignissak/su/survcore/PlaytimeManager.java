package sk.ignissak.su.survcore;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlaytimeManager {

    SQLManager sql = new SQLManager();

    private Player p;

    public PlaytimeManager(Player p) {
        this.p = p;
        this.fetchData(p.getName());
    }

    public void fetchData(String p) {
        long previous = sql.getPlaytimeData(p);
        long leave = System.currentTimeMillis() - sql.getPlaytimeJoinData(p);
        long now = previous + leave;

        sql.setPlaytimeJoinData(Bukkit.getPlayer(p), System.currentTimeMillis());
        sql.setPlaytimeData(p, now);
    }
}
