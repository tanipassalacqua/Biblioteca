package com.biblioteca.dao;

import com.biblioteca.models.Genero;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.biblioteca.dao.DAOUtil.prepareStatement;

public class GeneroDAOJDBC implements GeneroDAO{
    private static final String SQL_FIND_BY_NAME =
            "SELECT IDGenero FROM Genero WHERE Nombre = ?";
    private static final String SQL_LIST_ORDER_BY_ID =
            "SELECT IDGenero FROM Genero ORDER BY IDGenero";
    private static final String SQL_INSERT =
            "INSERT INTO genero (Nombre) VALUES (?)";
    private static final String SQL_UPDATE =
            "UPDATE genero SET IDGenero = ?, Nombre = ? WHERE IDGenero = ?;";
    private static final String SQL_DELETE =
            "DELETE FROM genero WHERE ? ;";

    // Vars ---------------------------------------------------------------------------------------

    private DAOFactory daoFactory;

    // Constructors -------------------------------------------------------------------------------

    /**
     * Construct an Genero DAO for the given DAOFactory. Package private so that it can be constructed
     * inside the DAO package only.
     * @param daoFactory The DAOFactory to construct this Genero DAO for.
     */
    GeneroDAOJDBC(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    // Actions ------------------------------------------------------------------------------------

    @Override
    public Genero find(String Nombre) throws RuntimeException {
        return find(SQL_FIND_BY_NAME, Nombre);
    }

    /**
     * Returns the genero from the database matching the given SQL query with the given values.
     * @param sql The SQL query to be executed in the database.
     * @param values The PreparedStatement values to be set.
     * @return The genero from the database matching the given SQL query with the given values.
     * @throws RuntimeException If something fails at database level.
     */
    private Genero find(String sql, Object... values) throws RuntimeException {
        Genero genero = null;

        try (
                Connection connection = daoFactory.getConnection();
                PreparedStatement statement = prepareStatement(connection, sql, false, values);
                ResultSet resultSet = statement.executeQuery();
        ) {
            if (resultSet.next()) {
                genero = map(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return genero;
    }

    @Override
    public List<Genero> list() throws RuntimeException {
        List<Genero> genero = new ArrayList<>();

        try (
                Connection connection = daoFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_LIST_ORDER_BY_ID);
                ResultSet resultSet = statement.executeQuery();
        ) {
            while (resultSet.next()) {
                genero.add(map(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return genero;
    }

    @Override
    public void create(Genero genero) throws IllegalArgumentException, RuntimeException {
        if (null != genero.getIDGenero()) {
            throw new IllegalArgumentException("Genero is already created, the genero ID is not null.");
        }

        Object[] values = {
                genero.getNombre()

        };

        try (
                Connection connection = daoFactory.getConnection();
                PreparedStatement statement = prepareStatement(connection, SQL_INSERT, true, values);
        ) {
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new RuntimeException("Creating genero failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    genero.setIDGenero(generatedKeys.getLong(1));
                } else {
                    throw new RuntimeException("Creating genero failed, no generated key obtained.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Genero genero) throws RuntimeException {
        if (genero.getIDGenero() == null) {
            throw new IllegalArgumentException("Genero is not created yet, the genero IDGenero is null.");
        }

        Object[] values = {
                genero.getNombre()
        };

        try (
                Connection connection = daoFactory.getConnection();
                PreparedStatement statement = prepareStatement(connection, SQL_UPDATE, false, values);
        ) {
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new RuntimeException("Updating genero failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Genero genero) throws RuntimeException {
        if (genero.getIDGenero() == null) {
            throw new IllegalArgumentException("Genero has already been deleted or does not exist, the genero IDGenero is null.");
        }
        Object[] values = {
                genero.getNombre()
        };

        try (
                Connection connection = daoFactory.getConnection();
                PreparedStatement statement = prepareStatement(connection, SQL_DELETE, false, values);
        ) {
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new RuntimeException("Deleting genero failed, no rows affected.");
            } else {
                genero.setIDGenero(null);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Helpers ------------------------------------------------------------------------------------

    /**
     * Map the current row of the given ResultSet to an Genero.
     * @param resultSet The ResultSet of which the current row is to be mapped to an Genero.
     * @return The mapped Genero from the current row of the given ResultSet.
     * @throws SQLException If something fails at database level.
     */
    private static Genero map(ResultSet resultSet) throws SQLException {
        Genero genero = new Genero();
        genero.setIDGenero(resultSet.getLong("IDGenero"));
        genero.setNombre(resultSet.getString("Nombre"));
        return genero;
    }
}
