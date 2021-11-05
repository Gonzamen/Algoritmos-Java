package uy.edu.ort.obligatorio;

public class NodoArbolObrero {

	private Obrero obrero;
	
	private NodoArbolObrero izq;
	
	private NodoArbolObrero der;
	
	private final int profundidad;
	
	public NodoArbolObrero(Obrero o, int profundidad) {
		obrero = o;
		this.profundidad = profundidad;
	}
	
	public NodoArbolObrero agregar(NodoArbolObrero nodo, Obrero o) {
		return agregar(nodo, o, 0);
	}
	
	private NodoArbolObrero agregar(NodoArbolObrero nodo, Obrero o, int aux) {
		aux ++;
		if(nodo==null){
			return new NodoArbolObrero(o,aux);
		}else if(nodo.obrero.equals(o)) {
			throw new IllegalArgumentException("El obrero ya existe");
		}else if(o.esMayor(nodo.obrero)) {
			nodo.der=agregar(nodo.der,o,aux);	
			return nodo;			
		}else {
			nodo.izq=agregar(nodo.izq, o,aux);
			return nodo;
		}
	}
	
	public static int getCantidad(NodoArbolObrero raiz) {
		if(raiz!=null) {
			return 1+ getCantidad(raiz.izq) + getCantidad(raiz.der);
		}
		return 0;
	}

	public Obrero getObrero() {
		return obrero;
	}

	public void setObrero(Obrero obrero) {
		this.obrero = obrero;
	}

	public NodoArbolObrero getIzq() {
		return izq;
	}

	public void setIzq(NodoArbolObrero izq) {
		this.izq = izq;
	}

	public NodoArbolObrero getDer() {
		return der;
	}

	public void setDer(NodoArbolObrero der) {
		this.der = der;
	}

	public int getProfundidad() {
		return profundidad;
	}
	
	
	
}
