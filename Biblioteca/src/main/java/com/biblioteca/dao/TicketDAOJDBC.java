package com.biblioteca.dao;

import com.biblioteca.models.Ticket;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.biblioteca.dao.DAOUtil.*;
public class TicketDAOJDBC implements TicketDAO{
    // Constants ----------------------------------------------------------------------------------

    private static final String SQL_FIND_BY_ID =
            "SELECT Nombre FROM Ticket WHERE IDTicket = ?";
    private static final String SQL_LIST_ORDER_BY_ID =
            "SELECT IDTicket, Legajo, IDLibro, fechaPrestamo, fechaDevolucionPactada, fechaDevolucionFROM ticket ORDER BY IDTicket";
    private static final String SQL_INSERT =
            "INSERT INTO ticket ( Legajo, IDLibro, fechaPrestamo, fechaDevolucionPactada, fechaDevolucion) VALUES ( ?, ?, ?, ?, ?);";
    private static final String SQL_UPDATE =
            "UPDATE Ticket SET Legajo = ?, IDLibro = ?, fechaPrestamo = ?, fechaDevolucionPactada = ?, fechaDevolucion = ? WHERE IDTicket = ?;";
    private static final String SQL_DELETE =
            "DELETE FROM Ticket WHERE IDTicket = ?";
    // Vars ---------------------------------------------------------------------------------------

    private DAOFactory daoFactory;

    // Constructors -------------------------------------------------------------------------------

    /**
     * Construct an Ticket DAO for the given DAOFactory. Package private so that it can be constructed
     * inside the DAO package only.
     * @param daoFactory The DAOFactory to construct this Ticket DAO for.
     */
    TicketDAOJDBC(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    // Actions ------------------------------------------------------------------------------------

    @Override
    public Ticket find(Long IDTicket) throws RuntimeException {
        return find(SQL_FIND_BY_ID, IDTicket);
    }
    
    /**
     * Returns the ticket from the database matching the given SQL query with the given values.
     * @param sql The SQL query to be executed in the database.
     * @param values The PreparedStatement values to be set.
     * @return The ticket from the database matching the given SQL query with the given values.
     * @throws RuntimeException If something fails at database level.
     */
    private Ticket find(String sql, Object... values) throws RuntimeException {
        Ticket ticket = null;

        try (
                Connection connection = daoFactory.getConnection();
                PreparedStatement statement = prepareStatement(connection, sql, false, values);
                ResultSet resultSet = statement.executeQuery();
        ) {
            if (resultSet.next()) {
                ticket = map(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return ticket;
    }

    @Override
    public List<Ticket> list() throws RuntimeException {
        List<Ticket> tickets = new ArrayList<>();

        try (
                Connection connection = daoFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_LIST_ORDER_BY_ID);
                ResultSet resultSet = statement.executeQuery();
        ) {
            while (resultSet.next()) {
                tickets.add(map(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return tickets;
    }

    @Override
    public void create(Ticket ticket) throws IllegalArgumentException, RuntimeException {
        if (null != ticket.getIdTicket()) {
            throw new IllegalArgumentException("Ticket is already created, the ticket ID is not null.");
        }

        Object[] values = {
                ticket.getLegajo(),
                ticket.getIdLibro(),
                toSqlDate(ticket.getFechaPrestamo()),
                toSqlDate(ticket.getFechaDevolucionPactada()),
                toSqlDate(ticket.getFechaDevolucion())
        };

        try (
                Connection connection = daoFactory.getConnection();
                PreparedStatement statement = prepareStatement(connection, SQL_INSERT, true, values);
        ) {
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new RuntimeException("Creating ticket failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    ticket.setIdTicket(generatedKeys.getLong(1));
                } else {
                    throw new RuntimeException("Creating ticket failed, no generated key obtained.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Ticket ticket) throws RuntimeException {
        if (ticket.getLegajo() == null) {
            throw new IllegalArgumentException("Ticket is not created yet, the ticket Legajo is null.");
        }

        Object[] values = {
                ticket.getLegajo(),
                ticket.getIdLibro(),
                ticket.getFechaPrestamo(),
                ticket.getFechaDevolucionPactada(),
                ticket.getFechaDevolucion()
        };

        try (
                Connection connection = daoFactory.getConnection();
                PreparedStatement statement = prepareStatement(connection, SQL_UPDATE, false, values);
        ) {
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new RuntimeException("Updating ticket failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Ticket ticket) throws RuntimeException {

        Object[] values = {
                ticket.getIdTicket()
        };

        try (
                Connection connection = daoFactory.getConnection();
                PreparedStatement statement = prepareStatement(connection, SQL_DELETE, false, values);
        ) {
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new RuntimeException("Deleting ticket failed, no rows affected.");
            } else {
                ticket.setLegajo(null);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Helpers ------------------------------------------------------------------------------------

    /**
     * Map the current row of the given ResultSet to an Ticket.
     * @param resultSet The ResultSet of which the current row is to be mapped to an Ticket.
     * @return The mapped Ticket from the current row of the given ResultSet.
     * @throws SQLException If something fails at database level.
     */
    private static Ticket map(ResultSet resultSet) throws SQLException {
        Ticket ticket = new Ticket();
        ticket.setIdTicket(resultSet.getLong("IDTicket"));
        ticket.setLegajo(resultSet.getLong("Legajo"));
        ticket.setIdLibro(resultSet.getLong("IDLibro"));
        ticket.setFechaPrestamo(resultSet.getDate("Fecha Prestamo"));
        ticket.setFechaDevolucionPactada(resultSet.getDate("Fecha de Devolucion Pactada"));
        ticket.setFechaDevolucion(resultSet.getDate("Fecha de Devolucion"));
        return ticket;
    }
}
