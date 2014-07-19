package ar.com.marcelomingrone.derechosAutor.estadisticas.modelo;


public interface Derecho extends Listable, Comparable<Derecho> {
	
	String getNombre();
	
	void setNombre(String nombre);
	
	boolean isModificable();
	
	String getLinksModificarEliminar();
}
