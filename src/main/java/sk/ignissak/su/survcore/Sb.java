package sk.ignissak.su.survcore;

import org.bukkit.Bukkit;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

public class Sb {

    ScoreboardManager m = Bukkit.getScoreboardManager();
    Scoreboard b = m.getNewScoreboard();
    Team n = b.getTeam("nitro");
    Team p = b.getTeam("player");

    public void registerTeams() {
        if (b.getTeam("nitro") == null) {
            b.registerNewTeam("nitro");
        }
        if (b.getTeam("player") == null) {
            b.registerNewTeam("player");
        }
    }
}
