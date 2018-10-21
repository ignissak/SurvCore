package sk.ignissak.su.survcore.commands;

import net.minecraft.server.v1_13_R2.PacketPlayOutWorldParticles;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Spawn implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player p = (Player) sender;

        if (!p.hasPermission("admin")) {
            p.sendMessage("§c§l(!)§c Prístup k tomuto príkazu majú iba administrátori!");
            return true;
        }

        p.teleport(p.getWorld().getSpawnLocation());
        p.sendMessage("§aWhoosh!");
        p.playSound(p.getLocation(), Sound.BLOCK_DISPENSER_DISPENSE, 1.0F, 2.0F);
        //p.getLocation().getWorld().spawnParticle(Particle.SMOKE_NORMAL, p.getLocation(), 10, 0, 0, 0, 0.05);
        p.getLocation().getWorld().spawnParticle(Particle.DRAGON_BREATH, p.getLocation(), 100, 0.5, 1, 0.5,0.01);

        return true;
    }
}
