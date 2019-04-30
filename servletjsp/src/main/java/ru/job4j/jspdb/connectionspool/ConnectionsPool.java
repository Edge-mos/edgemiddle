package ru.job4j.jspdb.connectionspool;

import org.apache.commons.dbcp2.BasicDataSource;
import ru.job4j.jspdb.config.Config;
import ru.job4j.jspdb.config.Settings;
import ru.job4j.jspdb.interfaces.ConnectionBuilder;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionsPool implements ConnectionBuilder {
    private final BasicDataSource source = new BasicDataSource();
    private final Config config;

    public ConnectionsPool(Config config) {
        this.config = config;
        this.source.setDriverClassName(this.config.getProperty(Settings.DRIVER));
        this.source.setUrl(this.config.getProperty(Settings.URL));
        this.source.setUsername(this.config.getProperty(Settings.LOGIN));
        this.source.setPassword(this.config.getProperty(Settings.PASSWORD));
        this.source.setMinIdle(Integer.parseInt(this.config.getProperty(Settings.MIN_IDLE)));
        this.source.setMaxIdle(Integer.parseInt(this.config.getProperty(Settings.MAX_IDLE)));
        this.source.setMaxOpenPreparedStatements(Integer.parseInt(this.config.getProperty(Settings.MAX_PREP_STATS)));
    }

    @Override
    public Connection getConnection() throws SQLException {
        return this.source.getConnection();
    }
}
