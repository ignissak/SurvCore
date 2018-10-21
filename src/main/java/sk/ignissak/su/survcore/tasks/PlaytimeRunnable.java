package sk.ignissak.su.survcore.tasks;

import org.bukkit.Bukkit;
import sk.ignissak.su.survcore.PlaytimeManager;

public class PlaytimeRunnable implements Runnable {

    @Override
    public void run() {
        Bukkit.getOnlinePlayers().forEach(player -> new PlaytimeManager(player));
    }
}
