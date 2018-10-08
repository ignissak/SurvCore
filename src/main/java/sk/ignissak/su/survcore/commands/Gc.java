package sk.ignissak.su.survcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import sk.ignissak.su.survcore.Lag;

import java.lang.management.ManagementFactory;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public class Gc implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        double tps = Math.round(Lag.getTPS() / 10.00) * 10.00;
        double lag = Math.round((1.00D - tps / 20.00D) * 100.00D);
        if (!sender.hasPermission("admin")) {
            sender.sendMessage("§fAktuálne TPS: " + formatTPS(tps) + " §7(lag: " + lag + "%)");
            return true;
        }
        else {
            Long passed;
            passed = System.currentTimeMillis() - ManagementFactory.getRuntimeMXBean().getStartTime();
            sender.sendMessage("§fAktuálne TPS: " + formatTPS(tps) + " §7(lag: " + lag + "%)");
            sender.sendMessage("§fUptime: §a" + TimeUnit.MILLISECONDS.toHours(passed) + "h " + TimeUnit.MILLISECONDS.toMinutes(passed) % 60 + "m " + TimeUnit.MILLISECONDS.toSeconds(passed) % 60 % 60 + "s");
            sender.sendMessage("§fMemory (max/total/free): §a" + (Runtime.getRuntime().maxMemory() / 1024 / 1024) + "§7/§a" + (Runtime.getRuntime().totalMemory() / 1024 / 1024) + "§7/§a" +
                    (Runtime.getRuntime().freeMemory() / 1024 / 1024));
            List<World> worlds = Bukkit.getServer().getWorlds();
            for (World w : worlds) {
                String worldType = "World";
                switch (w.getEnvironment()) {
                    case NETHER:
                        worldType = "Nether";
                        break;
                    case THE_END:
                        worldType = "The End";
                        break;
                }

                int tileEntities = 0;

                try {
                    for (Chunk chunk : w.getLoadedChunks()) {
                        tileEntities += chunk.getTileEntities().length;
                    }
                } catch (java.lang.ClassCastException ex) {
                    Bukkit.getLogger().log(Level.SEVERE, "Corrupted chunk data on world " + w, ex);
                }
                sender.sendMessage("§fWorld " + w.getName() + " §7(" + worldType + ")§f ma loaded chunkov §7" + w.getLoadedChunks().length + "§f, entit §7" + w.getEntities().size() + "§f a tile §7" + tileEntities);
                //sender.sendMessage(tl("gcWorld", worldType, w.getName(), w.getLoadedChunks().length, w.getEntities().size(), tileEntities));
            }
        }
        return true;
    }

    private String formatTPS(Double s) {
        if (s >= 15.0) {
            return "§a" + s;
        }
        if (s > 10.0) {
            return "§e" + s;
        }
        if (s < 10.0) {
            return "§c" + s;
        }


        return "§7" + s;
    }
}
