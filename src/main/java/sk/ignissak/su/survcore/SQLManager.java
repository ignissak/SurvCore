package sk.ignissak.su.survcore;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;
import sk.ignissak.su.survcore.utils.BukkitSerialization;

import java.sql.*;
import java.util.ArrayList;

public class SQLManager {

    public void close(Connection conn, PreparedStatement ps, ResultSet res) {
        if (conn != null) try {
            conn.close();
        } catch (SQLException ignored) {
        }
        if (ps != null) try {
            ps.close();
        } catch (SQLException ignored) {
        }
        if (res != null) try {
            res.close();
        } catch (SQLException ignored) {
        }
    }

    public void createHomes() {
        Connection conn = null;
        PreparedStatement ps = null;
        try (Connection connection = Core.getInstance().getConnection();
             Statement statement = connection.createStatement();){
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS Surv_Homes(uuid TEXT(255), serverNick TEXT(255), loc_wo TEXT(255), loc_x DOUBLE, loc_y DOUBLE, loc_z DOUBLE, loc_yaw FLOAT, loc_pitch FLOAT  )");
            return;
        } catch (Exception e) {
            e.printStackTrace();
            return;
        } finally {
            close(conn, ps, null);
        }
    }

    public boolean hasHomeData(Player player) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = Core.getInstance().getConnection();
            ps = conn.prepareStatement("SELECT * FROM Surv_Homes WHERE serverNick = ?;");
            ps.setString(1, player.getName());
            ps.executeQuery();
            return ps.getResultSet().next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            close(conn, ps, null);
        }
    }

    public void instertHomeData(Player player) {
        new BukkitRunnable() {
            @Override
            public void run() {
                Connection conn = null;
                PreparedStatement ps = null;
                try {
                    conn = Core.getInstance().getConnection();
                    ps = conn.prepareStatement("INSERT INTO Surv_Homes (uuid, serverNick) VALUES (?, ?)");
                    ps.setString(1, player.getUniqueId().toString());
                    ps.setString(2, player.getName());
                    ps.executeUpdate();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    close(conn, ps, null);
                }
            }
        }.runTaskAsynchronously(Core.getInstance());
    }

    public void setHome(Player player, Location loc) {
        Connection conn = null;
        PreparedStatement ps = null;

        try (Connection connection = Core.getInstance().getConnection();
             Statement statement = connection.createStatement();){
            conn = Core.getInstance().getConnection();
            ps = conn.prepareStatement("UPDATE `Surv_Homes` SET `loc_x` = " + loc.getX() + ", `loc_y` = " + loc.getY() + ", `loc_z` = " + loc.getZ() + ", `loc_yaw` = " + loc.getYaw() + ", `loc_pitch` = " + loc.getPitch() + ", `loc_wo` = '" + loc.getWorld().getName() + "' WHERE serverNick = ?;");
            ps.setString(1, player.getName());
            ps.executeUpdate();
            close(conn, ps, null);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(conn, ps, null);
        }
    }

    public void deleteHome(Player player) {
        Connection conn = null;
        PreparedStatement ps = null;

        try (Connection connection = Core.getInstance().getConnection();
             Statement statement = connection.createStatement();){
            conn = Core.getInstance().getConnection();
            ps = conn.prepareStatement("UPDATE `Surv_Homes` SET `loc_wo` = NULL, `loc_x` = NULL, `loc_y` = NULL, `loc_z` = NULL, `loc_yaw` = NULL, `loc_pitch` = NULL WHERE serverNick = ?;");
            ps.setString(1, player.getName());
            ps.executeUpdate();
            close(conn, ps, null);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(conn, ps, null);
        }
    }

    public Location getHome(Player player) {
        Connection conn = null;
        PreparedStatement ps = null;
        Location loc = null;
        try {
            conn = Core.getInstance().getConnection();
            ps = conn.prepareStatement("SELECT * FROM Surv_Homes WHERE serverNick = ?;");
            ps.setString(1, player.getName());
            ps.executeQuery();
            ResultSet rs = ps.getResultSet();
            if (rs.next()) {
                if (rs.getString(3) != null) {
                    loc = new Location(Bukkit.getWorld(rs.getString(3)), rs.getDouble(4), rs.getDouble(5), rs.getDouble(6), rs.getFloat(7), rs.getFloat(8));
                }
            }
            return loc;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, ps, null);
        }
        return loc;
    }


    public void createPlaytimeTable() {
        Connection conn = null;
        PreparedStatement ps = null;
        try (Connection connection = Core.getInstance().getConnection();
             Statement statement = connection.createStatement();){
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS Playtime(uuid TEXT(255), serverNick TEXT(255), playTime BIGINT(100), joinTime BIGINT(100))");
            return;
        } catch (Exception e) {
            e.printStackTrace();
            return;
        } finally {
            close(conn, ps, null);
        }
    }

    public boolean hasPlaytimeData(Player player) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = Core.getInstance().getConnection();
            ps = conn.prepareStatement("SELECT * FROM Playtime WHERE serverNick = ?;");
            ps.setString(1, player.getName().toString());
            ps.executeQuery();
            return ps.getResultSet().next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            close(conn, ps, null);
        }
    }

    public void insertPlaytimeData(Player player) {
        new BukkitRunnable() {
            @Override
            public void run() {
                Connection conn = null;
                PreparedStatement ps = null;
                try {
                    conn = Core.getInstance().getConnection();
                    ps = conn.prepareStatement("INSERT INTO Playtime (uuid, serverNick, playTime, joinTime) VALUES (?, ?, 0, " + System.currentTimeMillis()+ ");");
                    ps.setString(1, player.getUniqueId().toString());
                    ps.setString(2, player.getName());
                    ps.executeUpdate();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    close(conn, ps, null);
                }
            }
        }.runTaskAsynchronously(Core.getInstance());
    }

    public void addPlaytimeData(Player player, Long count) {
        Connection conn = null;
        PreparedStatement ps = null;
        try (Connection connection = Core.getInstance().getConnection();
             Statement statement = connection.createStatement();){
            conn = Core.getInstance().getConnection();
            ps = conn.prepareStatement("UPDATE `Playtime` SET `playTime` = `playTime` + " + count + " WHERE serverNick = ?;");
            ps.setString(1, player.getName());
            ps.executeUpdate();
            close(conn, ps, null);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(conn, ps, null);
        }
    }

    public void setPlaytimeData(String player, Long count) {
        Connection conn = null;
        PreparedStatement ps = null;
        try (Connection connection = Core.getInstance().getConnection();
             Statement statement = connection.createStatement();){
            conn = Core.getInstance().getConnection();
            ps = conn.prepareStatement("UPDATE `Playtime` SET `playTime` = " + count + " WHERE serverNick = ?;");
            ps.setString(1, player);
            ps.executeUpdate();
            close(conn, ps, null);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(conn, ps, null);
        }
    }

    public void setPlaytimeJoinData(Player player, Long time) {
        Connection conn = null;
        PreparedStatement ps = null;
        try (Connection connection = Core.getInstance().getConnection();
             Statement statement = connection.createStatement();){
            conn = Core.getInstance().getConnection();
            ps = conn.prepareStatement("UPDATE `Playtime` SET `joinTime` = " + time + " WHERE serverNick = ?;");
            ps.setString(1, player.getName());
            ps.executeUpdate();
            close(conn, ps, null);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(conn, ps, null);
        }
    }

    public long getPlaytimeData(String player) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = Core.getInstance().getConnection();
            ps = conn.prepareStatement("SELECT playTime FROM Playtime WHERE serverNick = ?;");
            ps.setString(1, player);
            ps.executeQuery();
            if (ps.getResultSet().next()) {
                return ps.getResultSet().getLong("playTime");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, ps, null);
        }
        return 0;
    }

    public long getPlaytimeJoinData(String player) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = Core.getInstance().getConnection();
            ps = conn.prepareStatement("SELECT joinTime FROM Playtime WHERE serverNick = ?;");
            ps.setString(1, player);
            ps.executeQuery();
            if (ps.getResultSet().next()) {
                return ps.getResultSet().getLong("joinTime");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, ps, null);
        }
        return 0;
    }

    public boolean hasRequestAlready(Player player) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = Core.getInstance().getConnection();
            ps = conn.prepareStatement("SELECT * FROM DiscordConnection WHERE serverNick = ? AND discordID IS NULL;");
            ps.setString(1, player.getName());
            ps.executeQuery();
            return ps.getResultSet().next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            close(conn, ps, null);
        }
    }

    public boolean isVerifiedAlready(Player player) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = Core.getInstance().getConnection();
            ps = conn.prepareStatement("SELECT * FROM DiscordConnection WHERE serverNick = ? AND discordID IS NOT NULL;");
            ps.setString(1, player.getName());
            ps.executeQuery();
            return ps.getResultSet().next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            close(conn, ps, null);
        }
    }

    public void createConnectionRequest(Player player) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = Core.getInstance().getConnection();
            ps = conn.prepareStatement("INSERT INTO DiscordConnection (serverNick) VALUES (?);");
            ps.setString(1, player.getName());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, ps, null);
        }
    }

    public void unlinkAccount(Player player) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = Core.getInstance().getConnection();
            ps = conn.prepareStatement("DELETE FROM DiscordConnection WHERE serverNick = ?");
            ps.setString(1, player.getName());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, ps, null);
        }
    }

    //Inventories

    public boolean hasInventoryData(Player player) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = Core.getInstance().getConnection();
            ps = conn.prepareStatement("SELECT * FROM Surv_Inventory WHERE serverNick = ?;");
            ps.setString(1, player.getName().toString());
            ps.executeQuery();
            return ps.getResultSet().next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            close(conn, ps, null);
        }
    }

    public void insertInventoryData(Player player) {
        new BukkitRunnable() {
            @Override
            public void run() {
                Connection conn = null;
                PreparedStatement ps = null;
                try {
                    conn = Core.getInstance().getConnection();
                    ps = conn.prepareStatement("INSERT INTO Surv_Inventory (uuid, serverNick) VALUES (?, ?);");
                    ps.setString(1, player.getUniqueId().toString());
                    ps.setString(2, player.getName());
                    ps.executeUpdate();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    close(conn, ps, null);
                }
            }
        }.runTaskAsynchronously(Core.getInstance());
    }

    public void setInventory(Player player, String inv) {
        Connection conn = null;
        PreparedStatement ps = null;
            try (Connection connection = Core.getInstance().getConnection();
                 Statement statement = connection.createStatement();){
                conn = Core.getInstance().getConnection();
                ps = conn.prepareStatement("UPDATE Surv_Inventory SET inventory = ? WHERE serverNick = ?;");
                ps.setString(1, inv);
                ps.setString(2, player.getName());
                ps.executeUpdate();
                Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "Inventár hráča " + player.getName() + " bol aktualizovaný.");
                close(conn, ps, null);
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                close(conn, ps, null);
            }
    }

    //LOGS
    //todo

    public boolean hasLogData(String player) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = Core.getInstance().getConnection();
            ps = conn.prepareStatement("SELECT * FROM Surv_Logs WHERE serverNick = ?;");
            ps.setString(1, player);
            ps.executeQuery();
            return ps.getResultSet().next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            close(conn, ps, null);
        }
    }

    public void insertLogData(Player player) {
        new BukkitRunnable() {
            @Override
            public void run() {
                Connection conn = null;
                PreparedStatement ps = null;
                try {
                    conn = Core.getInstance().getConnection();
                    ps = conn.prepareStatement("INSERT INTO Surv_Logs (uuid, serverNick) VALUES (?, ?);");
                    ps.setString(1, player.getUniqueId().toString());
                    ps.setString(2, player.getName());
                    ps.executeUpdate();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    close(conn, ps, null);
                }
            }
        }.runTaskAsynchronously(Core.getInstance());
    }

    public void setLogJoin(String player, Long l) {
        Connection conn = null;
        PreparedStatement ps = null;
        try (Connection connection = Core.getInstance().getConnection();
             Statement statement = connection.createStatement();){
            conn = Core.getInstance().getConnection();
            ps = conn.prepareStatement("UPDATE Surv_Logs SET lastJoin = ? WHERE serverNick = ?;");
            ps.setLong(1, l);
            ps.setString(2, player);
            ps.executeUpdate();
            close(conn, ps, null);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(conn, ps, null);
        }
    }

    public void setLogQuit(String player, Long l) {
        Connection conn = null;
        PreparedStatement ps = null;
        try (Connection connection = Core.getInstance().getConnection();
             Statement statement = connection.createStatement();){
            conn = Core.getInstance().getConnection();
            ps = conn.prepareStatement("UPDATE Surv_Logs SET lastLogout = ? WHERE serverNick = ?;");
            ps.setLong(1, l);
            ps.setString(2, player);
            ps.executeUpdate();
            close(conn, ps, null);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(conn, ps, null);
        }
    }

    public long getLogJoinData(String player) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = Core.getInstance().getConnection();
            ps = conn.prepareStatement("SELECT lastJoin FROM Surv_Logs WHERE serverNick = ?;");
            ps.setString(1, player);
            ps.executeQuery();
            if (ps.getResultSet().next()) {
                return ps.getResultSet().getLong("lastJoin");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, ps, null);
        }
        return 0;
    }

    public long getLogQuitData(String player) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = Core.getInstance().getConnection();
            ps = conn.prepareStatement("SELECT lastLogout FROM Surv_Logs WHERE serverNick = ?;");
            ps.setString(1, player);
            ps.executeQuery();
            if (ps.getResultSet().next()) {
                return ps.getResultSet().getLong("lastLogout");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, ps, null);
        }
        return 0;
    }


}
