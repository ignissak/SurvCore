package sk.ignissak.su.survcore.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import sk.ignissak.su.survcore.API;

import java.util.concurrent.TimeUnit;

public class ChatListener implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player p = (Player) e.getPlayer();
        String msg = e.getMessage();
        if (p.hasPermission("nitro")) {
            if (msg.contains(":lenny:")) {
                msg = msg.replace(":lenny:", "( ͡° ͜ʖ ͡°)");
            }
            if (msg.contains(":shrug:")) {
                msg = msg.replace(":shrug:", "¯\\_(ツ)_/¯");
            }
            if (msg.contains(":tableflip:")) {
                msg = msg.replace(":tableflip:", "(╯°□°）╯︵ ┻━┻");
            }
            if (msg.contains("<3")) {
                msg = msg.replace("<3", "❤");
            }
            if(msg.contains(":cry:")){
                msg = msg.replace(":cry:", "(ಥ﹏ಥ)");
            }
            e.setMessage(msg);
        }
        if (API.isAdmin(p)) {
            e.setFormat("§4§lA §f" + p.getName() + "§7: §f%2$s" );
        } else if (API.isManager(p)) {
            e.setFormat("§6§lM §f" + p.getName() + "§7: §f%2$s");
        } else if (API.isNitroPlayer(p)) {
            e.setFormat("§3§lN §f" + p.getName() + "§7: §f%2$s");
        } else {
            e.setFormat("§f" + p.getName() + "§7: §f" + e.getMessage());
        }

    }
}
