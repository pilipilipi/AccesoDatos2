package es.pilar.dam.AccesoDatos2.testEj14;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import es.pilar.dam.AccesoDatos2.ejercicio14.dao.AlumnoDao;
import es.pilar.dam.AccesoDatos2.ejercicio14.dao.AlumnoDaoImpl;
import es.pilar.dam.AccesoDatos2.ejercicio14.model.Alumno;

class AlumnoDaoTest {

    private AlumnoDao dao;
    private Alumno alumnoTest;

    @BeforeEach
    void setUp() {
        dao = new AlumnoDaoImpl();
        int randomNia = (int)(Math.random() * 100000);
        alumnoTest = new Alumno(randomNia, "Test", "JUnit", "DAM", "1", 1, 'H', LocalDate.of(2000,1,1));
    }

    @AfterEach
    void tearDown() throws SQLException {
        if (alumnoTest.getId() > 0) {
            dao.delete(alumnoTest.getId());
        }
    }

    @Test
    void testAdd() throws SQLException {
        int filas = dao.add(alumnoTest);
        assertEquals(1, filas, "Debe insertarse 1 fila");
        assertTrue(alumnoTest.getId() > 0, "El ID se debe asignar automáticamente");
    }

    @Test
    void testGetById() throws SQLException {
        dao.add(alumnoTest);
        Alumno a = dao.getById(alumnoTest.getId());
        assertNotNull(a, "Debe encontrar el alumno por ID");
        assertEquals(alumnoTest.getNombre(), a.getNombre(), "El nombre debe coincidir");
    }

    @Test
    void testGetAll() throws SQLException {
        dao.add(alumnoTest);
        List<Alumno> lista = dao.getAll();
        assertNotNull(lista, "La lista no debe ser null");
        assertTrue(lista.size() > 0, "Debe haber al menos un alumno");
    }

    @Test
    void testUpdate() throws SQLException {
        dao.add(alumnoTest);
        alumnoTest.setNombre("Actualizado");
        int filas = dao.update(alumnoTest);
        assertEquals(1, filas, "Debe actualizar 1 fila");

        Alumno a = dao.getById(alumnoTest.getId());
        assertEquals("Actualizado", a.getNombre(), "El nombre debe estar actualizado");
    }

    @Test
    void testDelete() throws SQLException {
        dao.add(alumnoTest);
        dao.delete(alumnoTest.getId());

        Alumno a = dao.getById(alumnoTest.getId());
        assertNull(a, "Después de borrar, el alumno no debe encontrarse");
    }
}

