package cola;

public class Cola<T> {
	
	private NodoCola<T> inicio;
	private NodoCola<T> fin;
	private int largo;

	public Cola() {
	}

	public boolean esVacia() {
		return inicio == null;
	}

	public void encolar(T dato) {
		if (this.inicio == null) {
			inicio = new NodoCola<T>(dato);
			fin = inicio;
		} else {
			fin.setSiguiente(new NodoCola<T>(dato));
			fin = fin.getSig();
		}
		this.largo++;
	}

	public T desencolar() {
		T dato = this.inicio.getDato();
		inicio = inicio.getSig();
		this.largo--;
		if(this.inicio == null) {
			fin = null;
		}
		return dato;
	}

}
