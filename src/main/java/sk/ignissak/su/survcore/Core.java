package sk.ignissak.su.survcore;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;
import sk.ignissak.su.survcore.commands.*;
import sk.ignissak.su.survcore.listeners.*;
import sk.ignissak.su.survcore.tasks.VanishRunnable;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class Core extends JavaPlugin {

    private static Core instance;
    public HikariDataSource hikari;
    SQLManager sql = new SQLManager();
    public static List<Player> vanished = new ArrayList<>();
    private SQLManager sqlgetter;

    @Override
    public void onEnable() {
        setupHikari();
        saveDefaultConfig();

        registerCommands();
        registerListeners();

        instance = this;

        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Lag(), 100L, 1L);
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new VanishRunnable(), 100L, 25L);
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new PlaytimeRunnable(), 100L, 60L*20L);

        sql.createHomes();
        sql.createPlaytimeTable();

        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "----------------");
        Bukkit.getConsoleSender().sendMessage("");
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "SurvCore V" + getDescription().getVersion());
        Bukkit.getConsoleSender().sendMessage("");
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "----------------");

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Core getInstance() {
        return instance;
    }

    public void setupHikari() {
        HikariConfig hikari = new HikariConfig();
        hikari.setJdbcUrl("jdbc:mysql://" + getConfig().getString("mysql.host") + ":3306/" + getConfig().getString("mysql.database"));
        hikari.setDriverClassName("com.mysql.jdbc.Driver");
        hikari.setUsername(getConfig().getString("mysql.user"));
        hikari.setPassword(getConfig().getString("mysql.password"));
        hikari.setConnectionTimeout(getConfig().getInt("mysql.timeout"));
        hikari.setMaximumPoolSize(getConfig().getInt("mysql.maximumConnections"));
        this.hikari = new HikariDataSource(hikari);

    }

    public HikariDataSource getHikari() {
        return hikari;
    }

    public Connection getConnection() throws SQLException {
        return hikari.getConnection();
    }

    private void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new PlayerJoin(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerLeave(), this);
        Bukkit.getPluginManager().registerEvents(new MotdListener(), this);
        Bukkit.getPluginManager().registerEvents(new ChatListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerLoginEvent(), this);
        Bukkit.getPluginManager().registerEvents(new BedListener(), this);
    }

    private void registerCommands() {
        getCommand("sethome").setExecutor(new Sethome());
        getCommand("home").setExecutor(new Home());
        getCommand("delhome").setExecutor(new Delhome());
        getCommand("gc").setExecutor(new Gc());
        getCommand("playtime").setExecutor(new Playtime());
        getCommand("ping").setExecutor(new Ping());
        getCommand("overit").setExecutor(new Overit());
        getCommand("odpojit").setExecutor(new Odpojit());
        getCommand("version").setExecutor(new Version());
        getCommand("vanish").setExecutor(new Vanish());
        getCommand("seen").setExecutor(new Seen());
        getCommand("spawn").setExecutor(new Spawn());
        getCommand("msg").setExecutor(new Msg());
        getCommand("su").setExecutor(new Su());
    }

    public String getSeason() {
        return getConfig().getString("season");
    }

    public List<String> halloweenJoins() {
        return getConfig().getStringList("halloween_join");
    }

    public List<String> halloweenQuits() {
        return getConfig().getStringList("halloween_quit");
    }




}
