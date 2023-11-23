package com.biblioteca.dao;

import com.biblioteca.models.Alumno;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.biblioteca.dao.DAOUtil.*;
public class AlumnoDAOJDBC implements AlumnoDAO{
    // Constants ----------------------------------------------------------------------------------

    private static final String SQL_FIND_BY_ID =
            "SELECT Nombre FROM Alumno WHERE legajo = ?";
    private static final String SQL_LIST_ORDER_BY_ID =
            "SELECT Legajo, Nombre, Apellido, FechaCumpleanios, Edad, Email, IDCarrera FROM Alumno ORDER BY Legajo";
    private static final String SQL_INSERT =
            "INSERT INTO alumnos"+
                    "(Nombre," +
                    "Apellido," +
                    "FechaCumpleanios," +
                    "Edad," +
                    "Email," +
                    "IDCarrera)" +
                    "VALUES" +
                    "(?, ?, ?, ?, ?, ?);";
    private static final String SQL_UPDATE =
            "UPDATE Alumno SET Nombre = ?, Apellido = ?, FechaCumpleanios = ?, Edad = ?, Email = ?, IDCarrera = ? WHERE legajo = ?";
    private static final String SQL_DELETE =
            "DELETE FROM Alumno WHERE legajo = ?";
    private static final String SQL_EXIST_EMAIL =
            "SELECT Legajo FROM Alumno WHERE email = ?";
    // Vars ---------------------------------------------------------------------------------------

    private DAOFactory daoFactory;

    // Constructors -------------------------------------------------------------------------------

    /**
     * Construct an Alumno DAO for the given DAOFactory. Package private so that it can be constructed
     * inside the DAO package only.
     * @param daoFactory The DAOFactory to construct this Alumno DAO for.
     */
    AlumnoDAOJDBC(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    // Actions ------------------------------------------------------------------------------------

    @Override
    public Alumno find(Long legajo) throws RuntimeException {
        return find(SQL_FIND_BY_ID, legajo);
    }

    /**
     * Returns the alumno from the database matching the given SQL query with the given values.
     * @param sql The SQL query to be executed in the database.
     * @param values The PreparedStatement values to be set.
     * @return The alumno from the database matching the given SQL query with the given values.
     * @throws RuntimeException If something fails at database level.
     */
    private Alumno find(String sql, Object... values) throws RuntimeException {
        Alumno alumno = null;

        try (
                Connection connection = daoFactory.getConnection();
                PreparedStatement statement = prepareStatement(connection, sql, false, values);
                ResultSet resultSet = statement.executeQuery();
        ) {
            if (resultSet.next()) {
                alumno = map(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return alumno;
    }

    @Override
    public List<Alumno> list() throws RuntimeException {
        List<Alumno> alumnos = new ArrayList<>();

        try (
                Connection connection = daoFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_LIST_ORDER_BY_ID);
                ResultSet resultSet = statement.executeQuery();
        ) {
            while (resultSet.next()) {
                alumnos.add(map(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return alumnos;
    }

    @Override
    public void create(Alumno alumno) throws IllegalArgumentException, RuntimeException {
        if (null != alumno.getLegajo()) {
            throw new IllegalArgumentException("Alumno is already created, the alumno ID is not null.");
        }

        Object[] values = {
                alumno.getNombre(),
                alumno.getApellido(),
                toSqlDate(alumno.getFechaCumpleanios()),
                alumno.getEdad(),
                alumno.getEmail(),
                alumno.getIdCarrera()

        };

        try (
                Connection connection = daoFactory.getConnection();
                PreparedStatement statement = prepareStatement(connection, SQL_INSERT, true, values);
        ) {
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new RuntimeException("Creating alumno failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    alumno.setLegajo(generatedKeys.getLong(1));
                } else {
                    throw new RuntimeException("Creating alumno failed, no generated key obtained.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Alumno alumno) throws RuntimeException {
        if (alumno.getLegajo() == null) {
            throw new IllegalArgumentException("Alumno is not created yet, the alumno Legajo is null.");
        }

        Object[] values = {
                alumno.getNombre(),
                alumno.getApellido(),
                toSqlDate(alumno.getFechaCumpleanios()),
                alumno.getEdad(),
                alumno.getEmail(),
                alumno.getIdCarrera()
        };

        try (
                Connection connection = daoFactory.getConnection();
                PreparedStatement statement = prepareStatement(connection, SQL_UPDATE, false, values);
        ) {
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new RuntimeException("Updating alumno failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Alumno alumno) throws RuntimeException {
        if (alumno.getLegajo() == null) {
            throw new IllegalArgumentException("Alumno has already been deleted or does not exist, the alumno Legajo is null.");
        }
        Object[] values = {
                alumno.getNombre(),
                alumno.getApellido(),
                toSqlDate(alumno.getFechaCumpleanios()),
                alumno.getEdad(),
                alumno.getEmail(),
                alumno.getIdCarrera()
        };

        try (
                Connection connection = daoFactory.getConnection();
                PreparedStatement statement = prepareStatement(connection, SQL_DELETE, false, values);
        ) {
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new RuntimeException("Deleting alumno failed, no rows affected.");
            } else {
                alumno.setLegajo(null);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean existEmail(String email) throws RuntimeException {
        Object[] values = {
                email
        };

        boolean exist = false;

        try (
                Connection connection = daoFactory.getConnection();
                PreparedStatement statement = prepareStatement(connection, SQL_EXIST_EMAIL, false, values);
                ResultSet resultSet = statement.executeQuery();
        ) {
            exist = resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return exist;
    }

    // Helpers ------------------------------------------------------------------------------------

    /**
     * Map the current row of the given ResultSet to an Alumno.
     * @param resultSet The ResultSet of which the current row is to be mapped to an Alumno.
     * @return The mapped Alumno from the current row of the given ResultSet.
     * @throws SQLException If something fails at database level.
     */
    private static Alumno map(ResultSet resultSet) throws SQLException {
        Alumno alumno = new Alumno();
        alumno.setLegajo(resultSet.getLong("Legajo"));
        alumno.setNombre(resultSet.getString("Nombre"));
        alumno.setApellido(resultSet.getString("Apellido"));
        alumno.setFechaCumpleanios(resultSet.getDate("Fecha cumpleanios"));
        alumno.setEdad(resultSet.getInt("Edad"));
        alumno.setEmail(resultSet.getString("Email"));
        alumno.setIdCarrera(resultSet.getLong("IDCarrera"));
        return alumno;
    }
}
