package sk.ignissak.su.survcore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import sk.ignissak.su.survcore.SQLManager;

public class Sethome implements CommandExecutor {

    SQLManager sql = new SQLManager();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player p = (Player) sender;

        sql.setHome(p, p.getLocation());
        p.sendMessage("§aTvoj home bol nastavený na tvoju lokáciu.");
        return true;
    }
}
