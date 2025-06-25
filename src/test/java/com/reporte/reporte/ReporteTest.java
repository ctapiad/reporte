package com.reporte.reporte;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import com.reporte.reporte.Model.Reporte;
import com.reporte.reporte.Model.Dto.UsuarioDto;
import com.reporte.reporte.Model.Entity.ReporteEntity;
import com.reporte.reporte.Repository.ReporteRepository;
import com.reporte.reporte.Service.ReporteService;


public class ReporteTest {

    @Mock

    private RestTemplate restTemplate;

    @Mock

    private ReporteRepository reporteRepository;

    @InjectMocks 
    private ReporteService reporteService;

    private Reporte reporte;
    private ReporteEntity reporteEntity;

    @BeforeEach
    public void setup(){

        MockitoAnnotations.openMocks(this);
        reporte = new Reporte(1, "13.333.456-1", "Usuario creado");
        reporteEntity = new ReporteEntity();
        reporteEntity.setReporteId(1);
        reporteEntity.setRutUsuario("13.333.456-1");
        reporteEntity.setDetalles("Usuario creado");

    }

    @Test
    public void testAgregarReporte_nuevo(){

        when(reporteRepository.existsByReporteId(reporte.getReporteId())).thenReturn(false);
        when(reporteRepository.save(any(ReporteEntity.class))).thenReturn(reporteEntity);

        UsuarioDto usuarioMock = new UsuarioDto();
        usuarioMock.setRut("13.333.456-1");
        when(restTemplate.getForObject(anyString(), eq(UsuarioDto.class))).thenReturn(usuarioMock);

        String resultado = reporteService.crearReporte(reporte);
        assertEquals("Reporte creado correctamente", resultado);

    }

    @Test
    public void testAgregarReporte_existente(){

        when(reporteRepository.existsByReporteId(reporte.getReporteId())).thenReturn(true);

        String resultado = reporteService.crearReporte(reporte);
        assertEquals(null, resultado);

    }

    @Test
    public void testTraerReportePorId(){

        when(reporteRepository.findByReporteId(1)).thenReturn(reporteEntity);
        Reporte resultado = reporteService.obtenReporte(1);
        assertNotNull(resultado);
        assertEquals(1, resultado.getReporteId());

    }

    @Test 
    public void testTraerReporteNoExistente(){

        when(reporteRepository.findByReporteId(100)).thenReturn(null);
        Reporte resultado = reporteService.obtenReporte(100);
        assertNull(resultado);

    }

    @Test
    public void modificarReporte_existente(){

        when(reporteRepository.existsByReporteId(reporte.getReporteId())).thenReturn(true);
        when(reporteRepository.findByReporteId(reporte.getReporteId())).thenReturn(reporteEntity);
        when(reporteRepository.save(any(ReporteEntity.class))).thenReturn(reporteEntity);
        
        UsuarioDto usuarioMock = new UsuarioDto();
        usuarioMock.setRut("13.333.456-1");
        when(restTemplate.getForObject(anyString(), eq(UsuarioDto.class))).thenReturn(usuarioMock);

        String result = reporteService.modificarReporte(reporte);
        assertEquals("Modificación exitosa", result);

    }

    @Test
    public void borrarReporte(){

        when(reporteRepository.existsByReporteId(1)).thenReturn(true);
        doNothing().when(reporteRepository).deleteByReporteId(1);
        
        String resultado = reporteService.eliminarReporte(1);
        
        assertEquals("Reporte eliminado correctamente", resultado);

    }

}
