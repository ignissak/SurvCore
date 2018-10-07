package sk.ignissak.su.survcore.objects;

import org.bukkit.entity.Player;

public class SurvPlayer {

    private Player p;
    private int deaths;


    public SurvPlayer(Player p, int deaths) {
        this.p = p;
        this.deaths = deaths;
    }

    public Player getPlayer() {
        return p;
    }

    public int getDeaths() {
        return deaths;
    }


}
