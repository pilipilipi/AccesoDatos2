package es.pilar.dam.AccesoDatos2.ejercicio16.controller;

import es.pilar.dam.AccesoDatos2.ejercicio16.view.MenuImpl;
import es.pilar.dam.AccesoDatos2.ejercicio16.dao.ModeloDAO;
import es.pilar.dam.AccesoDatos2.ejercicio16.dao.ModeloDAOImpl;
import es.pilar.dam.AccesoDatos2.ejercicio16.view.Menu;

public class App {
	public static void main(String[] args) {
		Menu vista = new MenuImpl();
		ModeloDAO dao = new ModeloDAOImpl();
		Controller c = new Controller(dao, vista);
		c.ejecutar();
	}
}
