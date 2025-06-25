package com.reporte.reporte.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.reporte.reporte.Model.Reporte;
import com.reporte.reporte.Model.Dto.CursoDto;
import com.reporte.reporte.Model.Dto.CursoReporteDto;
import com.reporte.reporte.Model.Dto.SoporteDto;
import com.reporte.reporte.Model.Dto.SoporteReporteDto;
import com.reporte.reporte.Model.Dto.UsuarioDto;
import com.reporte.reporte.Model.Dto.UsuarioReporteDto;
import com.reporte.reporte.Model.Entity.ReporteEntity;
import com.reporte.reporte.Repository.ReporteRepository;




@Service
public class ReporteService {
    
    @Autowired
    private ReporteRepository reporteRepository;

    @Autowired
    private RestTemplate restTemplate;

    //private List<Reporte> reportes = new ArrayList<>();
    
    public List<Reporte> ObtenerTodosLosReportes() {

        try{

            List<ReporteEntity> listaReporte = (List<ReporteEntity>) reporteRepository.findAll();
            
            if(listaReporte.isEmpty()){
                
                throw new IllegalArgumentException("No reportes encontrados");

            }

            
            List<Reporte> reportes = new ArrayList<>();
            
            for (ReporteEntity reporte : listaReporte) {

                Reporte nuevoReporte = new Reporte();

                nuevoReporte.setRut_usuario(reporte.getRutUsuario());
                nuevoReporte.setReporteId(reporte.getReporteId());
                nuevoReporte.setDetalles(reporte.getDetalles());

                reportes.add(nuevoReporte);

            }
            return reportes;

        }
        catch (IllegalArgumentException e) {
            
            System.out.println("Error de validación: " + e.getMessage());
            return new ArrayList<>();
        
        }
        catch (Exception e) {
            
            System.out.println("Error al obtener los soportes: " + e.getMessage());
            return new ArrayList<>();

        }


    }

    public Reporte obtenReporte(int reporteId) {
        
        try{

            if (reporteId <= 0) {
                
                throw new IllegalArgumentException("El ID del curso no puede ser menor o igual a cero");
            }

            ReporteEntity reporte = reporteRepository.findByReporteId(reporteId);
            
            if(reporte == null){
                
                throw new IllegalArgumentException("El reporte no existe");

            }

            Reporte mostrarReporte = new Reporte();

            mostrarReporte.setRut_usuario(reporte.getRutUsuario());
            mostrarReporte.setReporteId(reporte.getReporteId());
            mostrarReporte.setDetalles(reporte.getDetalles());

            return mostrarReporte;

        }
        catch (IllegalArgumentException e) {
            
            System.out.println("Error de validación: " + e.getMessage());
            return null;
        
        }
        catch (Exception e) {
            
            System.out.println("Error al obtener el soporte: " + e.getMessage());
            return null;

        }

    }

    public String crearReporte(Reporte reporte) {
        
        try{

           Boolean estado = reporteRepository.existsByReporteId(reporte.getReporteId());
           if(estado != true){
                String usuarioUrl = "http://54.88.178.51:8080/obtenerUsuario/" + reporte.getRut_usuario();
                
                UsuarioDto usuario = restTemplate.getForObject(usuarioUrl, UsuarioDto.class);

                if(usuario == null || usuario.getRut() == null ){

                    System.out.println("El usuario no fue encontrado");
                    return null;

                }

                ReporteEntity nuevoReporte = new ReporteEntity();
                
                nuevoReporte.setRutUsuario(usuario.getRut());
                nuevoReporte.setReporteId(reporte.getReporteId());
                nuevoReporte.setDetalles(reporte.getDetalles());


                reporteRepository.save(nuevoReporte);
                
                return "Reporte creado correctamente";
            }
            else{
                
                System.out.println("El reporte ya existe");
                return null;

            }

        }
        catch (Exception e) {
            
            System.out.println("Error al crear el reporte: " + e.getMessage());
            return null;

        }
    }

    @Transactional
    public String eliminarReporte(int reporteId) {
        
        try{
            
            Boolean estado = reporteRepository.existsByReporteId(reporteId);
            if(estado == true){

                reporteRepository.deleteByReporteId(reporteId);
                
                return "Reporte eliminado correctamente";

            }else{
                
                System.out.println("El sreporte con el ID ingresado no existe");
                return null;

            }
            
        }
        catch (Exception e) {
            
            System.out.println("Error al eliminar el soporte: " + e.getMessage());
            return null;

        }
    }

    public String modificarReporte(Reporte reporte){
        try{
            
            if(reporteRepository.existsByReporteId(reporte.getReporteId())){
                
                String usuarioUrl = "http://54.88.178.51:8080/obtenerUsuario/" + reporte.getRut_usuario();
                UsuarioDto usuario = restTemplate.getForObject(usuarioUrl, UsuarioDto.class);

                if(usuario == null || usuario.getRut() == null){
                    System.out.println("El usuario no fue encontrado");
                    return null;
                }

                ReporteEntity reporteExistente = reporteRepository.findByReporteId(reporte.getReporteId());

                if (reporteExistente != null) {
                    reporteExistente.setRutUsuario(reporte.getRut_usuario());
                    reporteExistente.setReporteId(reporte.getReporteId());
                    reporteExistente.setDetalles(reporte.getDetalles());

                    reporteRepository.save(reporteExistente);

                    System.out.println("El soporte ha sido modificado correctamente");
                    return "Modificación exitosa";
                } else {
                    System.out.println("El soporte no existe");
                    return "El soporte no existe";
                }
            } else {
                return "El soporte con el ID ingresado no existe";
            }
        }
        catch (Exception e) {
            System.out.println("Error al modificar el soporte: " + e.getMessage());
            return "Error al modificar el soporte";
        }
    }

