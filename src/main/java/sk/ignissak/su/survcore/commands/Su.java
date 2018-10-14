package sk.ignissak.su.survcore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import sk.ignissak.su.survcore.Core;

public class Su implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("admin")) {
            sender.sendMessage("§c§l(!) §cPrístup k tomuto príkazu majú iba administrátori!");
            return true;
        }
        Core.getInstance().reloadConfig();
        sender.sendMessage("§aConfig bol reloadovaný.");
        return true;
    }
}
