package com.biblioteca.dao;

import com.biblioteca.models.Genero;

import java.util.List;

public interface GeneroDAO {
    /**
     * Returns the Genero from the database matching the given ID, otherwise null.
     * @param Nombre The name of the Genero to be returned.
     * @return The Genero from the database matching the given ID, otherwise null.
     * @throws RuntimeException If something fails at database level.
     */
    public Genero find(String Nombre) throws RuntimeException;

    public List<Genero> list() throws RuntimeException;

    /**
     * Create the given Genero in the database. The Genero ID must be null, otherwise it will throw
     * IllegalArgumentException. After creating, the DAO will set the obtained ID in the given Genero.
     * @param Genero The Genero to be created in the database.
     * @throws IllegalArgumentException If the Genero ID is not null.
     * @throws RuntimeException If something fails at database level.
     */
    public void create(Genero Genero) throws IllegalArgumentException, RuntimeException;

    /**
     * Update the given Genero in the database. The Genero ID must not be null, otherwise it will throw
     * IllegalArgumentException. Note: the password will NOT be updated. Use changePassword() instead.
     * @param Genero The Genero to be updated in the database.
     * @throws IllegalArgumentException If the Genero ID is null.
     * @throws RuntimeException If something fails at database level.
     */
    public void update(Genero Genero) throws IllegalArgumentException, RuntimeException;

    /**
     * Delete the given Genero from the database. After deleting, the DAO will set the ID of the given
     * Genero to null.
     * @param Genero The Genero to be deleted from the database.
     * @throws RuntimeException If something fails at database level.
     */
    public void delete(Genero Genero) throws RuntimeException;

}
