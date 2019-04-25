package ru.job4j.jspdb.persistent;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.crud.model.User;
import ru.job4j.crud.persistent.Store;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public enum StoreDb implements Store {
    INSTANCE;
    private final BasicDataSource source = new BasicDataSource();
    private static final Logger LOGGER = LoggerFactory.getLogger(StoreDb.class);

    private StoreDb() {
        this.source.setDriverClassName("org.postgresql.Driver");
        this.source.setUrl("jdbc:postgresql://localhost:5433/users_jsp");
        this.source.setUsername("edge");
        this.source.setPassword("Assault");
        this.source.setMinIdle(100);
        this.source.setMaxIdle(200);
        this.source.setMaxOpenPreparedStatements(300);
    }

    @Override
    public boolean add(User user) {
        // метка
        boolean result = false;
        try (Connection connection = this.source.getConnection();
             PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO users_list(name, login, email, create_date) VALUES(?, ?, ?, ?)")) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getLogin());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getCreateDate());
            result = ps.executeUpdate() == 1;
            connection.close();
        } catch (SQLException e) {
            LOGGER.error("ADD method problem!", e);
        }
        return result;
    }

    @Override
    public boolean update(int id, User upd) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        // метка 2
        boolean result = false;
        try (Connection connection = this.source.getConnection();
             PreparedStatement ps = connection.prepareStatement(
                "DELETE FROM users_list WHERE id = ?")) {
            ps.setInt(1, id);
            result = ps.executeUpdate() == 1;
            connection.close();
        } catch (SQLException e) {
            LOGGER.error("DELETE method problem!", e);
        }
        return result;
    }

    @Override
    public Map<Integer, User> findAll() {
        Map<Integer, User> result = new HashMap<>();
        try (ResultSet rs = this.source.getConnection().createStatement().executeQuery("SELECT * FROM users_list")) {
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
        try (PreparedStatement ps = this.source.getConnection().prepareStatement(
                "SELECT * FROM users_list WHERE id = ?",
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
                rs.getString("create_date"));
    }
}
