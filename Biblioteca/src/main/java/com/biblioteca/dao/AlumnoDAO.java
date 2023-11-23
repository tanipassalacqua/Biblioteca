package com.biblioteca.dao;
import com.biblioteca.models.Alumno;
import java.util.List;

//define los metodos para conectarse con la ddbbb
public interface AlumnoDAO {
    /**
     * Returns the Alumno from the database matching the given ID, otherwise null.
     * @param legajo The ID of the Alumno to be returned.
     * @return The Alumno from the database matching the given ID, otherwise null.
     * @throws RuntimeException If something fails at database level.
     */
    public Alumno find(Long legajo) throws RuntimeException;

    public List<Alumno> list() throws RuntimeException;

    /**
     * Create the given Alumno in the database. The Alumno ID must be null, otherwise it will throw
     * IllegalArgumentException. After creating, the DAO will set the obtained ID in the given Alumno.
     * @param Alumno The Alumno to be created in the database.
     * @throws IllegalArgumentException If the Alumno ID is not null.
     * @throws RuntimeException If something fails at database level.
     */
    public void create(Alumno Alumno) throws IllegalArgumentException, RuntimeException;

    /**
     * Update the given Alumno in the database. The Alumno ID must not be null, otherwise it will throw
     * IllegalArgumentException. Note: the password will NOT be updated. Use changePassword() instead.
     * @param Alumno The Alumno to be updated in the database.
     * @throws IllegalArgumentException If the Alumno ID is null.
     * @throws RuntimeException If something fails at database level.
     */
    public void update(Alumno Alumno) throws IllegalArgumentException, RuntimeException;

    /**
     * Delete the given Alumno from the database. After deleting, the DAO will set the ID of the given
     * Alumno to null.
     * @param Alumno The Alumno to be deleted from the database.
     * @throws RuntimeException If something fails at database level.
     */
    public void delete(Alumno Alumno) throws RuntimeException;

    /**
     * Returns true if the given email address exist in the database.
     * @param email The email address which is to be checked in the database.
     * @return True if the given email address exist in the database.
     * @throws RuntimeException If something fails at database level.
     */
    public boolean existEmail(String email) throws RuntimeException;

}
