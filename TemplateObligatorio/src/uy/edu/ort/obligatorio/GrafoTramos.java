package uy.edu.ort.obligatorio;

import cola.Cola;
import uy.edu.ort.obligatorio.ISistema.Estado;

public class GrafoTramos {

	private Tramo[][] tramos;

	private Poste[] postes;

	private int maxPuntos;

	private int cant = 0;

	public GrafoTramos(int maxPuntos) {
		this.maxPuntos = maxPuntos;
		this.postes = new Poste[maxPuntos];
		this.tramos = new Tramo[maxPuntos][maxPuntos];
		for (int i = 0; i < maxPuntos; i++) {
			for (int j = i; j < maxPuntos; j++) {
				this.tramos[i][j] = new Tramo();
				this.tramos[j][i] = this.tramos[i][j];
			}
		}
		this.cant = 0;
	}

	public boolean registrarPoste(Poste p) {
		int index = getPosLibre();
		if (index != -1) {
			postes[index] = p;
			this.cant++;
			return true;
		}
		return false;
	}

	public int getPosLibre() {
		for (int i = 0; i < maxPuntos; i++) {
			if (postes[i] == null)
				return i;
		}
		return -1;
	}

	public int buscarPoste(double cX, double cY) {
		for (int i = 0; i < maxPuntos; i++) {
			Poste p = postes[i];
			if (p != null) {
				if (p.getCoordenadaX() == cX && p.getCoordenadaY() == cY) {
					return i;
				}
			}

		}
		return -1;
	}

	public boolean registrarTramo(int indexPosI, int indexPosF, double metros) {
		if (existeTramo(indexPosI, indexPosF))
			return false;
		tramos[indexPosI][indexPosF].setExiste(true);
		tramos[indexPosI][indexPosF].setMetros(metros);
		tramos[indexPosI][indexPosF].setEstado(Estado.BUENO);
		return true;
	}

	public boolean existeTramo(int indexPosI, int indexPosF) {
		return tramos[indexPosI][indexPosF].getExiste();
	}

	public Tramo buscarTramo(int indexPosI, int indexPosF) {
		return tramos[indexPosI][indexPosF];
	}

	public boolean actualizarTramo(int indexPosI, int indexPosF, double metros, Estado estado) {
		if (existeTramo(indexPosI, indexPosF)) {
			tramos[indexPosI][indexPosF].setMetros(metros);
			tramos[indexPosI][indexPosF].setEstado(estado);
			return true;
		}
		return false;
	}

	public String getPostesByRango(double coordX, double coordY, int rango) {
		if (rango < 0 || rango > maxPuntos)
			return "";
		int indexInicio = buscarPoste(coordX, coordY);
		Cola<Integer> cola = new Cola<>();
		String ret = "";
		ret += postes[indexInicio].toString() + "|";
		boolean[] visitados = new boolean[maxPuntos];
		cola.encolar(indexInicio);
		visitados[indexInicio] = true;
		int na = 1, ns = 0;
		while (!cola.esVacia() && rango > 0) {
			int poste = cola.desencolar();
			na--;
			for (int i = 0; i < maxPuntos; i++) {
				if (tramos[poste][i].getExiste() && !visitados[i]) {
					ns++;
					cola.encolar(i);
					ret += postes[i].toString() + "|";
					visitados[i] = true;
				}
			}
			if (na == 0) {
				na = ns;
				ns = 0;
				rango--;
			}
		}
		return ret;
	}

	public CaminoCorto caminoMasCorto(int posOrigen, int posDestino) {
		boolean[] visitados = new boolean[this.maxPuntos];
		CaminoCorto[] distancia = new CaminoCorto[this.maxPuntos];
		String[] anterior = new String[this.maxPuntos];

		for (int i = 0; i < this.maxPuntos; i++) {
			distancia[i] = new CaminoCorto();
			distancia[i].setDistancia(Integer.MAX_VALUE);
			anterior[i] = "";
		}

		distancia[posOrigen].setDistancia(0);
		distancia[posOrigen].setCamino(postes[posOrigen].toString());
		for (int v = 0; v < this.cant; v++) {
			int pos = getPosteMasCercano(distancia, visitados);

			if (pos != -1) {
				visitados[pos] = true;
				
				for (int j = 0; j < maxPuntos; j++) {
					if (tramos[pos][j].getExiste() && !visitados[j]
							&& !tramos[pos][j].getEstado().equals(Estado.MALO)) {
						double distanciaNueva = distancia[pos].getDistancia() + tramos[pos][j].getMetros();
						if (distanciaNueva < distancia[j].getDistancia()) {
							String caminoNuevo = distancia[pos].getCamino() + "|" + postes[j].toString();
							distancia[j].setDistancia(distanciaNueva);
							distancia[j].setCamino(caminoNuevo);
							anterior[j] = postes[pos].getNombre();
						}
					}
				}
			}
		}
		return distancia[posDestino];
	}

	private int getPosteMasCercano(CaminoCorto[] distancias, boolean[] visitados) {
		int posMin = -1;
		double min = Double.MAX_VALUE;
		for (int i = 0; i < maxPuntos; i++) {
			if (!visitados[i] && distancias[i].getDistancia() < min) {
				min = distancias[i].getDistancia();
				posMin = i;
			}
		}
		return posMin;
	}

}
