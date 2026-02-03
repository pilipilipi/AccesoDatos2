package es.pilar.dam.AccesoDatos2.ejercicio16.controller;

import java.util.List;

import es.pilar.dam.AccesoDatos2.ejercicio16.model.Alumno;
import es.pilar.dam.AccesoDatos2.ejercicio16.model.Grupo;

public interface Conversor {
	
	void exportarAlumnos(List<Alumno> alumnos);
	
	void exportarGrupos(List<Grupo> grupos);
	
	void exportarGruposYAlumnos(List<Grupo> grupos, List<Alumno> alumnos);
	
	List<Alumno> importarAlumnos();
	
	List<Grupo> importarGrupos();
}
