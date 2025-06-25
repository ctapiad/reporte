package com.reporte.reporte.Model.Dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CursoDto {

    private int id;
    private String nombre;
    private String nombre_docente;
    private String rut_docente;
    private Date fecha_inicio;
    private Date fecha_fin;
    private String descripcion;


}