    // Sección para llamar datos de usuario 

    public List<UsuarioDto> obtenerTodosLosUsuarios() {
        try {
            String url = "http://54.88.178.51:8080/usuarios";
            UsuarioDto[] usuariosArray = restTemplate.getForObject(url, UsuarioDto[].class);
            if (usuariosArray != null) {
                return List.of(usuariosArray);
            } else {
                return new ArrayList<>();
            }
        } catch (Exception e) {
            System.out.println("Error al obtener los usuarios: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public UsuarioReporteDto obtenerUsuarioYReportePorReporteId(int reporteId) {
        
        Reporte reporte = obtenReporte(reporteId);
        
        if (reporte == null) {
        
            return null;
        
        }
        
        try {
            
            String usuarioUrl = "http://54.88.178.51:8080/obtenerUsuario/" + reporte.getRut_usuario();
            UsuarioDto usuario = restTemplate.getForObject(usuarioUrl, UsuarioDto.class);
            
            if (usuario == null) {
            
                return null;
            
            }
            
            System.out.println("ReporteId: " + reporte.getReporteId());
            System.out.println("Usuario: " + usuario.toString());
            System.out.println("Detalles: " + reporte.getDetalles());
            
            return new UsuarioReporteDto(usuario, reporte);
        
        } catch (Exception e) {
            
            System.out.println("Error al obtener usuario y reporte: " + e.getMessage());
            
            return null;
        
        }
}

    // Sección para llamar datos de curso

    public List<CursoDto> obtenerTodosLosCursos() {
        try {
            String url = "http://54.88.178.51:8082/cursos";
            CursoDto[] cursoArray = restTemplate.getForObject(url, CursoDto[].class);
            
            if (cursoArray != null) {
                
                return List.of(cursoArray);
           
            } else {
                
                return new ArrayList<>();
           
            }
        
        } catch (Exception e) {
            
            System.out.println("Error al obtener los cursos: " + e.getMessage());
            
            return new ArrayList<>();
        
        }
    }
   

    public CursoReporteDto obtenerCursoYReportePorReporteId(int reporteId) {
        Reporte reporte = obtenReporte(reporteId);
        if(reporte == null) {
            return null;
        }
        try {
            String cursoUrl = "http://54.88.178.51:8082/cursos/" + reporte.getReporteId();
            CursoDto curso = restTemplate.getForObject(cursoUrl, CursoDto.class);
            if (curso == null) {
                return null;
            }
            System.out.println("ReporteId: " + reporte.getReporteId());
            System.out.println("Curso: " + curso.toString());
            System.out.println("Detalles: " + reporte.getDetalles());
            return new CursoReporteDto(curso, reporte);
        } catch (Exception e) {
            System.out.println("Error al obtener curso y reporte: " + e.getMessage());
            return null;

        }

        

    }

    // Sección para llamar datos de soporte

    public List<SoporteDto> obtenerTodosLosSoportes() {
        try {
            String url = "http://54.88.178.51:8081/soportes";
            SoporteDto[] soportesArray = restTemplate.getForObject(url, SoporteDto[].class);
            if (soportesArray != null) {
                return List.of(soportesArray);
            } else {
                return new ArrayList<>();
            }
        } catch (Exception e) {
            System.out.println("Error al obtener los soportes: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public List<Reporte> obtenerReportesPorSoporteId(int soporteId) {
    try {
        // 1. Obtener el soporte por su ID
        String soporteUrl = "http://54.88.178.51:8081/soportes/" + soporteId;
        SoporteDto soporte = restTemplate.getForObject(soporteUrl, SoporteDto.class);
        if (soporte == null) {
            System.out.println("Soporte no encontrado");
            return new ArrayList<>();
        }

        // 2. Obtener el rut del usuario asociado al soporte
        String rutUsuario = soporte.getUser_rut();

        // 3. Buscar todos los reportes que tengan ese rut
        List<ReporteEntity> entidades = reporteRepository.findAllByRutUsuario(rutUsuario);

        // 4. Convertir a DTO y devolver
        List<Reporte> reportes = new ArrayList<>();
        for (ReporteEntity entity : entidades) {
            Reporte reporte = new Reporte();
            reporte.setReporteId(entity.getReporteId());
            reporte.setRut_usuario(entity.getRutUsuario());
            reporte.setDetalles(entity.getDetalles());
            reportes.add(reporte);
        }
        return reportes;
    } catch (Exception e) {
        System.out.println("Error al obtener reportes por soporte: " + e.getMessage());
        return new ArrayList<>();
    }
}
    
}
