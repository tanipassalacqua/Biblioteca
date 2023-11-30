package com.biblioteca.interfaz;

import javax.swing.*;

public class VentanaCrearLibro extends JFrame {
    private JTextField nombreField;
    private JTextField autorField;
    private JButton guardarButton;

    public VentanaCrearLibro() {
        initComponents();
    }

    private void initComponents() {
        nombreField = new JTextField(20);
        autorField = new JTextField(20);
        guardarButton = new JButton("Guardar");

        JPanel panel = new JPanel();
        panel.add(new JLabel("Nombre:"));
        panel.add(nombreField);
        panel.add(new JLabel("Autor:"));
        panel.add(autorField);
        panel.add(guardarButton);

        add(panel);

        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Cierra esta ventana, no la aplicación completa
        setLocationRelativeTo(null); // Centra la ventana en la pantalla
    }

    public JTextField getNombreField() {
        return nombreField;
    }

    public JTextField getAutorField() {
        return autorField;
    }

    public JButton getGuardarButton() {
        return guardarButton;
    }
}

