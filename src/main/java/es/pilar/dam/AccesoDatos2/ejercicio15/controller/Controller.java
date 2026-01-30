package es.pilar.dam.AccesoDatos2.ejercicio15.controller;

import es.pilar.dam.AccesoDatos2.ejercicio15.dao.ModeloDAO;

public class Controller {
	private ModeloDAO modelo;
    private IVista vista;

    public Controlador(IAlumnoDAO modelo, IVista vista) {
        this.modelo = modelo;
        this.vista = vista;
    }

    public void ejecutar() {
}
