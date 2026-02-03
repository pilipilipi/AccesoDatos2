package es.pilar.dam.AccesoDatos2.ejercicio16.controller;

import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import es.pilar.dam.AccesoDatos2.ejercicio16.dao.ModeloDAO;
import es.pilar.dam.AccesoDatos2.ejercicio16.model.Alumno;
import es.pilar.dam.AccesoDatos2.ejercicio16.model.Grupo;
import es.pilar.dam.AccesoDatos2.ejercicio16.view.Menu;

public class Controller {
	private static final Logger logger = LogManager.getLogger(Controller.class);
	private final ModeloDAO dao;
	private final Menu vista;
	private final Conversor conversorTxt = new ConversorTxt();
	private final Conversor conversorXml = new ConversorXml();

	public Controller(ModeloDAO dao, Menu vista) {
		this.dao = dao;
		this.vista = vista;
	}

	public void ejecutar() {
		int opcion;

		do {
			opcion = vista.mostrarMenu();
			try {
				procesarOpcion(opcion);
			} catch (Exception e) {
				logger.error("Error crítico en la aplicación: ", e);
				System.err.println("Ha ocurrido un error. Consulte el archivo de log.");
			}
		} while (opcion != 0);
	}

	private void procesarOpcion(int opcion) throws SQLException {
		switch (opcion) {
		case 1 -> insertarAlumno();
		case 2 -> insertarGrupo();
		case 3 -> listarAlumnos();
		case 4 -> modificarNombreAlumno();
		case 5 -> eliminarAlumnoPorId();
		case 6 -> eliminarAlumnosPorCurso();
		case 7 -> guardarBinario();
		case 8 -> leerBinario();
		case 9 -> guardarXML();
		case 10 -> leerXML();
		case 11 -> mostrarPorGrupo();
		case 12 -> listarAlumnoPorId();
		case 13 -> modificarGrupo();
		case 0 -> logger.info("Terminado");
		default -> vista.mostrarError("Opción no válida.");
		}
	}

	private void insertarAlumno() throws SQLException {

		List<Grupo> grupos = dao.listarGrupos();
		if (grupos.isEmpty()) {
			logger.info("No hay grupos. Cree uno primero.");
			return;
		}

		Alumno a = vista.pedirDatosAlumno(grupos);
		dao.insertarAlumno(a);
		logger.info("Alumno insertado");
	}

	private void insertarGrupo() throws SQLException {

		Grupo g = vista.pedirDatosGrupo();
		dao.insertarGrupo(g);
		logger.info("Grupo insertado");
	}

	private void listarAlumnos() throws SQLException {
		List<Alumno> alumnos = dao.listarAlumnos();
		vista.mostrarAlumnos(alumnos);
	}

	private void modificarNombreAlumno() throws SQLException {
		int id = vista.pedirEntero("\nDime el id");
		String n = vista.pedirCadena("\nDime el nombre");

		dao.modificarNombre(id, n);
	}

	private void eliminarAlumnoPorId() throws SQLException {
		int id = vista.pedirEntero("\nDime el id");

		dao.eliminarPorId(id);
		logger.info("Eliminado alumno con ID {}", id);
	}

	private void eliminarAlumnosPorCurso() throws SQLException {
		String c = vista.pedirCadena("\nDime el curso");

		int total = dao.eliminarPorCurso(c);
		logger.info("Se han eliminado " + total + " alumnos.");
	}

	private void guardarBinario() throws SQLException {
		List<Alumno> alumnos = dao.listarAlumnos();
		conversorTxt.exportarAlumnos(alumnos);

		List<Grupo> grupos = dao.listarGrupos();
		conversorTxt.exportarGrupos(grupos);
	}

	private void leerBinario() throws SQLException {
		List<Alumno> alumnos = conversorTxt.importarAlumnos();
		for (Alumno a : alumnos) {
			dao.insertarAlumno(a);
		}

		List<Grupo> grupos = conversorTxt.importarGrupos();
		for (Grupo g : grupos) {
			dao.insertarGrupo(g);
		}
	}

	private void guardarXML() throws SQLException {
		List<Alumno> alumnos = dao.listarAlumnos();
		List<Grupo> grupos = dao.listarGrupos();

		conversorXml.exportarGruposYAlumnos(grupos, alumnos);
	}

	private void leerXML() throws SQLException {
		List<Alumno> alumnos = conversorXml.importarAlumnos();
		for (Alumno a : alumnos) {
			dao.insertarAlumno(a);
		}

		List<Grupo> grupos = conversorXml.importarGrupos();
		for (Grupo g : grupos) {
			dao.insertarGrupo(g);
		}
	}
	
	private void mostrarPorGrupo() throws SQLException {
		List<Grupo> grupos = dao.listarGrupos();
		vista.mostrarGrupos(grupos);
				
		int id = vista.pedirEntero("Dime el id del grupo:");
		
		List<Alumno> alumnos = dao.listarAlumnosPorGrupo(id);
		vista.mostrarAlumnos(alumnos);
	}

	private void listarAlumnoPorId() throws SQLException {
		List<Alumno> alumnos = dao.todosLosAlumnos();
		
		for (Alumno a : alumnos) {
            vista.mostrarMensaje(a.getId() + " - " + a.getNombre());
        }
		
		int id = vista.pedirEntero("Dime el id del Alumno:");
		
		Alumno a = dao.mostrarAlumnoPorId(id);
		vista.mostrarMensaje(a.toString());
	}
	
	private void modificarGrupo() throws SQLException {
		
		List<Alumno> alumnos = dao.todosLosAlumnos();
		
		for (Alumno a : alumnos) {
            vista.mostrarMensaje(a.getId() + " - " + a.getNombre());
        }
		
		int ida = vista.pedirEntero("Dime el id del Alumno:");
		
		List<Grupo> grupos = dao.listarGrupos();
        vista.mostrarGrupos(grupos);
         
        int idg = vista.pedirEntero("Dime el id del Grupo:");
        
        dao.cambiarGrupoAlumno(ida, idg);
	}
}
