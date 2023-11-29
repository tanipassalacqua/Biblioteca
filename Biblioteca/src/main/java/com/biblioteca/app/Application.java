package com.biblioteca.app;

import com.biblioteca.dao.AlumnoDAO;
import com.biblioteca.dao.CarrerasDAO;
import com.biblioteca.dao.LibroDAO;
import com.biblioteca.dao.GeneroDAO;
import com.biblioteca.dao.TicketDAO;
import com.biblioteca.models.*;
import com.biblioteca.dao.DAOFactory;
import com.biblioteca.interfaz.Pantalla;
import org.apache.commons.lang3.RandomStringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;


public class Application {
    public static void main (String arg[]){
        
        Pantalla win = new Pantalla();
        win.setVisible(true);
        win.setLocationRelativeTo(null);
        
        // Obtain DAOFactory.
        DAOFactory bibliotecaDB = DAOFactory.getInstance();
        System.out.println("DAOFactory successfully obtained: " + bibliotecaDB);

        //Obtain CarrerasDAO.
        CarrerasDAO carrerasDAO = bibliotecaDB.getCarrerasDAO();
        // Obtain AlumnoDAO.
        AlumnoDAO alumnoDAO = bibliotecaDB.getAlumnoDAO();
        //Obtain Genero
        GeneroDAO generoDAO = bibliotecaDB.getGeneroDAO();
        //Obtain LibroDAO
        LibroDAO libroDAO = bibliotecaDB.getLibroDAO();
        //Obtain TicketDAO
        TicketDAO ticketDAO = bibliotecaDB.getTicketDAO();

        //Create carrera.
        Carreras carreras = new Carreras();
        carreras.setNombre(RandomStringUtils.randomAlphabetic(10));
        carrerasDAO.create(carreras);
        System.out.println("Carreras was succesfully obtained: "+carreras);


        //Create Genero.
        Genero genero = new Genero();
        genero.setNombre(RandomStringUtils.randomAlphabetic(10));
        generoDAO.create(genero);
        System.out.println("Genero successfully created:"+genero);

        //Create libro.
        Libro libro = new Libro();
        libro.setNombre("Harry Potter y la camara secreta");
        libro.setIdGenero(genero.getIDGenero());
        libro.setEstado(true);
        libro.setAutor("J. K. Rowling");
        libroDAO.create(libro);
        System.out.println("Libro successfully created: "+libro);

        //Create Ticket
        Ticket ticket = new Ticket();
        // ticket.setLegajo(alumno.getLegajo());
        ticket.setIdLibro(libro.getIdLibro());
        try{
        ticket.setFechaPrestamo(new SimpleDateFormat("dd-MM-yyyy").parse("28-01-2004"));
        } catch (ParseException e) {
        throw new RuntimeException(e);
        }
        try{
        ticket.setFechaDevolucionPactada(new SimpleDateFormat("dd-MM-yyyy").parse("28-01-2004"));
        } catch (ParseException e) {
        throw new RuntimeException(e);
        }
        try{
        ticket.setFechaDevolucion(new SimpleDateFormat("dd-MM-yyyy").parse("28-01-2004"));
        } catch (ParseException e) {
        throw new RuntimeException(e);
        }
        ticketDAO.create(ticket);
        System.out.println("Ticket successfully created:"+ticket);

        libro.setEstado(false);
        libroDAO.update(libro);
        System.out.println("Libro successfully updated: "+libroDAO.find(libro.getIdLibro()));
    }
}
