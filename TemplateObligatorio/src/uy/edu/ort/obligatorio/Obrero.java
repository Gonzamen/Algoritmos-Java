package uy.edu.ort.obligatorio;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Obrero {
	
	private String nombre;
	
	private String cedula;
	
	public Obrero(String nombre, String cedula) {
		this.nombre = nombre;
		this.cedula = cedula;
	}

	public boolean esMayor(Obrero obrero) {
		return formatearCedula(this.cedula) > formatearCedula(obrero.getCedula());
	}

	public static int formatearCedula(String cedula) {
		String c = cedula.replace(".", "");
		return Integer.parseInt(c.replace("-", ""));
	}
	
	public int getCedulaFormateada() {
		return formatearCedula(cedula);
	}
	
	public boolean validar() {
		return validarCedula(this.cedula);
	}
	
	public static boolean validarCedula(String cedula) {
		return Pattern.matches("\\d\\.\\d\\d\\d\\.\\d\\d\\d-\\d", cedula) || Pattern.matches("\\d\\d\\d\\.\\d\\d\\d-\\d", cedula);
//		Matcher mat = pat.matcher(cedula);
//		return mat.matches();
	}
	
	@Override
	public String toString() {
		return this.cedula + ";" + this.nombre;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}
	
	
	
}
