package uy.edu.ort.obligatorio;

import uy.edu.ort.obligatorio.Retorno.Resultado;

public class Sistema implements ISistema {

	private ArbolObreros arbolObreros;
	private GrafoTramos grafo;
	
	@Override
	public Retorno inicializarSistema(int maxPuntos) {
		Retorno ret = new Retorno(Resultado.ERROR_1);
		if(maxPuntos > 0) {
			grafo = new GrafoTramos(maxPuntos);
			arbolObreros = new ArbolObreros();
			ret.resultado = Resultado.OK;
		}
		return ret;
	}

	@Override
	public Retorno destruirSistema() {
		arbolObreros = null;
		grafo = null;
		return new Retorno(Resultado.OK);
	}

	@Override
	public Retorno registrarObrero(String cedula, String nombre) {
		Obrero o = new Obrero(nombre, cedula);
		if(!o.validar()) return new Retorno(Resultado.ERROR_1);
		if(arbolObreros.registrarObrero(o)) {
			return new Retorno(Resultado.OK);
		}
		return new Retorno(Resultado.ERROR_2);
	}

	@Override
	public Retorno buscarObrero(String cedula) {
		if(!Obrero.validarCedula(cedula))return new Retorno(Resultado.ERROR_1);
		NodoArbolObrero busqueda = arbolObreros.buscarObrero(cedula);
		if(busqueda != null) {
			Retorno ret = new Retorno(Resultado.OK);
			ret.valorString = busqueda.getObrero().toString();
			ret.valorEntero = busqueda.getProfundidad();
			return ret;
		}
		return new Retorno(Resultado.ERROR_2);
	}

	@Override
	public Retorno listarObreros() {
		Retorno ret = new Retorno(Resultado.OK);
		ret.valorString = arbolObreros.listarObreros();
		return ret;
	}

	@Override
	public Retorno registrarPoste(double coordX, double coordY, String nombre) {
		if(grafo.buscarPoste(coordX, coordY) != -1) return new Retorno(Resultado.ERROR_2);
		if(grafo.registrarPoste(new Poste(coordX,coordY,nombre))) {
			Retorno ret = new Retorno(Resultado.OK);
			return ret;
		}
		return new Retorno(Resultado.ERROR_1);
	}

	@Override
	public Retorno registrarTramo(double coordXi, double coordYi, double coordXf, double coordYf, double metros) {
		if(metros <= 0) return new Retorno(Resultado.ERROR_1);
		int posI = grafo.buscarPoste(coordXi, coordYi), posF = grafo.buscarPoste(coordXf, coordYf);
		if(posI == -1 || posF == -1) return new Retorno(Resultado.ERROR_2);
		if(grafo.registrarTramo(posI, posF, metros)) {
			Retorno ret = new Retorno(Resultado.OK);
			return ret;
		}
		return new Retorno(Resultado.ERROR_3);
	}

	@Override
	public Retorno actualizarTramo(double coordXi, double coordYi, double coordXf, double coordYf, double metros,
			Estado estado) {
		if(metros <= 0 || estado == null) return new Retorno(Resultado.ERROR_1);
		int posI = grafo.buscarPoste(coordXi, coordYi), posF = grafo.buscarPoste(coordXf, coordYf);
		if(posI == -1 || posF == -1) return new Retorno(Resultado.ERROR_2);
		if(grafo.actualizarTramo(posI, posF, metros, estado)) {
			Retorno ret = new Retorno(Resultado.OK);
			return ret;
		}
		return new Retorno(Resultado.ERROR_3);
	}

	@Override
	public Retorno cuadrillaAuditoria(double coordXi, double coordYi, int cantTramosMaximo) {
		int poste = grafo.buscarPoste(coordXi, coordYi);
		if( poste == -1) return new Retorno(Resultado.ERROR_1);
		Retorno ret = new Retorno(Resultado.OK);
		ret.valorString = grafo.getPostesByRango(coordXi, coordYi, cantTramosMaximo);
		return ret;
	}

	@Override
	public Retorno caminoMinimoEnBuenEstado(double coordXi, double coordYi, double coordXf, double coordYf) {
		int posI = grafo.buscarPoste(coordXi, coordYi), posF = grafo.buscarPoste(coordXf, coordYf);
		if(posI == -1 || posF == -1) return new Retorno(Resultado.ERROR_1);
		Retorno ret = new Retorno(Resultado.OK);
		CaminoCorto camino = grafo.caminoMasCorto(posI, posF);
		if(camino.getDistancia() == 0) return new Retorno(Resultado.ERROR_2);
		ret.valorEntero = (int)camino.getDistancia();
		ret.valorString = camino.getCamino();
		return ret;
	}

}
