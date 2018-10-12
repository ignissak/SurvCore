package sk.ignissak.su.survcore.tasks;

import org.bukkit.entity.Player;
import sk.ignissak.su.survcore.Core;
import sk.ignissak.su.survcore.utils.ActionBar;

public class VanishRunnable implements Runnable {

    ActionBar a = new ActionBar();

    @Override
    public void run() {
        if (Core.vanished.isEmpty()) return;
        for (Player p : Core.vanished) {
            a.sendActionBar(p, "§c§lSI VO VANISHI");
        }
    }
}
