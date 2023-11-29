package com.biblioteca.interfaz;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.biblioteca.dao.AlumnoDAO;
import com.biblioteca.dao.DAOFactory;
import com.biblioteca.models.Alumno;

public class Pantalla extends javax.swing.JFrame {
    public Pantalla() {
        initComponents();
    };
    private void initComponents() {
        DAOFactory bibliotecaDB = DAOFactory.getInstance();
        Alumno student = new Alumno();
        AlumnoDAO alumnoDAO = bibliotecaDB.getAlumnoDAO();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        dropBoxCarreras = new javax.swing.JComboBox<>();
        nameField = new javax.swing.JTextField();
        surnameField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        birthField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        emailField = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        age = new javax.swing.JSpinner();
        createButton = new javax.swing.JButton();
        editButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Ebrima", 0, 12)); // NOI18N
        jLabel1.setText("NOMBRE");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 80, -1, -1));

        dropBoxCarreras.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        dropBoxCarreras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dropBoxCarrerasActionPerformed(evt);
            }
        });
        jPanel1.add(dropBoxCarreras, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 280, 190, -1));
        jPanel1.add(nameField, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 80, 190, -1));
        jPanel1.add(surnameField, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 120, 190, -1));

        jLabel2.setFont(new java.awt.Font("Ebrima", 0, 12)); // NOI18N
        jLabel2.setText("APELLIDO");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 120, -1, -1));
        jPanel1.add(birthField, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 160, 190, -1));

        jLabel3.setFont(new java.awt.Font("Ebrima", 0, 12)); // NOI18N
        jLabel3.setText("FECHA NACIMIENTO");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 160, -1, -1));

        jLabel4.setFont(new java.awt.Font("Ebrima", 0, 12)); // NOI18N
        jLabel4.setText("EDAD");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 200, -1, -1));
        jPanel1.add(emailField, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 240, 190, -1));

        jLabel5.setFont(new java.awt.Font("Ebrima", 0, 12)); // NOI18N
        jLabel5.setText("EMAIL");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 240, -1, -1));

        jLabel6.setFont(new java.awt.Font("Ebrima", 0, 12)); // NOI18N
        jLabel6.setText("CARRERA");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 280, -1, -1));
        jPanel1.add(age, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 200, 80, -1));

        createButton.setText("CREAR");
        jPanel1.add(createButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 340, -1, -1));
        createButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    student.setNombre(nameField.getText());
                    student.setEdad((int) age.getValue());
                    student.setEmail(emailField.getText());
                    student.setApellido(surnameField.getText());
                    // todo: quedaria ver como hacer para pasar el String del dato al tipo long;
                    // System.out.println(dropBoxCarreras.getSelectedItem());
                    // ! por ahora lo hardcodeo acá:
                    student.setIdCarrera(1L);
                    student.setFechaCumpleanios(new SimpleDateFormat("dd-MM-yyyy").parse(birthField.getText()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                alumnoDAO.create(student);
                System.out.println("Estudiante creado con éxito!");
                PantallaListado listado = new PantallaListado();
                listado.setVisible(true);
                setVisible(false);
            };
        });

        // editButton.setText("EDITAR");
        // jPanel1.add(editButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 340, -1, -1));

        // deleteButton.setText("ELIMINAR");
        // jPanel1.add(deleteButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 340, -1, -1));

        jLabel7.setFont(new java.awt.Font("Ebrima", 1, 24)); // NOI18N
        jLabel7.setText("ALUMNO");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 20, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void dropBoxCarrerasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dropBoxCarrerasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dropBoxCarrerasActionPerformed

    /**
     * @param args the command line arguments
     */
   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSpinner age;
    private javax.swing.JTextField birthField;
    private javax.swing.JButton createButton;
    private javax.swing.JButton deleteButton;
    private javax.swing.JComboBox<String> dropBoxCarreras;
    private javax.swing.JButton editButton;
    private javax.swing.JTextField emailField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField nameField;
    private javax.swing.JTextField surnameField;
    // End of variables declaration//GEN-END:variables
}
