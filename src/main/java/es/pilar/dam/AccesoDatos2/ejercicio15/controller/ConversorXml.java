package es.pilar.dam.AccesoDatos2.ejercicio15.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import es.pilar.dam.AccesoDatos2.ejercicio15.model.Alumno;
import es.pilar.dam.AccesoDatos2.ejercicio15.model.Grupo;

public class ConversorXml implements Conversor {

	private final File f = new File("GruposXMLEj15.xml");

	@Override
	public void exportarAlumnos(List<Alumno> alumnos) {

	}

	@Override
	public void exportarGruposYAlumnos(List<Grupo> grupos, List<Alumno> alumnos) {

		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

			Document doc = builder.newDocument();
			Element raiz = doc.createElement("grupos");
			doc.appendChild(raiz);

			for (Grupo g : grupos) {

				Element grupoXML = doc.createElement("grupo");
				grupoXML.setAttribute("id", String.valueOf(g.getId()));
				grupoXML.setAttribute("nombre", g.getNombre());

				for (Alumno a : alumnos) {

					if (a.getGrupo() != null && a.getGrupo().getId() == g.getId()) {

						Element alumnoXML = doc.createElement("alumno");

						alumnoXML.setAttribute("id", String.valueOf(a.getId()));
						alumnoXML.setAttribute("nia", String.valueOf(a.getNia()));
						alumnoXML.setAttribute("nombre", a.getNombre());
						alumnoXML.setAttribute("apellidos", a.getApellidos());
						alumnoXML.setAttribute("curso", a.getCurso());
						alumnoXML.setAttribute("ciclo", a.getCiclo());
						alumnoXML.setAttribute("genero", String.valueOf(a.getGenero()));
						alumnoXML.setAttribute("fecha", a.getFechaString());

						grupoXML.appendChild(alumnoXML);
					}
				}

				raiz.appendChild(grupoXML);
			}

			Transformer transformer = TransformerFactory.newInstance().newTransformer();

			transformer.setOutputProperty(OutputKeys.INDENT, "yes");

			transformer.transform(new DOMSource(doc), new StreamResult(f));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public List<Grupo> importarGrupos() {

		List<Grupo> grupos = new ArrayList<>();

		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

			Document doc = builder.parse(f);
			doc.getDocumentElement().normalize();

			var nodosGrupo = doc.getElementsByTagName("grupo");

			for (int i = 0; i < nodosGrupo.getLength(); i++) {

				Element grupoXML = (Element) nodosGrupo.item(i);

				Grupo g = new Grupo("");
				g.setId(Integer.parseInt(grupoXML.getAttribute("id")));
				g.setNombre(grupoXML.getAttribute("nombre"));

				grupos.add(g);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return grupos;
	}

	@Override
	public List<Alumno> importarAlumnos() {

		List<Alumno> alumnos = new ArrayList<>();

		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

			Document doc = builder.parse(f);
			doc.getDocumentElement().normalize();

			var nodosGrupo = doc.getElementsByTagName("grupo");

			for (int i = 0; i < nodosGrupo.getLength(); i++) {

				Element grupoXML = (Element) nodosGrupo.item(i);

				Grupo g = new Grupo("");				
				g.setId(Integer.parseInt(grupoXML.getAttribute("id")));
				g.setNombre(grupoXML.getAttribute("nombre"));

				var nodosAlumno = grupoXML.getElementsByTagName("alumno");

				for (int j = 0; j < nodosAlumno.getLength(); j++) {

					Element alumnoXML = (Element) nodosAlumno.item(j);

					int nia = Integer.parseInt(alumnoXML.getAttribute("nia"));
					String nombre = alumnoXML.getAttribute("nombre");
					String apellidos = alumnoXML.getAttribute("apellidos");
					String curso = alumnoXML.getAttribute("curso");
					String ciclo = alumnoXML.getAttribute("ciclo");
					char genero = alumnoXML.getAttribute("genero").charAt(0);
					String fecha = alumnoXML.getAttribute("fecha");

					Alumno alumno = new Alumno(nia, nombre, apellidos, ciclo, curso, g, genero, fecha);

					if (alumnoXML.hasAttribute("id")) {
						alumno.setId(Integer.parseInt(alumnoXML.getAttribute("id")));
					}

					alumnos.add(alumno);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return alumnos;
	}

	@Override
	public void exportarGrupos(List<Grupo> grupos) {
		// TODO Auto-generated method stub

	}
}
