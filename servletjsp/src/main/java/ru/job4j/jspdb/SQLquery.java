package ru.job4j.jspdb;

public interface SQLquery {

    static String init() {
        return "CREATE OR REPLACE FUNCTION init_userRole()" +
                "RETURNS VOID AS $res$" +
                "  DECLARE table_exists VARCHAR(100);" +
                "  BEGIN" +
                "      table_exists = (SELECT to_regclass('user_role'));" +
                "      if table_exists isnull then" +
                "        CREATE TABLE user_role(id BIGSERIAL PRIMARY KEY, role TEXT UNIQUE NOT NULL);" +
                "        INSERT INTO user_role(role) VALUES ('ADMIN'), ('USER');" +
                "      end if;" +
                "  END ;" +
                "  $res$ LANGUAGE plpgsql;" 

                +

                "SELECT init_userRole();" +

                "CREATE TABLE IF NOT EXISTS users_list (" +
                "  id BIGSERIAL PRIMARY KEY," +
                "  name TEXT," +
                "  login TEXT UNIQUE," +
                "  password TEXT," +
                "  email TEXT UNIQUE," +
                "  role INT NOT NULL REFERENCES user_role(id) ON DELETE CASCADE," +
                "  create_date TIMESTAMP," +
                "  CHECK ((name !='') AND (login !='') AND (password !='') AND (email !=''))" +
                ");";
    }

    static String dropTables() {
        return "DROP TABLE IF EXISTS users_list;" +
                "DROP TABLE IF EXISTS user_role;";
    }

    static String addUser() {
        return "INSERT INTO users_list(name, login, password, email, role, create_date)" +
                "VALUES (?, ?, ?, ?, (SELECT id FROM user_role as ur WHERE ur.role = ?), ?);";
    }

    static String updateUser() {
        return "UPDATE users_list\n" +
                "SET name = ?, login = ?, password = ?, email = ?, role = (SELECT id FROM user_role AS ur WHERE ur.role = ?)\n" +
                "WHERE id = ?;";
    }

    static String deleteUser() {
        return "DELETE FROM users_list\n" +
                "WHERE id = ?;";
    }

    static String getAllUsers() {
        return "SELECT ul.id, ul.name, ul.login, ul.password, ul.email, ur.role, ul.create_date\n" +
                "FROM users_list AS ul\n" +
                "INNER JOIN user_role AS ur\n" +
                "ON ul.role = ur.id\n" +
                "ORDER BY ul.id ASC;";
    }

    static String findById() {
        return "SELECT ul.id, ul.name, ul.login, ul.password, ul.email, ur.role, ul.create_date\n" +
                "FROM users_list as ul\n" +
                "       INNER JOIN\n" +
                "       user_role as ur\n" +
                "       ON ul.role = ur.id\n" +
                "WHERE ul.id = ?";
    }

    static String auth() {
        return "SELECT ul.id, ul.name, ul.login, ul.password, ul.email, ur.role, ul.create_date\n" +
                "FROM users_list AS ul\n" +
                "INNER JOIN user_role AS ur\n" +
                "ON ul.role = ur.id\n" +
                "WHERE ul.login = ? AND ul.password = ?;";
    }
}
