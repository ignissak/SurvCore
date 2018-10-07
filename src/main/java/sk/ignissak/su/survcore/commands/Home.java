package sk.ignissak.su.survcore.commands;

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
        if (sql.getHome(p) == loc) {
            p.sendMessage("§c§l(!) §cNemáš zatiaľ nastavený home. Použi /sethome pre nastavenie (limit: 1).");
            return true;
        }
        p.teleport(sql.getHome(p));
        p.sendMessage("§aTeleportácia na home lokáciu...");
        return true;
    }
}
