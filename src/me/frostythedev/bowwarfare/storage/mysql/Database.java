package me.frostythedev.bowwarfare.storage.mysql;

import me.frostythedev.bowwarfare.BWPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.*;
import java.util.logging.Level;

/**
 * Programmed by Tevin on 7/8/2016.
 */
public class Database {

    final String username="hive_admin"; //Enter in your db username
    final String password="Homeworks1"; //Enter your password for the db
    final String url = "jdbc:mysql://db4free.net:3306/hive_test"; //Enter URL w/db name

    private Connection connection;

    private static Database instance;

    public Database() {
        openConnection();

    }

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    public boolean checkConnection() {
        try {
            return getConnection() != null && !getConnection().isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean openConnection() {
        if (!checkConnection()) {
            synchronized (this) {
                try { //We use a try catch to avoid errors, hopefully we don't get any.
                    Class.forName("com.mysql.jdbc.Driver"); //this accesses Driver in jdbc.
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                    System.err.println("jdbc driver unavailable!");
                    return false;
                }
                try { //Another try catch to get any SQL errors (for example connections errors)
                    connection = DriverManager.getConnection(url,username,password);
                    //with the method getConnection() from DriverManager, we're trying to set
                    //the connection's url, username, password to the variables we made earlier and
                    //trying to get a connection at the same time. JDBC allows us to do this.
                    return true;
                } catch (SQLException e) { //catching errors)
                    e.printStackTrace(); //prints out SQLException errors to the console (if any)
                }
            }
        }
        return false;
    }

    public void closeConnection() {
        if (checkConnection()) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            connection = null;
        }
    }

    public void syncQuery(String query, Object[] parameters, Callback callback) {
        PreparedStatement stat = null;
        ResultSet rs = null;
        try {;
            stat =getConnection().prepareStatement(query);
            injectParameters(stat, parameters);
            stat.execute();
            rs = stat.getResultSet();
            callback.read(rs);
            callback.digestSync();
        } catch (SQLException e) {
            e.printStackTrace();
            BWPlugin.getInstance().getLogger().log(Level.SEVERE, e.getMessage());
            try {
                BWPlugin.getInstance().getLogger().log(Level.SEVERE, stat.toString());
            } catch (Exception e1) {
            }
        } finally {
            tryClose(rs);
            tryClose(stat);
        }
    }

    /**
     * @param query
     * @param parameters
     * @param callback
     */
    public void asyncQueryCallback(final String query, final Object[] parameters, final Callback callback) {
        new BukkitRunnable() {
            public void run() {
                PreparedStatement stat = null;
                ResultSet rs = null;
                try {
                    stat =getConnection().prepareStatement(query);
                    injectParameters(stat, parameters);
                    stat.execute();
                    rs = stat.getResultSet();
                    callback.read(rs);
                    callback.digestAsync();
                    new BukkitRunnable() {
                        public void run() {
                            callback.digestSync();
                        }
                    }.runTask(BWPlugin.getInstance());
                } catch (SQLException e) {
                    e.printStackTrace();
                    BWPlugin.getInstance().getLogger().log(Level.SEVERE, e.getMessage());
                } finally {
                    tryClose(rs);
                    tryClose(stat);
                }
            }
        }.runTaskAsynchronously(BWPlugin.getInstance());
    }

    /**
     * @param query
     * @param parameters
     * @param callback
     */
    public void asyncSyncQuery(String query, Object[] parameters, Callback callback) {
        PreparedStatement stat = null;
        ResultSet rs = null;
        try {
            stat =getConnection().prepareStatement(query);
            injectParameters(stat, parameters);
            stat.execute();
            rs = stat.getResultSet();
            callback.read(rs);
            callback.digestAsync();
        } catch (SQLException e) {
            e.printStackTrace();
            BWPlugin.getInstance().getLogger().log(Level.SEVERE, e.getMessage());
            try {
                BWPlugin.getInstance().getLogger().log(Level.SEVERE, stat.toString());
            } catch (Exception e1) {
            }
        } finally {
            tryClose(rs);
            tryClose(stat);
        }
    }

    /**
     * @param query
     * @param parameters
     * @return integer
     */
    public int syncUpdate(final String query, final Object[] parameters) {
       // Connection con = null;
        PreparedStatement stat = null;
        try {
            //con = getConnection();
            stat = getConnection().prepareStatement(query);
            injectParameters(stat, parameters);
            return stat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            BWPlugin.getInstance().getLogger().log(Level.SEVERE, e.getMessage());
            try {
                BWPlugin.getInstance().getLogger().log(Level.SEVERE, stat.toString());
            } catch (Exception e1) {
            }
        } finally {
            tryClose(stat);
            //tryClose(con);
        }
        return -1;
    }

    /**
     * @param query
     * @param parameters
     */
    public void asyncUpdate(final String query, final Object[] parameters) {
        new BukkitRunnable() {
            public void run() {
                PreparedStatement stat = null;
                try {
                    stat = getConnection().prepareStatement(query);
                    injectParameters(stat, parameters);
                    stat.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                    BWPlugin.getInstance().getLogger().log(Level.SEVERE, e.getMessage());
                    try {
                        BWPlugin.getInstance().getLogger().log(Level.SEVERE, stat.toString());
                    } catch (Exception e1) {
                    }
                } finally {
                    tryClose(stat);
                }
            }
        }.runTaskAsynchronously(BWPlugin.getInstance());
    }

    /**
     * @param query
     * @param parameters
     * @return integer
     */
    public int asyncSyncUpdate(String query, final Object[] parameters) {
        PreparedStatement stat = null;
        try {
            stat =getConnection().prepareStatement(query);
            injectParameters(stat, parameters);
            return stat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            BWPlugin.getInstance().getLogger().log(Level.SEVERE, e.getMessage());
            try {
                BWPlugin.getInstance().getLogger().log(Level.SEVERE, stat.toString());
            } catch (Exception e1) {
            }
        } finally {
            tryClose(stat);
        }
        return -1;
    }

    /**
     * @param query
     * @param parameters
     * @return integer
     */
    public int syncInsert(final String query, final Object[] parameters) {
        PreparedStatement stat = null;
        ResultSet rs = null;
        try {
            stat = getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            injectParameters(stat, parameters);
            rs = stat.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
            BWPlugin.getInstance().getLogger().log(Level.SEVERE, e.getMessage());
            try {
                BWPlugin.getInstance().getLogger().log(Level.SEVERE, stat.toString());
            } catch (Exception e1) {
            }
        } finally {
            tryClose(rs);
            tryClose(stat);
        }
        return -1;
    }

    /**
     * @param query
     * @param parameters
     * @return integer
     */
    public int asyncSyncInsert(String query, final Object[] parameters) {
        PreparedStatement stat = null;
        ResultSet rs = null;
        try {
            stat = getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            injectParameters(stat, parameters);
            rs = stat.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
            BWPlugin.getInstance().getLogger().log(Level.SEVERE, e.getMessage());
            try {
                BWPlugin.getInstance().getLogger().log(Level.SEVERE, stat.toString());
            } catch (Exception e1) {
            }
        } finally {
            tryClose(rs);
            tryClose(stat);
        }
        return -1;
    }

    /**
     * @param tableName
     * @param fields
     */
    public void createTable(String tableName, String fields) {
        syncUpdate("CREATE TABLE IF NOT EXISTS " + tableName + " (" + fields + ")", null);
    }

    /**
     * @param stat
     * @param parameters
     * @throws SQLException
     */
    private void injectParameters(PreparedStatement stat, Object[] parameters) throws SQLException {
        if (parameters != null) {
            for (int i = 0; i < parameters.length; i++) {
                Object param = parameters[i];
                if (param instanceof String) {
                    stat.setString((i + 1), String.valueOf(param));
                } else if (param instanceof Integer) {
                    stat.setInt((i + 1), (Integer) param);
                } else if (param instanceof Long) {
                    stat.setLong((i + 1), (Long) param);
                } else if (param instanceof Timestamp) {
                    stat.setTimestamp((i + 1), (Timestamp) param);
                } else if (param instanceof Boolean) {
                    stat.setBoolean((i + 1), (Boolean) param);
                }
            }
        }
    }

    private void tryClose(AutoCloseable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
            }
        }
    }
}
