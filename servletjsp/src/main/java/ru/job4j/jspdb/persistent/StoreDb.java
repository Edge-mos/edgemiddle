package ru.job4j.jspdb.persistent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.jspdb.SQLquery;
import ru.job4j.jspdb.config.Config;
import ru.job4j.jspdb.config.Settings;
import ru.job4j.jspdb.connectionspool.ConnectionsPool;
import ru.job4j.jspdb.interfaces.ConnectionBuilder;
import ru.job4j.jspdb.interfaces.Store;
import ru.job4j.jspdb.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class StoreDb implements Store<User> {
    private static final Logger LOGGER = LoggerFactory.getLogger(StoreDb.class);
    private final Config config = new Settings();
    private final ConnectionBuilder cb = new ConnectionsPool(this.config);
    private static final StoreDb INSTANCE = new StoreDb();

    public StoreDb() {
        this.init();
    }

    public static StoreDb getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean add(User user) {
        boolean result = false;
        try (Connection connection = this.cb.getConnection();
        PreparedStatement ps = connection.prepareStatement(SQLquery.addUser())) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getLogin());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getEmail());
            ps.setString(5, user.getRole());
            ps.setTimestamp(6, java.sql.Timestamp.valueOf(user.getCreateDate()));
            result = ps.executeUpdate() == 1;
        } catch (SQLException e) {
            LOGGER.error("ADD method problem!", e);
        }
        return result;
    }

    @Override
    public boolean update(int id, User user) {
        boolean result = false;
        try (Connection connection = this.cb.getConnection();
        PreparedStatement ps = connection.prepareStatement(SQLquery.updateUser())) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getLogin());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getEmail());
            ps.setString(5, user.getRole());
            ps.setInt(6, id);
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
             PreparedStatement ps = connection.prepareStatement(SQLquery.deleteUser())) {
            ps.setInt(1, id);
            result = ps.executeUpdate() == 1;
        } catch (SQLException e) {
            LOGGER.error("DELETE method problem!", e);
        }
        return result;
    }

    @Override
    public Map<Integer, User> findAll() {
        Map<Integer, User> result = new LinkedHashMap<>();
        try (Connection connection = this.cb.getConnection();
             ResultSet rs = connection.createStatement().executeQuery(SQLquery.getAllUsers())) {
            while (rs.next()) {
                result.put(rs.getInt("id"),
                        new User(
                        rs.getString("name"),
                        rs.getString("login"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getString("role"),
                        rs.getTimestamp("create_date").toLocalDateTime()
                ));
            }
        } catch (SQLException e) {
            LOGGER.error("FIND_ALL method problem!", e);
        }
        return result;
    }

    @Override
    public User findById(int id) {
        try (Connection connection = this.cb.getConnection();
        PreparedStatement ps = connection.prepareStatement(SQLquery.findById(),
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            rs.first();
            return new User(
                    rs.getString("name"),
                    rs.getString("login"),
                    rs.getString("password"),
                    rs.getString("email"),
                    rs.getString("role"),
                    rs.getTimestamp("create_date").toLocalDateTime());
        } catch (SQLException e) {
            LOGGER.error("FIND BY ID method problem!", e);
        }
        return null;
    }

    @Override
    public int authenticate(String login, String password) {
        try (Connection connection = this.cb.getConnection();
             PreparedStatement ps = connection.prepareStatement(SQLquery.auth(),
                     ResultSet.TYPE_SCROLL_SENSITIVE,
                     ResultSet.CONCUR_UPDATABLE)) {
            ps.setString(1, login);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.first()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            LOGGER.error("AUTHENTICATE method problem!", e);
        }
        return -1;
    }

    @Override
    public void init() {
        try (Connection connection = this.cb.getConnection()) {
            connection.createStatement().execute(SQLquery.init());
        } catch (SQLException e) {
            LOGGER.error("INIT method problem!", e);
        }
    }

    @Override
    public void drop() {
        try (Connection connection = this.cb.getConnection()){
            connection.createStatement().execute(SQLquery.dropTables());
        } catch (SQLException e) {
            LOGGER.error("DROP method problem!", e);
        }
    }

}
