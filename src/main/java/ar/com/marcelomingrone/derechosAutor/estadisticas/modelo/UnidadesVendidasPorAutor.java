package ar.com.marcelomingrone.derechosAutor.estadisticas.modelo;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class UnidadesVendidasPorAutor implements Serializable, Listable { 

	private static final long serialVersionUID = 6732625702256780282L;

	private long cantidadUnidades;
	
	private String nombreAutor;
	
	public UnidadesVendidasPorAutor() {}
	
	public UnidadesVendidasPorAutor(String nombreAutor, long cantidadUnidades) {
		super();
		this.cantidadUnidades = cantidadUnidades;
		this.nombreAutor = nombreAutor;
	}


	public Long getCantidadUnidades() {
		return cantidadUnidades;
	}

	public void setCantidadUnidades(Long cantidadUnidades) {
		this.cantidadUnidades = cantidadUnidades;
	}

	public String getNombreAutor() {
		return nombreAutor;
	}
	
	public void setNombreAutor(String nombreAutor) {
		this.nombreAutor = nombreAutor;
	}
	
	@Override
	public String toString() {
		return this.nombreAutor + " " + this.cantidadUnidades;
	}

	@Override
	public List<String> getCamposAsList() {
		
		List<String> campos = new LinkedList<>();
		campos.add(nombreAutor);
		campos.add(String.valueOf(cantidadUnidades));
		
		return campos;
	}
}
