package es.pilar.dam.AccesoDatos2.ejercicio14;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.StringTokenizer;

import es.pilar.dam.AccesoDatos2.ejercicio14.dao.AlumnoDao;
import es.pilar.dam.AccesoDatos2.ejercicio14.dao.AlumnoDaoImpl;
import es.pilar.dam.AccesoDatos2.ejercicio14.model.Alumno;

public class Menu {

	private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	
	private KeyboardReader sc;
	private AlumnoDao dao;
	
	public Menu() {
		sc = new KeyboardReader();
		dao = AlumnoDaoImpl.getInstance();				
	}
	
	public void init() {
		int opcion;
		
		do {
			opcion = elegir();
			menu(opcion);
			
		} while (opcion != 0);
		
	}
	
	private void menu(int opcion) {
		
		switch (opcion) {
		case 1 -> mostrarTodos();
		case 2 -> mostrarPorId();
		case 3 -> insertar();
		case 4 -> actualizar();
		case 5 -> borrar();
		case 0 -> System.out.println("Terminado");
		default -> System.out.println("Opcion no valida");
		}
	}
	
	private int elegir() {
		System.out.println("\nElija opcion:"
				+ "\n0: Salir"
				+ "\n1: Listar alumnos"
				+ "\n2: Listar por id"
				+ "\n3: Insertar nuevo alumno"
				+ "\n4: Actualizar alumno"
				+ "\n5: Eliminar un alumno");
		System.out.print("-> ");
		
		return Integer.parseInt(sc.nextLine());
	}

	private void insertar() {
		System.out.println("\nINSERCION DE UN NUEVO ALUMNO");
		System.out.println("-".repeat(100));
		
		Alumno a = leerAlumno();
		
		try {
			dao.add(a);
			System.out.println("Alumno insertado");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println("Error insertando el nuevo alumno. Vuelva a intentarlo");
			e.printStackTrace();
		}
	}
	
	private Alumno leerAlumno() {

		System.out.println("NIA:");
		System.out.print("-> ");
		int nia = Integer.parseInt(sc.nextLine());

		System.out.println("Nombre");
		System.out.print("-> ");
		String nombre = sc.nextLine();

		System.out.println("Apellidos:");
		System.out.print("-> ");
		String apellidos = sc.nextLine();

		System.out.println("Ciclo:");
		System.out.print("-> ");
		String ciclo = sc.nextLine();

		System.out.println("Curso:");
		System.out.print("-> ");
		String curso = sc.nextLine();

		System.out.println("Grupo:");
		System.out.print("-> ");
		String grupo = sc.nextLine();

		System.out.println("Género (f/m)");
		System.out.print("-> ");
		char genero = sc.nextLine().charAt(0);

		System.out.println("Fecha de nacimiento yyyy-MM-dd");
		System.out.print("-> ");
		LocalDate fecha = sc.nextLocalDate();
		
		return new Alumno(nia, nombre, apellidos, ciclo, curso, grupo, genero, fecha);
	}
	
