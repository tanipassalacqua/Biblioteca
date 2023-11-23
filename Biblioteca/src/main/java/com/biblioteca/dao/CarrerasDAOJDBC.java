package com.biblioteca.dao;

import com.biblioteca.models.Carreras;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.biblioteca.dao.DAOUtil.prepareStatement;


public class CarrerasDAOJDBC implements CarrerasDAO {
    private static final String SQL_FIND_BY_NAME =
            "SELECT IDCarrera FROM Carreras WHERE Nombre = ?";
    private static final String SQL_LIST_ORDER_BY_ID =
            "SELECT Nombre FROM Carreras ORDER BY IDCarrera";
    private static final String SQL_INSERT =
            "INSERT INTO carreras (Nombre) VALUES (?)";
    private static final String SQL_UPDATE =
            "UPDATE carreras SET IDCarrera = ?, Nombre = ? WHERE IDCarrera = ?;";
    private static final String SQL_DELETE =
            "DELETE FROM carreras WHERE ? ;";
    
    // Vars ---------------------------------------------------------------------------------------

    private DAOFactory daoFactory;

    // Constructors -------------------------------------------------------------------------------

    /**
     * Construct an Carreras DAO for the given DAOFactory. Package private so that it can be constructed
     * inside the DAO package only.
     * @param daoFactory The DAOFactory to construct this Carreras DAO for.
     */
    CarrerasDAOJDBC(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    // Actions ------------------------------------------------------------------------------------

    @Override
    public Carreras find(String Nombre) throws RuntimeException {
        return find(SQL_FIND_BY_NAME, Nombre);
    }

    /**
     * Returns the carreras from the database matching the given SQL query with the given values.
     * @param sql The SQL query to be executed in the database.
     * @param values The PreparedStatement values to be set.
     * @return The carreras from the database matching the given SQL query with the given values.
     * @throws RuntimeException If something fails at database level.
     */
    private Carreras find(String sql, Object... values) throws RuntimeException {
        Carreras carreras = null;

        try (
                Connection connection = daoFactory.getConnection();
                PreparedStatement statement = prepareStatement(connection, sql, false, values);
                ResultSet resultSet = statement.executeQuery();
        ) {
            if (resultSet.next()) {
                carreras = map(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return carreras;
    }

    @Override
    public List<Carreras> list() throws RuntimeException {
        List<Carreras> carreras = new ArrayList<>();

        try (
                Connection connection = daoFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_LIST_ORDER_BY_ID);
                ResultSet resultSet = statement.executeQuery();
        ) {
            while (resultSet.next()) {
                carreras.add(map(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return carreras;
    }

    @Override
    public void create(Carreras carreras) throws IllegalArgumentException, RuntimeException {
        if (null != carreras.getIDCarrera()) {
            throw new IllegalArgumentException("Carreras is already created, the carreras ID is not null.");
        }

        Object[] values = {
                carreras.getNombre()

        };

        try (
                Connection connection = daoFactory.getConnection();
                PreparedStatement statement = prepareStatement(connection, SQL_INSERT, true, values);
        ) {
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new RuntimeException("Creating carreras failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    carreras.setIDCarrera(generatedKeys.getLong(1));
                } else {
                    throw new RuntimeException("Creating carreras failed, no generated key obtained.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Carreras carreras) throws RuntimeException {
        if (carreras.getIDCarrera() == null) {
            throw new IllegalArgumentException("Carreras is not created yet, the carreras IDCarrera is null.");
        }

        Object[] values = {
                carreras.getNombre()
        };

        try (
                Connection connection = daoFactory.getConnection();
                PreparedStatement statement = prepareStatement(connection, SQL_UPDATE, false, values);
        ) {
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new RuntimeException("Updating carreras failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Carreras carreras) throws RuntimeException {
        if (carreras.getIDCarrera() == null) {
            throw new IllegalArgumentException("Carreras has already been deleted or does not exist, the carreras IDCarrera is null.");
        }
        Object[] values = {
                carreras.getNombre()
        };

        try (
                Connection connection = daoFactory.getConnection();
                PreparedStatement statement = prepareStatement(connection, SQL_DELETE, false, values);
        ) {
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new RuntimeException("Deleting carreras failed, no rows affected.");
            } else {
                carreras.setIDCarrera(null);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Helpers ------------------------------------------------------------------------------------

    /**
     * Map the current row of the given ResultSet to an Carreras.
     * @param resultSet The ResultSet of which the current row is to be mapped to an Carreras.
     * @return The mapped Carreras from the current row of the given ResultSet.
     * @throws SQLException If something fails at database level.
     */
    private static Carreras map(ResultSet resultSet) throws SQLException {
        Carreras carreras = new Carreras();
        carreras.setIDCarrera(resultSet.getLong("IDCarrera"));
        carreras.setNombre(resultSet.getString("Nombre"));
        return carreras;
    }
}
