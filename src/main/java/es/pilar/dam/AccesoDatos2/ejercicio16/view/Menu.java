package es.pilar.dam.AccesoDatos2.ejercicio16.view;

import java.util.List;

import es.pilar.dam.AccesoDatos2.ejercicio16.model.Alumno;
import es.pilar.dam.AccesoDatos2.ejercicio16.model.Grupo;

public interface Menu {
    int mostrarMenu();
    
    Alumno pedirDatosAlumno(List<Grupo> grupos);
    
    Grupo pedirDatosGrupo();
    
    void mostrarAlumnos(List<Alumno> alumnos);
    
    void mostrarGrupos(List<Grupo> grupos);
    
    String pedirCadena(String mensaje);
    
    int pedirEntero(String mensaje);
    
    void mostrarMensaje(String mensaje);
    
    void mostrarError(String mensaje);
}
