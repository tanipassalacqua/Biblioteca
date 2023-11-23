package com.biblioteca.dao;
import com.biblioteca.models.Libro;
import java.util.List;

public interface LibroDAO {
    public Libro find(Long idLibro) throws RuntimeException;
    /**
     * Returns the Libro from the database matching the given email and password, otherwise null.
     * @param nombre The email of the Libro to be returned.
     * @return The Libro from the database matching the given email and password, otherwise null.
     * @throws RuntimeException If something fails at database level.
     */
    public Libro find(String nombre) throws RuntimeException;

    /**
     * Returns a list of all Libros from the database ordered by Libro ID. The list is never null and
     * is empty when the database does not contain any Libro.
     * @return A list of all Libros from the database ordered by Libro ID.
     * @throws RuntimeException If something fails at database level.
     */
    public List<Libro> list() throws RuntimeException;

    /**
     * Create the given Libro in the database. The Libro ID must be null, otherwise it will throw
     * IllegalArgumentException. After creating, the DAO will set the obtained ID in the given Libro.
     * @param Libro The Libro to be created in the database.
     * @throws IllegalArgumentException If the Libro ID is not null.
     * @throws RuntimeException If something fails at database level.
     */
    public void create(Libro Libro) throws IllegalArgumentException, RuntimeException;

    /**
     * Update the given Libro in the database. The Libro ID must not be null, otherwise it will throw
     * IllegalArgumentException. Note: the password will NOT be updated. Use changePassword() instead.
     * @param Libro The Libro to be updated in the database.
     * @throws IllegalArgumentException If the Libro ID is null.
     * @throws RuntimeException If something fails at database level.
     */
    public void update(Libro Libro) throws IllegalArgumentException, RuntimeException;

    /**
     * Delete the given Libro from the database. After deleting, the DAO will set the ID of the given
     * Libro to null.
     * @param Libro The Libro to be deleted from the database.
     * @throws RuntimeException If something fails at database level.
     */
    public void delete(Libro Libro) throws RuntimeException;

    /**
     * Returns true if the given email address exist in the database.
     * @param estado The email address which is to be checked in the database.
     * @return True if the given email address exist in the database.
     * @throws RuntimeException If something fails at database level.
     */
    public boolean existEstado(Boolean estado) throws RuntimeException;

}