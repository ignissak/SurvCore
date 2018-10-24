package sk.ignissak.su.survcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import sk.ignissak.su.survcore.Core;

public class Maintenance implements CommandExecutor {

    private boolean state = Core.getInstance().getConfig().getBoolean("maintenance");

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player p = (Player) sender;

        if (!p.hasPermission("admin")) {
            p.sendMessage("§c§l(!)§c Prístup k tomuto príkazu majú iba administrátori!");
            return true;
        }

        if (state) {
            Core.getInstance().turnOffMaintenance();
            p.sendMessage("§aÚdržba bolá úspešne vypnutá, hooray!");
            return true;
        }
        if (!state) {
            Core.getInstance().turnOnMaintenanace();
            for (Player pl : Bukkit.getOnlinePlayers()) {
                if (!pl.hasPermission("management")) {
                    pl.kickPlayer("§cBola zapnutá údržba, ďakujeme za pochopenie.");
                    return true;
                }
            }
            p.sendMessage("§aÚdržba bola zapnutá!");
            return true;
        }

        return true;
    }
}
