package es.pilar.dam.AccesoDatos2.ejercicio16.controller;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import es.pilar.dam.AccesoDatos2.ejercicio16.model.Alumno;
import es.pilar.dam.AccesoDatos2.ejercicio16.model.Grupo;

public class ConversorTxt implements Conversor {

	private final File f = new File("AlumnosEj15A.dat");
	private final File fg = new File("AlumnosEj15G.dat");

	@Override
	public void exportarAlumnos(List<Alumno> alumnos) {

		try (ObjectOutputStream salidaDatos = new ObjectOutputStream(new FileOutputStream(f))) {

			for (Alumno a : alumnos) {
				salidaDatos.writeObject(a);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void exportarGrupos(List<Grupo> grupos) {
		try (ObjectOutputStream salidaDatos = new ObjectOutputStream(new FileOutputStream(fg))) {

			for (Grupo g : grupos) {
				salidaDatos.writeObject(g);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Alumno> importarAlumnos() {

		List<Alumno> alumnos = new ArrayList<>();

		try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(f))) {

			while (true) {
				try {
					Alumno a = (Alumno) entrada.readObject();
					alumnos.add(a);
				} catch (EOFException e) {
					break;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return alumnos;
	}

	@Override
	public List<Grupo> importarGrupos() {
		
		List<Grupo> grupos = new ArrayList<>();

		try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(fg))) {

			while (true) {
				try {
					Grupo g = (Grupo) entrada.readObject();
					grupos.add(g);
				} catch (EOFException e) {
					break;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return grupos;
	}

	@Override
	public void exportarGruposYAlumnos(List<Grupo> grupos, List<Alumno> alumnos) {
		// TODO Auto-generated method stub
		
	}

}
