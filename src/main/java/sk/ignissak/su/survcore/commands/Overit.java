package sk.ignissak.su.survcore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import sk.ignissak.su.survcore.Core;
import sk.ignissak.su.survcore.SQLManager;

public class Overit implements CommandExecutor {
    
    SQLManager sql = new SQLManager();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player p = (Player) sender;

        if (sql.hasRequestAlready(p)) {
            p.sendMessage("§c§l(!) §cŽiadosť o prepojenie si už podal, zadaj ?overit " + p.getName() + " na discorde.");
            return true;
        }
        if (sql.isVerifiedAlready(p)) {
            p.sendMessage("§c§l(!) §cTvoj účet už je prepojený s discord účtom. Pre odpojenie účtu napíš /odpojit.");
            return true;
        }

        sql.createConnectionRequest(p);
        p.sendMessage("§aŽiadosť o prepojenie účtu bola poslaná do databáze.");
        p.sendMessage("§aNapíš na discord serveri príkaz ?overit " + p.getName() + ".");
        return true;
    }
}
