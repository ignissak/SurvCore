package sk.ignissak.su.survcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
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
            Player p = (Player) sender;
            sender.sendMessage("§fTvoj playtime je §a" + hodky + "h§f a §a" + minutky + "m§f. §7(#" + sql.getTopPlaytime(p) + ")");
            return true;
        }
        else {
            if (!sender.hasPermission("management")) {
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
            sender.sendMessage("§fPlaytime hráča §2" + args[0] + "§f je §2" + hodkyy + "h §fa§a " + minutkyy + "m§f. §7(#" + sql.getTopPlaytime(Bukkit.getPlayer(args[0])) + ")");
            return true;
        }

    }
}