	private void mostrarTodos() {
		System.out.println("\nLISTADO DE ALUMNOS:");
		System.out.println("-".repeat(100));
		try {
			List<Alumno> alumnos = dao.getAll();
			
			if (alumnos.isEmpty()) {
				System.out.println("No se han encontrado alumnos");
				
			} else {
				cabecera();
				alumnos.forEach(this::escribirAlumno);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println("Error consultando en la BBDD. Vuelva a intentarlo");
			e.printStackTrace();
		}
	}
	
	private void escribirAlumno(Alumno a) {
		System.out.printf("%-4s %-10s %-15s %-25s %-8s %-6s %-6s %-5s %-12s\n", 
		        a.getId(), a.getNia(), a.getNombre(), a.getApellidos(), a.getCiclo(), a.getCurso(), a.getGrupo(), a.getGenero(), a.getFecha());
	}
	
	private void cabecera() {
		System.out.printf("%-4s %-10s %-15s %-25s %-8s %-6s %-6s %-5s %-12s\n", 
		        "ID", "NIA", "NOMBRE", "APELLIDOS", "CICLO", "CURSO", "GRUPO", "GEN.", "FECHA");
		    
		System.out.println("-".repeat(100));
		System.out.println();
	}
	
	private void mostrarPorId() {
		System.out.println("\nBUSQUEDA DE ALUMNO POR ID");
		System.out.println("-".repeat(100));
		
		Alumno a = buscarPorId();
			
		if (a == null) {
			System.out.println("No se ha encontrado ningún alumno con ese id");
				
		} else {
			cabecera();
			escribirAlumno(a);
		}
	}
	
	private Alumno buscarPorId() {
		
		System.out.println("Introduce el ID del alumno");
		System.out.print("->");
		int id = Integer.parseInt(sc.nextLine());
		
		try {
			return dao.getById(id);
			
		} catch (SQLException e) {
			System.err.println("Error consultando en la BBDD. Vuelva a intentarlo");
			e.printStackTrace();
			return null;
		}
	}
	
	private void actualizar() {
		System.out.println("\nACTUALIZACION DE ALUMNO");
		System.out.println("-".repeat(100));
		
		Alumno a = buscarPorId();
		
		if (a == null) {
			System.out.println("No se ha encontrado ningún alumno con ese id");
			
		} else {
			cabecera();
			escribirAlumno(a);
			
			try {
				dao.update(nuevosDatos(a));
				System.out.printf("Alumno con ID %s actualizado", a.getId() + "\n");
			} catch (SQLException e) {
				System.err.println("Error actualizando en la BBDD. Vuelva a intentarlo");
				e.printStackTrace();
			}
		}
	}
	
	private Alumno nuevosDatos(Alumno a) {
		System.out.println("\nIntroduzca los nuevos datos, enter para mantener");

	    System.out.printf("NIA (%d): ", a.getNia());
	    String nia = sc.nextLine();
	    a.setNia(nia.isBlank() ? a.getNia() : Integer.parseInt(nia));

	    System.out.printf("Nombre (%s): ", a.getNombre());
	    String nombre = sc.nextLine();
	    a.setNombre(nombre.isBlank() ? a.getNombre() : nombre);

	    System.out.printf("Apellidos (%s): ", a.getApellidos());
	    String apellidos = sc.nextLine();
	    a.setApellidos(apellidos.isBlank() ? a.getApellidos() : apellidos);

	    System.out.printf("Ciclo (%s): ", a.getCiclo());
	    String ciclo = sc.nextLine();
	    a.setCiclo(ciclo.isBlank() ? a.getCiclo() : ciclo);

	    System.out.printf("Curso (%s): ", a.getCurso());
	    String curso = sc.nextLine();
	    a.setCurso(curso.isBlank() ? a.getCurso() : curso);

	    System.out.printf("Grupo (%s): ", a.getGrupo());
	    String grupo = sc.nextLine();
	    a.setGrupo(grupo.isBlank() ? a.getGrupo() : grupo);

	    System.out.printf("Género (%c): ", a.getGenero());
	    String genero = sc.nextLine();
	    a.setGenero(genero.isBlank() ? a.getGenero() : genero.charAt(0));

	    System.out.printf("Introduzca la fecha de nacimiento del alumno(%s): ", a.getFecha().format(FORMATO));	    
	    String fecha = sc.nextLine();
	    a.setFecha((fecha.isBlank()) ? a.getFecha() : LocalDate.parse(fecha, FORMATO));
	   
	    return a;
	  }
	
	private void borrar() {
		System.out.println("\nBORRADO DE ALUMNO");
		System.out.println("-".repeat(100));
		
		try {
			Alumno a = buscarPorId();
			
			if (a == null) {
			    System.out.println("No se ha encontrado ningún alumno con ese id");
			    return;
			}
			
			System.out.println("Borrar el alumno:");
			escribirAlumno(a);
			
			System.out.println("Confirmar borrado? ->S/N");
			String borrar = sc.nextLine();
			
			if(borrar.equalsIgnoreCase("s")) {
				dao.delete(a.getId());
				System.out.println("El alumno ha sido eliminado");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println("Error borrando el  alumno. Vuelva a intentarlo");
			e.printStackTrace();
		}
	}

//------------------------------------------------------------------------------------------------------------------	
//LECTURA TECLADO	
	static class KeyboardReader {
		
		BufferedReader br;
		StringTokenizer st;
		
		public KeyboardReader() {
			br = new BufferedReader(new InputStreamReader(System.in));
		}
		
		String next() {
			while (st == null || !st.hasMoreElements()) {
				try {
					st = new StringTokenizer(br.readLine());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.err.println("Error de lectura de teclado");
					e.printStackTrace();
				}
			}
			return st.nextToken();
		}
		
		LocalDate nextLocalDate() {
			LocalDate fecha = null;
		    boolean fechaCorrecta = false;
		    
		    while (!fechaCorrecta) {
		        try {
		            fecha = LocalDate.parse(next(), FORMATO);
		            fechaCorrecta = true;
		            
		        } catch (Exception e) {
		            System.out.println("Formato incorrecto. Por favor, usa yyyy-MM-dd (Ej: 2005-03-25):");
		            System.out.print("-> ");
		        }
		    }
		    return fecha;
		}
		
		String nextLine() {
			String str = "";
			try {
				if (st != null && st.hasMoreElements()) {
					str = st.nextToken("\n");
					
				} else {
					str = br.readLine();
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.err.println("Error de lectura de teclado");
				e.printStackTrace();
			}
			return str;
		}
	}
}
