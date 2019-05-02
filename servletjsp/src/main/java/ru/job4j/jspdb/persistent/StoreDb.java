package ru.job4j.jspdb.persistent;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.crud.model.User;
import ru.job4j.crud.persistent.Store;
import ru.job4j.jspdb.config.Config;
import ru.job4j.jspdb.config.Settings;
import ru.job4j.jspdb.connectionspool.ConnectionsPool;
import ru.job4j.jspdb.interfaces.ConnectionBuilder;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public enum StoreDb implements Store {
    INSTANCE;
    private static final Logger LOGGER = LoggerFactory.getLogger(StoreDb.class);
    private final Config config = new Settings();
    private final ConnectionBuilder cb = new ConnectionsPool(this.config);

    StoreDb() {
        this.initTable();
    }

    public void initTable() {
        try (Connection connection = this.cb.getConnection()){
            connection.createStatement().execute(this.config.getProperty(Settings.INITIAL));
        } catch (SQLException e) {
            LOGGER.error("INIT TABLE method problem!", e);
        }
    }

    public void dropTable() {
        try (Connection connection = this.cb.getConnection()){
            connection.createStatement().execute(this.config.getProperty(Settings.DROP));
        } catch (SQLException e) {
            LOGGER.error("DROP TABLE method problem", e);
        }
    }

    @Override
    public boolean add(User user) {
        boolean result = false;
        try (Connection connection = this.cb.getConnection();
             PreparedStatement ps = connection.prepareStatement(this.config.getProperty(Settings.INSERT))) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getLogin());
            ps.setString(3, user.getEmail());
            ps.setTimestamp(4, java.sql.Timestamp.valueOf(LocalDateTime.now()));
            result = ps.executeUpdate() == 1;
        } catch (SQLException e) {
            LOGGER.error("ADD method problem!", e);
        }
        return result;
    }

    @Override
    public boolean update(int id, User upd) {
        boolean result = false;
        try (Connection connection = this.cb.getConnection();
             PreparedStatement ps = connection.prepareStatement(this.config.getProperty(Settings.UPDATE))
        ) {
            ps.setString(1, upd.getName());
            ps.setString(2, upd.getLogin());
            ps.setString(3, upd.getEmail());
            ps.setInt(4, id);
            result = ps.executeUpdate() == 1;
        } catch (SQLException e) {
            LOGGER.error("UPDATE method problem!", e);
        }
        return result;
    }

    @Override
    public boolean delete(int id) {
        boolean result = false;
        try (Connection connection = this.cb.getConnection();
             PreparedStatement ps = connection.prepareStatement(this.config.getProperty(Settings.DELETE))) {
            ps.setInt(1, id);
            result = ps.executeUpdate() == 1;
        } catch (SQLException e) {
            LOGGER.error("DELETE method problem!", e);
        }
        return result;
    }

    @Override
    public Map<Integer, User> findAll() {
        Map<Integer, User> result = new HashMap<>();
        try (Connection connection = this.cb.getConnection();
             ResultSet rs = connection.createStatement().executeQuery(this.config.getProperty(Settings.GETALL))) {
            while (rs.next()) {
                result.put(rs.getInt("id"), this.buildUser(rs));
            }
        } catch (SQLException e) {
            LOGGER.error("FIND_ALL method problem!", e);
        }
        return result;
    }

    @Override
    public User findById(int id) {
        try (Connection connection = this.cb.getConnection();
             PreparedStatement ps = connection.prepareStatement(
                this.config.getProperty(Settings.FIND),
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            rs.first();
            return this.buildUser(rs);
        } catch (SQLException e) {
            LOGGER.error("FIND_BY_ID method problem!", e);
        }
        return null;
    }

    private User buildUser(ResultSet rs) throws SQLException{
        return new User(rs.getString("name"),
                rs.getString("login"),
                rs.getString("email"),
                rs.getString("create_date").substring(0, 19));
    }
}
