package edu.dartit.warehouseapp.utils;

/**
 * Created by vysokov-mg on 15.06.2018.
 */
public class PostgresDAOFactory {

    private PostgresDAOFactory() {
    }

    private static class Holder {
        static final PostgresDAOFactory instance = new PostgresDAOFactory();
    }

    public static PostgresDAOFactory getInstance() {
        return Holder.instance;
    }

    public UserDAO createUserDAO() {
        return null;
    }
}
