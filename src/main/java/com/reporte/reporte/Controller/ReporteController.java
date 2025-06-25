package com.reporte.reporte.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.reporte.reporte.Model.Reporte;
import com.reporte.reporte.Model.Dto.CursoDto;
import com.reporte.reporte.Model.Dto.CursoReporteDto;
import com.reporte.reporte.Model.Dto.SoporteDto;
import com.reporte.reporte.Model.Dto.SoporteReporteDto;
import com.reporte.reporte.Model.Dto.UsuarioDto;
import com.reporte.reporte.Model.Dto.UsuarioReporteDto;
import com.reporte.reporte.Service.ReporteService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
public class ReporteController {
    
    @Autowired
    private ReporteService reporteService;

    //Obtener todos los reportes

    @Operation(summary = "Obtener todos los reportes")
    @GetMapping("/Reportes")
    public ResponseEntity<List<Reporte>> mostrarReportes() {

        List<Reporte> reportes = reporteService.ObtenerTodosLosReportes();

        if(reportes == null || reportes.isEmpty()){;
            
           return ResponseEntity.noContent().build();

        }
        return ResponseEntity.ok(reportes);

    }

    //Crear un nuevo reporte

    @Operation(summary = "Crear un nuevo reporte")
    @PostMapping("/Reportes")
    public ResponseEntity<String> crearReporte(@RequestBody Reporte reporte)
    {
        String respuesta = reporteService.crearReporte(reporte);

        if(respuesta != null) {

            return ResponseEntity.status(201).body(respuesta);
        
        }
        
        return ResponseEntity.badRequest().body("Error al crear el reporte. Verifica los datos proporcionados.");

    }

    //Eliminar un reporte por ID

    @Operation(summary = "Eliminar un reporte por ID")
    @DeleteMapping("/Reportes/{reporteId}")
    public ResponseEntity<String> eliminarReporte(@PathVariable int reporteId)
    { 
         
        String respuesta = reporteService.eliminarReporte(reporteId);

        if(respuesta == null) {

            return ResponseEntity.notFound().build();
        
        }
        return ResponseEntity.ok(respuesta);
    
    }

    //Obtener un reporte por ID

    @Operation(summary = "Obtener un reporte por ID")
    @GetMapping("/Reportes/{reporteId}")
    public ResponseEntity<Reporte> obtenerReporte(@PathVariable int reporteId)
    {
        if (reporteService.obtenReporte(reporteId) != null) {
            return ResponseEntity.ok(reporteService.obtenReporte(reporteId));
        }
        return ResponseEntity.notFound().build();
        
    }

    //Actualizar un reporte por ID

    @Operation(summary = "Actualizar un reporte por ID")
    @PutMapping("/Reportes")
    public ResponseEntity<String> modificarReporte(@RequestBody Reporte reporte)
    { 
        if (reporteService.modificarReporte(reporte) != null )  {
            return ResponseEntity.ok(reporteService.modificarReporte(reporte));
        }
        return ResponseEntity.badRequest().body("Error al modificar el reporte. Verifica los datos proporcionados.");

    }

    //Obtener todos los usuarios desde el microservicio de usuarios

    @Operation(summary = "Obtener todos los usuarios desde el microservicio de usuarios")
    @GetMapping("/usuarios") 
    public ResponseEntity<List<UsuarioDto>> obtenerTodosLosUsuarios() {
        List<UsuarioDto> usuarios = reporteService.obtenerTodosLosUsuarios();
        if (usuarios == null || usuarios.isEmpty()) {
            
            return ResponseEntity.noContent().build();
        
        }
        return ResponseEntity.ok(usuarios);
    }

    //Obtener usuario y reporte por reporteId

    @Operation(summary = "Obtener usuario y reporte por reporteId")
    
    @GetMapping("/usuario-reporte/{reporteId}")
    
    public ResponseEntity<UsuarioReporteDto> obtenerUsuarioYReportePorReporteId(@PathVariable int reporteId) { 
        if (reporteService.obtenerUsuarioYReportePorReporteId(reporteId) == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reporteService.obtenerUsuarioYReportePorReporteId(reporteId));
    }

    //Obtener todos los soportes del microservicio de soporte

    @Operation(summary = "Obtener todos los soportes del microservicio de soporte")
    @GetMapping("/soportes")
    public ResponseEntity<List<SoporteDto>> obtenerTodosLosSoportes() {   
        List<SoporteDto> soportes = reporteService.obtenerTodosLosSoportes();
        if (soportes == null || soportes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(soportes);
    }

    //Obtener soporte y reporte por reporteId

    @Operation(summary = "Obtener todos los reportes asociados a un soporte por soporteId")
    @GetMapping("/reportes-por-soporte/{soporteId}")
    public ResponseEntity<List<Reporte>> obtenerReportesPorSoporteId(@PathVariable int soporteId) {
        List<Reporte> reportes = reporteService.obtenerReportesPorSoporteId(soporteId);
        if (reportes == null || reportes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(reportes);
    }

    //Obtener todos los cursos del microservicio de cursos

    @Operation(summary = "Obtener todos los cursos del microservicio de cursos")
    @GetMapping("/cursos")
    public ResponseEntity<List<CursoDto>> obtenerTodosLosCursos() {
        List<CursoDto> cursos = reporteService.obtenerTodosLosCursos();
        if (cursos == null || cursos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(cursos);

    }

    //Obtener curso y reporte por reporteId

    @Operation(summary = "Obtener curso y reporte por reporteId")
    @GetMapping("/curso-reporte/{reporteId}")
    public ResponseEntity<CursoReporteDto> obtenerCursoYReportePorReporteId(@PathVariable int reporteId) {
        if (reporteService.obtenerCursoYReportePorReporteId(reporteId) == null) { 
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reporteService.obtenerCursoYReportePorReporteId(reporteId));
    }

}