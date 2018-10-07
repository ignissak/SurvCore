package sk.ignissak.su.survcore.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;
import sk.ignissak.su.survcore.Core;

import java.util.List;

public class MotdListener implements Listener {

    @EventHandler
    public void onMotd(ServerListPingEvent e) {
        List<String> motdLines = Core.getInstance().getConfig().getStringList("motd");
        e.setMotd(motdLines.get(0) + "\n" + motdLines.get(1));
    }
}
