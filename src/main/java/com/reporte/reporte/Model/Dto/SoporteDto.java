package com.reporte.reporte.Model.Dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class SoporteDto {

    private int id;
    private String nombre;
    private String user_rut;
    private String detalle;
    private String estado;
    private Date fecha_creacion;


}
