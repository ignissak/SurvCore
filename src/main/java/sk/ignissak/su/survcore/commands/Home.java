package sk.ignissak.su.survcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import sk.ignissak.su.survcore.SQLManager;


public class Home implements CommandExecutor {

    SQLManager sql = new SQLManager();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player p = (Player) sender;
        Location loc = null;
        if (args.length == 0) {
            if (sql.getHome(p.getName()) == loc) {
                p.sendMessage("§c§l(!) §cNemáš zatiaľ nastavený home. Použi /sethome pre nastavenie (limit: 1).");
                return true;
            }
            p.teleport(sql.getHome(p.getName()));
            p.sendMessage("§aTeleportácia na home lokáciu...");
            return true;
        }
        if (args.length == 1) {
            if (!p.hasPermission("admin")) {
                p.sendMessage("§c§l(!) §cPrístup k tomuto príkazu majú iba administrátori!");
                return true;
            }
            if (sql.getHome(args[0]) == loc) {
                p.sendMessage("§c§l(!) §cNeplatný hráč alebo tento hráč nemá home.");
                return true;
            }
            p.teleport(sql.getHome(args[0]));
            p.sendMessage("§aTeleportácia na home lokáciu hráča " + args[0] + "...");
            return true;
        }
        return true;
    }
}
