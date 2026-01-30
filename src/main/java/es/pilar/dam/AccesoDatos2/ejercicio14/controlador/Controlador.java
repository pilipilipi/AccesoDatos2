package es.pilar.dam.AccesoDatos2.ejercicio14.controlador;

import es.pilar.dam.AccesoDatos2.ejercicio14.dao.AlumnoDao;
import es.pilar.dam.AccesoDatos2.ejercicio14.dao.AlumnoDaoImpl;
import es.pilar.dam.AccesoDatos2.ejercicio14.view.Menu;

public class Controlador {

    public static void main(String[] args) {
        AlumnoDao modelo = new AlumnoDaoImpl();
        Menu vista = new Menu();
        new Controlador().ejecutar(modelo, vista);
    }

    public void ejecutar(AlumnoDao alumno, Menu interfaz) {
        interfaz.init(alumno);
    }
}
