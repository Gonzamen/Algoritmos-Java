package uy.edu.ort.obligatorio;

public class ArbolObreros {

	private NodoArbolObrero raiz;
	
	public ArbolObreros() {}
	
	public boolean registrarObrero(Obrero o) {
		if(esVacio()) {
			raiz = new NodoArbolObrero(o,1);
			return true;
		}
		else if(buscarObrero(o.getCedula()) == null)
			return raiz.agregar(raiz, o) != null;
		return false;
	}
	
	public NodoArbolObrero buscarObrero(String cedula) {
		return buscar(Obrero.formatearCedula(cedula),raiz);
	}
	
	private NodoArbolObrero buscar(int cedula, NodoArbolObrero nodo) {
		if(nodo==null)return null;
		else if(cedula==nodo.getObrero().getCedulaFormateada()) {
			return nodo;
		}
		else if(cedula > nodo.getObrero().getCedulaFormateada()) {
			return buscar(cedula, nodo.getDer());
		}
		else {
			return buscar(cedula,nodo.getIzq());
		}
	}
	
	public String listarObreros() {
		String listado = listarObreros(raiz);
		return listado.substring(0, listado.length() - 1);
	}
	
	private String listarObreros(NodoArbolObrero nodo) {
	    String listado = "";
	    if (nodo != null) {
	       listado += listarObreros(nodo.getIzq());
	       listado += nodo.getObrero().toString() + "|";
	       listado += listarObreros(nodo.getDer());
	    }
	    
	    return listado;
	    
	};
	
	public boolean esVacio() {
		return raiz == null;
	}
	
	
	
}
