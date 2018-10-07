package sk.ignissak.su.survcore;

import org.bukkit.entity.Player;

public class API {

    public static boolean isNitroPlayer(Player p) {
        if (p.hasPermission("nitro")) return true;
        return false;
    }

    public static boolean isAdmin(Player p) {
        if (p.hasPermission("admin")) return true;
        return false;
    }
}
