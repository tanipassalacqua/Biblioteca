package com.biblioteca.models;
import lombok.Data;

import java.util.Date;

@Data
public class Ticket {
    private Long idTicket;
    private Long Legajo;
    private Long idLibro;
    private Date fechaPrestamo;
    private Date fechaDevolucionPactada;
    private Date FechaDevolucion;
}
