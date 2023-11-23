package com.biblioteca.dao;

import com.biblioteca.models.Carreras;

import java.util.List;

public interface CarrerasDAO {
    /**
     * Returns the Carreras from the database matching the given ID, otherwise null.
     * @param Nombre The ID of the Carreras to be returned.
     * @return The Carreras from the database matching the given ID, otherwise null.
     * @throws RuntimeException If something fails at database level.
     */
    public Carreras find(String Nombre) throws RuntimeException;

    public List<Carreras> list() throws RuntimeException;

    /**
     * Create the given Carreras in the database. The Carreras ID must be null, otherwise it will throw
     * IllegalArgumentException. After creating, the DAO will set the obtained ID in the given Carreras.
     * @param Carreras The Carreras to be created in the database.
     * @throws IllegalArgumentException If the Carreras ID is not null.
     * @throws RuntimeException If something fails at database level.
     */
    public void create(Carreras Carreras) throws IllegalArgumentException, RuntimeException;

    /**
     * Update the given Carreras in the database. The Carreras ID must not be null, otherwise it will throw
     * IllegalArgumentException. Note: the password will NOT be updated. Use changePassword() instead.
     * @param Carreras The Carreras to be updated in the database.
     * @throws IllegalArgumentException If the Carreras ID is null.
     * @throws RuntimeException If something fails at database level.
     */
    public void update(Carreras Carreras) throws IllegalArgumentException, RuntimeException;

    /**
     * Delete the given Carreras from the database. After deleting, the DAO will set the ID of the given
     * Carreras to null.
     * @param Carreras The Carreras to be deleted from the database.
     * @throws RuntimeException If something fails at database level.
     */
    public void delete(Carreras Carreras) throws RuntimeException;

}
