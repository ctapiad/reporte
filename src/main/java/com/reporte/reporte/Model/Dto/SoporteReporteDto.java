package com.reporte.reporte.Model.Dto;

import com.reporte.reporte.Model.Reporte;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class SoporteReporteDto {
    
    SoporteDto soporte;
    Reporte reporte;

}
