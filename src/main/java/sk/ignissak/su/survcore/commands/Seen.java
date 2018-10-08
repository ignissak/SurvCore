package sk.ignissak.su.survcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import sk.ignissak.su.survcore.SQLManager;

import java.util.concurrent.TimeUnit;

public class Seen implements CommandExecutor {

    SQLManager sql = new SQLManager();
    Long join;
    Long quit;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("management")) {
            sender.sendMessage("§c§l(!) §cPrístup k tomuto príkazu má iba vedenie serveru!");
            return true;
        }
        if (args.length == 1) {
            if (!sql.hasLogData(args[0])) {
                invalidPlayer(sender);
                return true;
            }
            if (Bukkit.getPlayer(args[0]).isOnline()) { //online
                join = sql.getLogJoinData(args[0]);
                if (join == 0) {
                    invalidPlayer(sender);
                    return true;
                }
                sender.sendMessage("§fHráč §a" + args[0] + "§f je online už po dobu §a" + TimeUnit.MILLISECONDS.toHours(System.currentTimeMillis() - join) + "h "
                        + TimeUnit.MILLISECONDS.toMinutes(join) % 60 + "m " + TimeUnit.MILLISECONDS.toSeconds(join) % 60 % 60 + "s§f.");
                return true;
            }
            else {
                quit = sql.getLogQuitData(args[0]);
                if (quit == 0) {
                    invalidPlayer(sender);
                    return true;
                }
                sender.sendMessage("§fHráč §c" + args[0] + " §fbol online naposledy pred §c" + TimeUnit.MILLISECONDS.toDays(join) + "d " + TimeUnit.MILLISECONDS.toHours(join) % 24 + "h " +
                        TimeUnit.MILLISECONDS.toMinutes(join) % 24 % 60 + "m " + TimeUnit.MILLISECONDS.toSeconds(join) % 24 % 60 % 60 + "s§f.");
                return true;
            }
        }
        return true;
    }

    private void invalidPlayer(CommandSender s) {
        s.sendMessage("§c§l(!) §cNeplatný herný účet (alebo hráč ešte nie je v database)!");
    }
}
