package com.biblioteca.models;
import lombok.Data;
@Data
public class Libro {

    private Long idLibro;
    private String nombre;
    private String Autor;
    private Long idGenero;
    private Boolean estado;
}
