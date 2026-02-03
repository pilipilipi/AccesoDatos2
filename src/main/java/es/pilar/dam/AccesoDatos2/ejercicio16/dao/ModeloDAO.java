package es.pilar.dam.AccesoDatos2.ejercicio16.dao;

import java.sql.SQLException;
import java.util.List;

import es.pilar.dam.AccesoDatos2.ejercicio16.model.Alumno;
import es.pilar.dam.AccesoDatos2.ejercicio16.model.Grupo;

public interface ModeloDAO {
	
	//ALUMNOS
	int insertarAlumno(Alumno a) throws SQLException;
	List<Alumno> listarAlumnos() throws SQLException;
	int modificarNombre(int id, String nombre) throws SQLException;
	int eliminarPorId(int id) throws SQLException;
	int eliminarPorCurso(String curso) throws SQLException;
	
	//EJ16
	public List<Alumno> listarAlumnosPorGrupo(int idG) throws SQLException;
	List<Alumno> todosLosAlumnos() throws SQLException;
	Alumno mostrarAlumnoPorId(int id) throws SQLException;
	int cambiarGrupoAlumno(int idAlumno, int idGrupo) throws SQLException;
	
	//GRUPOS
	int insertarGrupo(Grupo g) throws SQLException;
	List<Grupo> listarGrupos() throws SQLException;
	Grupo buscarGrupoPorId(int id) throws SQLException;
	
//	//FICHERO
//	int guardarEnFichero(String ruta) throws SQLException;
//	int leerFichero(String ruta) throws SQLException;
//	int guardarFichero2(String ruta) throws SQLException;
//	int leerFichero2(String ruta) throws SQLException;
	
}
