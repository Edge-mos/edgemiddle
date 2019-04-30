package ru.job4j.jspdb.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Settings implements Config{
    private static final Logger LOGGER = LoggerFactory.getLogger(Config.class);
    private Properties properties = new Properties();
    public static final String DRIVER = "db.driver";
    public static final String URL = "db.url";
    public static final String LOGIN = "db.login";
    public static final String PASSWORD = "db.password";
    public static final String MIN_IDLE = "pool.minIdle";
    public static final String MAX_IDLE = "pool.maxIdle";
    public static final String MAX_PREP_STATS = "pool.maxOpenPreparedStatements";
    public static final String INITIAL = "db.initial";
    public static final String DROP = "db.drop";
    public static final String INSERT = "db.insert";
    public static final String UPDATE = "db.update";
    public static final String DELETE = "db.delete";
    public static final String GETALL = "db.getAll";
    public static final String FIND = "db.find";

    @Override
    public synchronized String getProperty(String arg) {
        if (this.properties.isEmpty()) {
            try (InputStream is = Settings.class.getClassLoader().getResourceAsStream("app.properties")){
                this.properties.load(is);
            } catch (IOException e) {
                LOGGER.error("Config problem!", e);
            }
        }
        return this.properties.getProperty(arg);
    }
}
