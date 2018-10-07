package sk.ignissak.su.survcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import sk.ignissak.su.survcore.Core;

public class Version implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage("§fServer beží na §7" + Bukkit.getName());
        sender.sendMessage("§fVerzia: §7" + Bukkit.getVersion());
        sender.sendMessage("§fAPI verzia: §7" + Bukkit.getBukkitVersion());
        sender.sendMessage("§fVerzia pluginu: §7" + Core.getInstance().getDescription().getVersion());
        return true;
    }
}
