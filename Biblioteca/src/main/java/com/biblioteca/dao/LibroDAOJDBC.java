package com.biblioteca.dao;

import com.biblioteca.models.Libro;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.biblioteca.dao.DAOUtil.prepareStatement;

public class LibroDAOJDBC implements LibroDAO {
    // Constants ----------------------------------------------------------------------------------

    private static final String SQL_FIND_BY_ID =
            "SELECT * FROM Libros WHERE idLibro = ?";
    private static final String SQL_LIST_ORDER_BY_ID =
            "SELECT Nombre, Autor, IDGenero, Estado FROM Libros ORDER BY idLibro";
    private static final String SQL_INSERT =
            "INSERT INTO Libros (nombre, autor, idGenero,estado) VALUES (?, ?, ?, ?)";
    private static final String SQL_UPDATE =
            "UPDATE Libros SET nombre=?, autor=?, idGenero=?, estado=? WHERE idLibro = ?";
    private static final String SQL_DELETE =
            "DELETE FROM Libros WHERE idLibro = ?";
    private static final String SQL_FIND_BY_NOMBRE =
            "SELECT idLibro, nombre, idGenero, autor, estado FROM Libros WHERE nombre = ? ";
    private static final String SQL_EXIST_ESTADO =
            "SELECT idLibro, nombre, idGenero, autor, estado FROM Libros WHERE estado = ? ";

    // Vars ---------------------------------------------------------------------------------------

    private DAOFactory daoFactory;
// Constructors -------------------------------------------------------------------------------

    /**
     * Construct an Alumno DAO for the given DAOFactory. Package private so that it can be constructed
     * inside the DAO package only.
     *
     * @param daoFactory The DAOFactory to construct this Alumno DAO for.
     */
    LibroDAOJDBC(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public Libro find(Long idLibro) throws RuntimeException {
        return find(SQL_FIND_BY_ID, idLibro);
    }

    @Override
    public Libro find(String nombre) throws RuntimeException {
        return find(SQL_FIND_BY_NOMBRE, nombre);
    }

    /**
     * Returns the libro from the database matching the given SQL query with the given values.
     *
     * @param sql    The SQL query to be executed in the database.
     * @param values The PreparedStatement values to be set.
     * @return The libro from the database matching the given SQL query with the given values.
     * @throws RuntimeException If something fails at database level.
     */
    private Libro find(String sql, Object... values) throws RuntimeException {
        Libro libro = null;

        try (
                Connection connection = daoFactory.getConnection();
                PreparedStatement statement = prepareStatement(connection, sql, false, values);
                ResultSet resultSet = statement.executeQuery();
        ) {
            if (resultSet.next()) {
                libro = map(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return libro;
    }

    @Override
    public List<Libro> list() throws RuntimeException {
        List<Libro> libros = new ArrayList<>();

        try (
                Connection connection = daoFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_LIST_ORDER_BY_ID);
                ResultSet resultSet = statement.executeQuery();
        ) {
            while (resultSet.next()) {
                libros.add(map(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return libros;
    }

    @Override
    public void create(Libro libro) throws IllegalArgumentException, RuntimeException {
        if (null != libro.getIdLibro()) {
            throw new IllegalArgumentException("Libro is already created, the libro ID is not null.");
        }

        Object[] values = {
                libro.getNombre(),
                libro.getAutor(),
                libro.getIdGenero(),
                libro.getEstado()

        };

        try (
                Connection connection = daoFactory.getConnection();
                PreparedStatement statement = prepareStatement(connection, SQL_INSERT, true, values);
        ) {
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new RuntimeException("Creating libro failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    libro.setIdLibro(generatedKeys.getLong(1));
                } else {
                    throw new RuntimeException("Creating libro failed, no generated key obtained.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Libro libro) throws RuntimeException {
        if (libro.getIdLibro() == null) {
            throw new IllegalArgumentException("Libro is not created yet, the libro Legajo is null.");
        }

        Object[] values = {
                libro.getNombre(),
                libro.getAutor(),
                libro.getIdGenero(),
                libro.getEstado(),
                libro.getIdLibro()
        };

        try (
                Connection connection = daoFactory.getConnection();
                PreparedStatement statement = prepareStatement(connection, SQL_UPDATE, false, values);
        ) {
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new RuntimeException("Updating libro failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Libro libro) throws RuntimeException {
        Object[] values = {
                libro.getIdLibro()
        };

        try (
                Connection connection = daoFactory.getConnection();
                PreparedStatement statement = prepareStatement(connection, SQL_DELETE, false, values);
        ) {
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new RuntimeException("Deleting libro failed, no rows affected.");
            } else {
                libro.setIdLibro(null);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean existEstado(Boolean estado) throws RuntimeException {
        Object[] values = {
                estado
        };

        boolean exist = false;

        try (
                Connection connection = daoFactory.getConnection();
                PreparedStatement statement = prepareStatement(connection, SQL_EXIST_ESTADO, false, values);
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
     * Map the current row of the given ResultSet to an Libro.
     *
     * @param resultSet The ResultSet of which the current row is to be mapped to an Libro.
     * @return The mapped Libro from the current row of the given ResultSet.
     * @throws SQLException If something fails at database level.
     */
    private static Libro map(ResultSet resultSet) throws SQLException {
        Libro libro = new Libro();
        libro.setIdLibro(resultSet.getLong("IdLibro"));
        libro.setNombre(resultSet.getString("Nombre"));
        libro.setAutor(resultSet.getString("Autor"));
        libro.setIdGenero(resultSet.getLong("IdGenero"));
        libro.setEstado(resultSet.getBoolean("Estado"));
        return libro;
    }
}
