package es.pilar.dam.AccesoDatos2.ejercicio14.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import es.pilar.dam.AccesoDatos2.ejercicio14.model.Alumno;
import es.pilar.dam.AccesoDatos2.ejercicio15.pool.MyDataSource;

public class AlumnoDaoImpl implements AlumnoDao {

	public AlumnoDaoImpl() {
	};

	@Override
	public int add(Alumno a) throws SQLException {

		String sql = "INSERT INTO alumno (nia, nombre, apellidos, ciclo, curso, grupo, genero, fecha) \n"
				+ "VALUES (?, ?, ?, ?, ?, 1, ?, ?)";

		int result;

		try (Connection conexion = MyDataSource.getConnection();
				PreparedStatement pstm = conexion.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

			pstm.setInt(1, a.getNia());
			pstm.setString(2, a.getNombre());
			pstm.setString(3, a.getApellidos());
			pstm.setString(4, a.getCiclo());
			pstm.setString(5, a.getCurso());
			//pstm.setString(6, a.getGrupo());
			pstm.setString(6, String.valueOf(a.getGenero()));
			pstm.setDate(7, Date.valueOf(a.getFecha()));

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
	public Alumno getById(int id) throws SQLException {
		
		Alumno result = null;
		
		String sql = "SELECT id, nia, nombre, apellidos, ciclo, curso, grupo, genero, fecha " 
				+ "FROM alumno "
				+ "WHERE id = ?";
		
		try(Connection conexion = MyDataSource.getConnection();
				PreparedStatement pstm = conexion.prepareStatement(sql)) {
			
			pstm.setInt(1, id);
			
			try(ResultSet rs = pstm.executeQuery()){
				
				while(rs.next()) {
					result = rellenarAlumno(rs);
				}
			}
		}
		
		return result;
	}

	@Override
	public List<Alumno> getAll() throws SQLException {

		String sql = "SELECT id, nia, nombre, apellidos, ciclo, curso, grupo, genero, fecha " 
		+ "FROM alumno";

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
	public int update(Alumno a) throws SQLException {
		
		String sql = "UPDATE alumno "
				+ "SET nia = ?, nombre = ?, apellidos = ?, ciclo  = ?, curso = ?, genero = ?, fecha = ? "
				+ "WHERE id = ?";
		
		int result;

		try (Connection conexion = MyDataSource.getConnection();
				PreparedStatement pstm = conexion.prepareStatement(sql)) {

			pstm.setInt(1, a.getNia());
			pstm.setString(2, a.getNombre());
			pstm.setString(3, a.getApellidos());
			pstm.setString(4, a.getCiclo());
			pstm.setString(5, a.getCurso());
			//pstm.setString(6, a.getGrupo());
			pstm.setString(6, String.valueOf(a.getGenero()));
			pstm.setDate(7, Date.valueOf(a.getFecha()));

			pstm.setInt(8, a.getId());
			
			result = pstm.executeUpdate();
		}
		return result;
	}

	@Override
	public void delete(int id) throws SQLException {
		
		String sql = "DELETE FROM alumno WHERE id = ?";

		try (Connection conexion = MyDataSource.getConnection();
				PreparedStatement pstm = conexion.prepareStatement(sql)) {

			pstm.setInt(1, id);
			pstm.executeUpdate();
		}
	}
	
	private Alumno rellenarAlumno(ResultSet rs) throws SQLException {
		
		Alumno a = new Alumno(
		        rs.getInt("nia"), 
		        rs.getString("nombre"), 
		        rs.getString("apellidos"),
		        rs.getString("ciclo"), 
		        rs.getString("curso"), 
		        rs.getInt("grupo"),
		        rs.getString("genero").charAt(0), 
		        rs.getDate("fecha").toLocalDate()
		    );
		    
		a.setId(rs.getInt("id"));  
	    return a;
	}

}
