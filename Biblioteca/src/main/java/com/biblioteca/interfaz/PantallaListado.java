package com.biblioteca.interfaz;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PantallaListado extends javax.swing.JFrame {
    private Connection connection;
    private JTable booksTable;

    public PantallaListado() {
        initComponents();
        setupDatabaseConnection();
        createBooksTable();
        loadBooksData();
    }

    private void initComponents() {
        // Configuración de la interfaz, si es necesaria
        // ...
    }

    private void setupDatabaseConnection() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/biblioteca", "root", "1234");
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
