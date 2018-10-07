package sk.ignissak.su.survcore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import sk.ignissak.su.survcore.SQLManager;

public class Odpojit implements CommandExecutor {

    SQLManager sql = new SQLManager();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player p = (Player) sender;

        if (sql.hasRequestAlready(p) || sql.isVerifiedAlready(p)) {
            sql.unlinkAccount(p);
            p.sendMessage("§aVšetky záznamy pre prepojenie pre tento účet boli vymazané.");
            return true;
        } else {
            p.sendMessage("§c§l(!) §cVypadá to, že tento účet nie je prepojený a nemá podanú žiadosť.");
        }
        return true;
    }
}
