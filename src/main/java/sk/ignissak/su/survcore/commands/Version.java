package sk.ignissak.su.survcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import sk.ignissak.su.survcore.Core;

public class Version implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage("§fServer beží na §e" + Bukkit.getName());
        sender.sendMessage("§fVerzia: §e" + Bukkit.getVersion());
        sender.sendMessage("§fAPI verzia: §e" + Bukkit.getBukkitVersion());
        sender.sendMessage("§fVerzia pluginu: §6" + Core.getInstance().getDescription().getVersion());
        return true;
    }
}
