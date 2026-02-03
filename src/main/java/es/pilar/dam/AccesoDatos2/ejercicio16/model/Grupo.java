package es.pilar.dam.AccesoDatos2.ejercicio16.model;

import java.io.Serializable;

public class Grupo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private int id;
	private String nombre;

	public Grupo(String nombre) {
		this.nombre = nombre;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return "Grupo [id=" + id + ", nombre=" + nombre + "]";
	}

}
