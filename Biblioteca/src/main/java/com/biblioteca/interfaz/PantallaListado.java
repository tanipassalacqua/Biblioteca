package com.biblioteca.interfaz;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.biblioteca.dao.DAOFactory;
import com.biblioteca.dao.LibroDAO;
import com.biblioteca.models.Libro;

import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PantallaListado extends javax.swing.JFrame {
    private Connection connection;
    private JTable booksTable;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    DAOFactory bibliotecaDB = DAOFactory.getInstance();

    public PantallaListado() {
        initComponents();
        setupDatabaseConnection();
        createBooksTable();
        loadBooksData();
        createButtons();
    }

    private void initComponents() {
        // Configuración de la interfaz, si es necesaria
        // ...
    }

    private void setupDatabaseConnection() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/biblioteca", "root", "admin38");
        } catch (SQLException e) {
            e.printStackTrace();
            // Manejar cualquier error de conexión aquí
        }
    }

    private void createBooksTable() {
        booksTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(booksTable);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void createButtons() {
      addButton = new JButton("Agregar");
      editButton = new JButton("Editar");
      deleteButton = new JButton("Eliminar");

      JPanel buttonPanel = new JPanel();
      buttonPanel.add(addButton);
      buttonPanel.add(editButton);
      buttonPanel.add(deleteButton);

      add(buttonPanel, BorderLayout.SOUTH);

      // Agregar ActionListener para cada botón
      addButton.addActionListener(e -> {
          // Lógica para agregar un libro
          // Puede abrir una ventana nueva para agregar un libro
          VentanaCrearLibro ventanaCrearLibro = new VentanaCrearLibro();
          ventanaCrearLibro.setVisible(true);
          ventanaCrearLibro.getGuardarButton().addActionListener(ev -> {
          // Obtener los datos ingresados por el usuario
          String nombre = ventanaCrearLibro.getNombreField().getText();
          String autor = ventanaCrearLibro.getAutorField().getText();
          // Aquí puedes guardar los datos en la base de datos o realizar otra lógica
          // Por ejemplo:
          try {
            LibroDAO libroDAO = bibliotecaDB.getLibroDAO();
            Libro libro = new Libro();
            libro.setNombre(nombre);
            libro.setIdGenero(1L);
            libro.setEstado(true);
            libro.setAutor(autor);
            libroDAO.create(libro);
            connection.close();
          } catch (SQLException err) {
            err.printStackTrace();
            // Manejo de errores de conexión o consulta SQL
          }
        });
      });

      editButton.addActionListener(e -> {
          // Lógica para editar un libro seleccionado en la tabla
          // Puede abrir una ventana nueva para editar un libro
      });

      deleteButton.addActionListener(e -> {
          // Lógica para eliminar un libro seleccionado en la tabla
          // Puede mostrar un mensaje de confirmación antes de eliminar
      });
    }

    private void loadBooksData() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM libros");

            // Crear un modelo de tabla para almacenar los datos
            DefaultTableModel model = new DefaultTableModel();
            booksTable.setModel(model);

            // Agregar columnas al modelo de tabla
            model.addColumn("ID");
            model.addColumn("Título");
            model.addColumn("Autor");
            // model.addColumn("Año");

            // Agregar filas al modelo con los datos de la base de datos
            while (resultSet.next()) {
                int id = resultSet.getInt("IDLibro");
                String title = resultSet.getString("Nombre");
                String author = resultSet.getString("Autor");
                // int year = resultSet.getInt("anio");

                model.addRow(new Object[]{id, title, author });
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            // Manejar cualquier error al cargar los datos de la base de datos aquí
        }
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new PantallaListado().setVisible(true));
    }
}
