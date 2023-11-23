package com.biblioteca.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class DAOFactory {

    // Actions ------------------------------------------------------------------------------------

    /**
     * Returns a new DAOFactory instance for the given database name.
     * @return A new DAOFactory instance for the given database name.
     * @throws RuntimeException If the database name is null, or if the properties file is
     *                          missing in the classpath or cannot be loaded, or if a required property is missing in the
     *                          properties file, or if either the driver cannot be loaded or the datasource cannot be found.
     */
    public static DAOFactory getInstance() {
        String driverClassName = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/biblioteca";
        String password = "1234";
        String username = "root";
        DAOFactory instance;
        // If driver is specified, then load it to let it register itself with DriverManager.
            try {
                Class.forName(driverClassName);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(
                        "Driver class '" + driverClassName + "' is missing in classpath.", e);
            }
            return new DriverManagerDAOFactory(url, username, password);
    }

    /**
     * Returns a connection to the database. Package private so that it can be used inside the DAO
     * package only.
     *
     * @return A connection to the database.
     * @throws SQLException If acquiring the connection fails.
     */
    abstract Connection getConnection() throws SQLException;

    // DAO implementation getters -----------------------------------------------------------------

    /**
     * Returns the User DAO associated with the current DAOFactory.
     *
     * @return The User DAO associated with the current DAOFactory.
     */
    public CarrerasDAO getCarrerasDAO() {
        return new CarrerasDAOJDBC(this);
    }
//acceso a las tablas
    public AlumnoDAO getAlumnoDAO() {
        return new AlumnoDAOJDBC(this);
    }

    public GeneroDAO getGeneroDAO() {
        return new GeneroDAOJDBC(this);
    }

    public LibroDAO getLibroDAO() {
        return new LibroDAOJDBC(this);
    }

    public TicketDAO getTicketDAO() {
        return new TicketDAOJDBC(this);
    }

    // You can add more DAO implementation getters here.

}

// Default DAOFactory implementations -------------------------------------------------------------

/**
 * The DriverManager based DAOFactory.
 */
class DriverManagerDAOFactory extends DAOFactory {
    private String url;
    private String username;
    private String password;

    DriverManagerDAOFactory(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
}

