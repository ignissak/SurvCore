package sk.ignissak.su.survcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import sk.ignissak.su.survcore.Core;

public class Vanish implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player p = (Player) sender;

        if (!p.hasPermission("admin")) {
            p.sendMessage("§c§l(!) §cPrístup k tomuto príkazu majú iba administrátori!");
            return true;
        }

        if (Core.vanished.contains(p)) {
            Core.vanished.remove(p);
            p.sendMessage("§fVanish: §cOFF");
            for (Player pl : Bukkit.getOnlinePlayers()) {
                pl.showPlayer(Core.getInstance(), p);
            }
            return true;
        } else {
            Core.vanished.add(p);
            p.sendMessage("§fVanish: §aON");
            for (Player pl : Bukkit.getOnlinePlayers()) {
                pl.hidePlayer(Core.getInstance(), p);
            }
            return true;
        }
    }
}
