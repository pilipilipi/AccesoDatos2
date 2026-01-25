package es.pilar.dam.AccesoDatos2.ejercicio14;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import es.pilar.dam.AccesoDatos2.ejercicio14.dao.AlumnoDaoImpl;
import es.pilar.dam.AccesoDatos2.ejercicio14.model.Alumno;
import es.pilar.dam.AccesoDatos2.ejercicio14.dao.AlumnoDao;
import es.pilar.dam.AccesoDatos2.ejercicio14.pool.MyDataSource;

public class App {

	public static void main(String[] args) {
		testDao();
	}

	public static void testDao() {

		AlumnoDao dao = AlumnoDaoImpl.getInstance();

		//Alumno a = new Alumno(2024001, "Andrés", "Pérez García", "DAM", "1", "A", 'H', "2005-03-25");
		List<Alumno> alumnos;
		Alumno a2;
		
		try {
			//int n = dao.add(a);			
			//System.out.println("Insertados: " + n);
			
			alumnos = dao.getAll();
			
			if(alumnos.isEmpty()) {
				System.out.println("No hay alumnos");
				
			} else {
				alumnos.forEach(System.out::println);
			}
			
			a2 = dao.getById(1);
			System.out.println(a2);
			
			a2.setApellidos("Perez");
			int m = dao.update(a2);
			System.out.println("Actualizados: " + m);
			
			dao.delete(1);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void testPool() {
		try (Connection conexion = MyDataSource.getConnection()) {

			DatabaseMetaData metaData = conexion.getMetaData();
			String[] types = { "TABLE" };
			ResultSet tables = metaData.getTables(null, null, "%", types);

			while (tables.next()) {
				System.out.println(tables.getString("TABLE_NAME"));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
