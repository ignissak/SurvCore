package sk.ignissak.su.survcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Msg implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player p = (Player) sender;

        if (args.length < 1) {
            p.sendMessage("§c§l(!) §cA komu chceš písať? /msg NICK SPRÁVA");
            return true;
        }
        if (args.length >= 2) {

            if (args[0].equalsIgnoreCase(p.getName())) {
                p.sendMessage("§c§l(!) §cNájdi si kamarátov, aby si nemusel písať sám sebe :(");
            }

            if (Bukkit.getPlayer(args[0]) == null) {
                p.sendMessage("§c§l(!) §cHráč s nickom " + args[0] + " nie je dostupný!");
                return true;
            }

            StringBuilder sb = new StringBuilder();
            for (int i = 1; i < args.length; i++) {
                sb.append(args[i]);
                sb.append(" ");
            }
            String msg = sb.toString();

            Bukkit.getPlayer(args[0]).sendMessage("§6§lMSG §f" + args[0] + " §7-> §fty_ §7| §f" + msg);
            p.sendMessage("§6§lMSG §fty §7-> §f" + args[0] + " §7| §f" + msg);

            return true;
        } else {
            p.sendMessage("§c§l(!) §cA komu chceš písať? /msg NICK SPRÁVA");
            return true;
        }
    }
}
