package es.pilar.dam.AccesoDatos2.ejercicio15.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import es.pilar.dam.AccesoDatos2.ejercicio15.model.Alumno;
import es.pilar.dam.AccesoDatos2.ejercicio15.model.Grupo;
import es.pilar.dam.AccesoDatos2.ejercicio15.pool.MyDataSource;

public class ModeloDAOImpl implements ModeloDAO {

	@Override
	public int insertarAlumno(Alumno a) throws SQLException {

		String sql = "INSERT INTO alumno (nia, nombre, apellidos, ciclo, curso, grupo, genero, fecha) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

		int result;

		try (Connection conexion = MyDataSource.getConnection();
				PreparedStatement pstm = conexion.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

			pstm.setInt(1, a.getNia());
			pstm.setString(2, a.getNombre());
			pstm.setString(3, a.getApellidos());
			pstm.setString(4, a.getCiclo());
			pstm.setString(5, a.getCurso());
			pstm.setInt(6, a.getGrupo().getId());// IMPORTANTE
			pstm.setString(7, String.valueOf(a.getGenero()));
			pstm.setDate(8, Date.valueOf(a.getFecha()));

			result = pstm.executeUpdate();

			try (ResultSet rs = pstm.getGeneratedKeys()) {
				if (rs.next()) {
					a.setId(rs.getInt(1));
				}
			}
		}
		return result;
	}

	@Override
	public List<Alumno> listarAlumnos() throws SQLException {
		String sql = "SELECT a.id, a.nia, a.nombre, a.apellidos, a.ciclo, a.curso, a.grupo, a.genero, a.fecha, g.nombre as nombre_grupo " 
				+ "FROM alumno a " 
				+ "INNER JOIN grupo g ON a.grupo = g.id";

		List<Alumno> result = new ArrayList<>();

		try (Connection conexion = MyDataSource.getConnection();
				PreparedStatement pstm = conexion.prepareStatement(sql);
				ResultSet rs = pstm.executeQuery()) {

			Alumno a;

			while (rs.next()) {

				a = rellenarAlumno(rs);
				result.add(a);
			}
		}

		return result;
	}

	@Override
	public int modificarNombre(int id, String nombre) throws SQLException {
		String sql = "UPDATE alumno " + "SET nombre = ? " + "WHERE id = ?";

		int result;

		try (Connection conexion = MyDataSource.getConnection();
				PreparedStatement pstm = conexion.prepareStatement(sql)) {

			pstm.setString(1, nombre);
			pstm.setInt(2, id);

			result = pstm.executeUpdate();
		}
		return result;
	}

	@Override
	public int eliminarPorId(int id) throws SQLException {
		String sql = "DELETE FROM alumno WHERE id = ?";

		int result;

		try (Connection conexion = MyDataSource.getConnection();
				PreparedStatement pstm = conexion.prepareStatement(sql)) {

			pstm.setInt(1, id);
			result = pstm.executeUpdate();
		}
		return result;
	}

	@Override
	public int eliminarPorCurso(String curso) throws SQLException {
		String sql = "DELETE FROM alumno WHERE curso = ?";

		int result;

		try (Connection conexion = MyDataSource.getConnection();
				PreparedStatement pstm = conexion.prepareStatement(sql)) {

			pstm.setString(1, curso);
			result = pstm.executeUpdate();
		}
		return result;
	}

	@Override
	public int insertarGrupo(Grupo g) throws SQLException {
		String sql = "INSERT INTO grupo (nombre) VALUES (?)";
		
		int result;
		
		try (Connection conexion = MyDataSource.getConnection();
				PreparedStatement pstm = conexion.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
			
			pstm.setString(1, g.getNombre());
			
			result = pstm.executeUpdate();
			
			try (ResultSet rs = pstm.getGeneratedKeys()) {
				if (rs.next()) {
					g.setId(rs.getInt(1));
				}
			}
		}
		return result;
	}

	@Override
	public List<Grupo> listarGrupos() throws SQLException {
		String sql = "SELECT id, nombre "
				+ "FROM grupo";		

		List<Grupo> result = new ArrayList<>();
		
		try (Connection conexion = MyDataSource.getConnection();
				PreparedStatement pstm = conexion.prepareStatement(sql);
				ResultSet rs = pstm.executeQuery()) {

			Grupo g;

			while (rs.next()) {

				g = rellenarGrupo(rs);
				result.add(g);
			}
		}
		
		return result;
	}

	@Override
	public Grupo buscarGrupoPorId(int id) throws SQLException {
		String sql = "SELECT id, nombre "
				+ "FROM grupo "
				+ "WHERE id = ?";	
		
		Grupo result = null;
		
		try (Connection conexion = MyDataSource.getConnection();
				PreparedStatement pstm = conexion.prepareStatement(sql)) {

			pstm.setInt(1, id);

			try(ResultSet rs = pstm.executeQuery()){
				while (rs.next()) {
					result = rellenarGrupo(rs);
				}
			}	
		}
		return result;
	}

	private Alumno rellenarAlumno(ResultSet rs) throws SQLException {

		Grupo g = rellenarGrupo(rs);

		Alumno a = new Alumno(rs.getInt("nia"), rs.getString("nombre"), rs.getString("apellidos"),
				rs.getString("ciclo"), rs.getString("curso"), g, rs.getString("genero").charAt(0),
				rs.getDate("fecha").toString());

		a.setId(rs.getInt("id"));
		return a;
	}
	
	private Grupo rellenarGrupo(ResultSet rs) throws SQLException {
		Grupo g = new Grupo(rs.getString("nombre"));
		g.setId(rs.getInt("id"));
		
		return g;
	}

}
