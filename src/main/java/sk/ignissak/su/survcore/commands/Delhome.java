package sk.ignissak.su.survcore.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import sk.ignissak.su.survcore.SQLManager;

public class Delhome implements CommandExecutor {

    SQLManager sql = new SQLManager();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player p = (Player) sender;
        Location loc = null;
        if (sql.getHome(p.getName()) == loc) {
            p.sendMessage("§c§l(!) §cAktuálne nemáš nastavenú žiadnu home lokáciu.");
            return true;
        }
        sql.deleteHome(p);
        p.sendMessage("§aTvoja home lokácia bola vymazaná.");
        return true;
    }
}
