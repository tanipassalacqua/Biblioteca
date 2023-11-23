package com.biblioteca.dao;
import com.biblioteca.models.Ticket;
import java.util.List;

public interface TicketDAO {
    public Ticket find(Long idTicket) throws RuntimeException;
    /**
     * Returns the Ticket from the database matching the given book id, otherwise null.
     * @param idTicket The email of the Ticket to be returned.
     * @return The Ticket from the database matching the book id, otherwise null.
     * @throws RuntimeException If something fails at database level.
     */
    //public Ticket find(int idLibro) throws RuntimeException;

    /**
     * Returns a list of all Tickets from the database ordered by Ticket ID. The list is never null and
     * is empty when the database does not contain any Ticket.
     * @return A list of all Tickets from the database ordered by Ticket ID.
     * @throws RuntimeException If something fails at database level.
     */
    public List<Ticket> list() throws RuntimeException;

    /**
     * Create the given Ticket in the database. The Ticket ID must be null, otherwise it will throw
     * IllegalArgumentException. After creating, the DAO will set the obtained ID in the given Ticket.
     * @param Ticket The Ticket to be created in the database.
     * @throws IllegalArgumentException If the Ticket ID is not null.
     * @throws RuntimeException If something fails at database level.
     */
    public void create(Ticket Ticket) throws IllegalArgumentException, RuntimeException;

    /**
     * Update the given Ticket in the database. The Ticket ID must not be null, otherwise it will throw
     * IllegalArgumentException. Note: the password will NOT be updated. Use changePassword() instead.
     * @param Ticket The Ticket to be updated in the database.
     * @throws IllegalArgumentException If the Ticket ID is null.
     * @throws RuntimeException If something fails at database level.
     */
    public void update(Ticket Ticket) throws IllegalArgumentException, RuntimeException;

    /**
     * Delete the given Ticket from the database. After deleting, the DAO will set the ID of the given
     * Ticket to null.
     * @param Ticket The Ticket to be deleted from the database.
     * @throws RuntimeException If something fails at database level.
     */
    public void delete(Ticket Ticket) throws RuntimeException;


}
