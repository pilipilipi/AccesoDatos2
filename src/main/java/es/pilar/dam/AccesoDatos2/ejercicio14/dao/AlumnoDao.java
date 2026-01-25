package es.pilar.dam.AccesoDatos2.ejercicio14.dao;

import java.sql.SQLException;
import java.util.List;

import es.pilar.dam.AccesoDatos2.ejercicio14.model.Alumno;

public interface AlumnoDao {

	int add(Alumno a) throws SQLException;

	Alumno getById(int id) throws SQLException;

	List<Alumno> getAll() throws SQLException;

	int update(Alumno a) throws SQLException;

	void delete(int id) throws SQLException;
}
