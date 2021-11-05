package uy.edu.ort.obligatorio;

public interface ISistema {

	public enum Estado {
		BUENO, REGULAR, MALO
	};
	
	Retorno inicializarSistema (int maxPuntos);
	
	Retorno destruirSistema();
	
	Retorno registrarObrero(String cedula, String nombre);

	Retorno buscarObrero(String cedula);

	Retorno listarObreros();

	Retorno registrarPoste(double coordX, double coordY, String nombre);
	
	Retorno registrarTramo(double coordXi, double coordYi, double coordXf, double coordYf, double metros);
	
	Retorno actualizarTramo(double coordXi, double coordYi, double coordXf, double coordYf, double metros, Estado estado);
	
	Retorno cuadrillaAuditoria(double coordXi, double coordYi, int cantTramosMaximo);
	
	Retorno caminoMinimoEnBuenEstado(double coordXi, double coordYi, double coordXf, double coordYf);
	
}
