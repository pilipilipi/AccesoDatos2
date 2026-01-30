package es.pilar.dam.AccesoDatos2.ejercicio15.view;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import es.pilar.dam.AccesoDatos2.ejercicio15.model.Alumno;
import es.pilar.dam.AccesoDatos2.ejercicio15.model.Grupo;

public class MenuImpl implements Menu {
	private Scanner sc;

    public MenuImpl() {
        this.sc = new Scanner(System.in);
    }

	@Override
	public int mostrarMenu() {
		System.out.println("Elija opcion");
        System.out.println("1. Insertar Alumno");
        System.out.println("2. Insertar Grupo");
        System.out.println("3. Mostrar Alumnos (con su Grupo)");
        System.out.println("4. Modificar Nombre Alumno (por ID)");
        System.out.println("5. Eliminar Alumno (por ID)");
        System.out.println("6. Eliminar Alumnos por Curso");
        System.out.println("7. Guardar Alumnos en Fichero (Binario)");
        System.out.println("8. Leer Alumnos de Fichero (Binario) a BD");
        System.out.println("9. Guardar Grupos y Alumnos (XML/JSON)");
        System.out.println("10. Leer Grupos y Alumnos (XML/JSON) a BD");
        System.out.println("0. Salir");
        System.out.print("-> ");
        
        try {
            int opcion = Integer.parseInt(sc.nextLine());
            return opcion;
        } catch (NumberFormatException e) {
            return -1; 
        }
	}

	@Override
	public Alumno pedirDatosAlumno(List<Grupo> grupos) {
		
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

		System.out.println("Género (f/m)");
		System.out.print("-> ");
		char genero = sc.nextLine().charAt(0);

		System.out.println("Fecha de nacimiento yyyy-MM-dd");
		System.out.print("-> ");
		String fecha = sc.nextLine();
		
		mostrarGrupos(grupos);
		
		System.out.println("\nId de grupo");
		System.out.print("-> ");
        int idGrupo = Integer.parseInt(sc.nextLine());
        
        Grupo grupoS = grupos.stream().filter(g -> g.getId() == idGrupo).findFirst().orElse(null);
        
		return new Alumno(nia, nombre, apellidos, ciclo, curso, grupoS, genero, fecha);
	}

	@Override
	public Grupo pedirDatosGrupo() {
		System.out.println("Nombre");
		System.out.print("-> ");
		String nombre = sc.nextLine();
		
		return new Grupo(nombre);
	}

	@Override
	public void mostrarAlumnos(List<Alumno> alumnos) {
		
		
	}

	@Override
	public void mostrarGrupos(List<Grupo> grupos) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String pedirCadena(String mensaje) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int pedirEntero(String mensaje) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void mostrarMensaje(String mensaje) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mostrarError(String mensaje) {
		// TODO Auto-generated method stub
		
	}
}
