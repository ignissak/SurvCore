package sk.ignissak.su.survcore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Spawn implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player p = (Player) sender;

        if (!p.hasPermission("admin")) {
            p.sendMessage("§c§l(!)§c Prístup k tomuto príkazu majú iba administrátori!");
            return true;
        }

        p.teleport(p.getWorld().getSpawnLocation());
        p.sendMessage("§aWhoosh!");

        return true;
    }
}
