package uy.edu.ort.obligatorio;

public class Poste {
	
	private double coordenadaX;
	
	private double coordenadaY;
	
	private String nombre;

	public Poste(double coordenadaX, double coordenadaY, String nombre) {
		super();
		this.coordenadaX = coordenadaX;
		this.coordenadaY = coordenadaY;
		this.nombre = nombre;
	}

	public double getCoordenadaX() {
		return coordenadaX;
	}

	public double getCoordenadaY() {
		return coordenadaY;
	}

	public void setCoordenadaY(double coordenadaY) {
		this.coordenadaY = coordenadaY;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setCoordenadaX(double coordenadaX) {
		this.coordenadaX = coordenadaX;
	}
	
	@Override
	public String toString() {
		return ""+coordenadaX+";"+coordenadaY+";"+nombre;
	}
	
}
