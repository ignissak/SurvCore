package sk.ignissak.su.survcore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import sk.ignissak.su.survcore.SQLManager;

import java.util.concurrent.TimeUnit;

public class Playtime implements CommandExecutor {

    SQLManager sql = new SQLManager();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Long playTime = sql.getPlaytimeData(sender.getName());
        Long hodky = TimeUnit.MILLISECONDS.toHours(playTime);
        Long minutky = TimeUnit.MILLISECONDS.toMinutes(playTime) % 60;
        if (args.length == 0) {
            sender.sendMessage("§fTvoj playtime: §a" + hodky + "h§f a §a" + minutky + "m");
            return true;
        }
        else {
            if (!sender.hasPermission("admin")) {
                sender.sendMessage("§c§l(!) §cPrávo na zistenie playtime ostatných hráčov majú iba admini.");
                return true;
            }
            if (sql.getPlaytimeData(args[0]) == 0) {
                sender.sendMessage("§cTento hráč sa zrejme nikdy nepripojil alebo je neplatný");
                return true;
            }
            //ptm.fetchData(args[0]);
            long playtime = sql.getPlaytimeData(args[0]);
            long hodkyy = TimeUnit.MILLISECONDS.toHours(playtime);
            long minutkyy = TimeUnit.MILLISECONDS.toMinutes(playtime) % 60;
            sender.sendMessage("§aPlaytime hráča §2" + args[0] + "§a: §2" + hodkyy + "h a " + minutkyy + "m§a.");
            return true;
        }

    }
}
