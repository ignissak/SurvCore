package sk.ignissak.su.survcore.objects;

import org.bukkit.entity.Player;
import sk.ignissak.su.survcore.SQLManager;
import sk.ignissak.su.survcore.utils.BukkitSerialization;

public class Inventar {

    private Player p;
    SQLManager sql = new SQLManager();

    public Inventar(Player p) {
        this.p = p;
        sql.setInventory(this.p, BukkitSerialization.toBase64(p.getInventory()));
    }
}
