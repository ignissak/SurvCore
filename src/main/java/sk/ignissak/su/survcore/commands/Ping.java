package sk.ignissak.su.survcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class Ping implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;
        if (args.length == 0) {
            p.sendMessage("§fTvoj ping: " + formatPing(((CraftPlayer) p).getHandle().ping) + "ms");
            return true;
        }
        else {
            if (!p.hasPermission("nitro")) {
                p.sendMessage("§c§l(!) §cPrístup k tomuto príkazu majú iba hráči s NITRO.");
                return true;
            }
            Player pl = Bukkit.getPlayer(args[0]);
            if (pl == null) {
                p.sendMessage("§c§l(!) §cTento hráč nie je online.");
                return true;
            }
            p.sendMessage("§fPing hráča §7" + pl.getName() + "§f: " + formatPing(((CraftPlayer) p).getHandle().ping) + "ms");
            return true;
        }
    }

    private String formatPing(Integer i) {
        if (i <= 75) {
            return "§a" + i;
        }
        if (i < 200) {
            return "§e" + i;
        }
        if (i > 200) {
            return "§c" + i;
        }
        return "§a" + i;
    }
}
