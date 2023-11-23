package com.biblioteca.models;

import lombok.Data;

import java.util.Date;


//lombok library genera automaticamente getters, setters, constructores, etc. (metodos)
@Data
public class Alumno {
    private Long legajo;
    private String nombre;
    private String apellido;
    private Date fechaCumpleanios;
    private int edad;
    private String email;
    private Long idCarrera;
}
