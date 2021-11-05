package uy.edu.ort.obligatorio;

import uy.edu.ort.obligatorio.ISistema.Estado;


public class Tramo {

	private double metros;
	
	private Estado estado; 
	
	private boolean existe;

	public double getMetros() {
		return metros;
	}

	public void setMetros(double metros) {
		this.metros = metros;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public boolean getExiste() {
		return existe;
	}

	public void setExiste(boolean existe) {
		this.existe = existe;
	}
	
	
	
}
