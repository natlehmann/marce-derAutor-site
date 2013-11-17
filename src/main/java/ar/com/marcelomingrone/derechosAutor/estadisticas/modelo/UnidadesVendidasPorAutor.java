package ar.com.marcelomingrone.derechosAutor.estadisticas.modelo;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class UnidadesVendidasPorAutor implements Serializable, Listable { 

	private static final long serialVersionUID = 6732625702256780282L;

	private long cantidadUnidades;
	
	private String nombreAutor;
	
	private double monto;
	
	public UnidadesVendidasPorAutor() {}
	
	public UnidadesVendidasPorAutor(String nombreAutor, long cantidadUnidades) {
		super();
		this.cantidadUnidades = cantidadUnidades;
		this.nombreAutor = nombreAutor;
	}

	public UnidadesVendidasPorAutor(String nombreAutor, long cantidadUnidades,
			double monto) {
		this(nombreAutor, cantidadUnidades);
		this.monto = monto;
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
	
	public double getMonto() {
		return monto;
	}
	
	public void setMonto(double monto) {
		this.monto = monto;
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
		campos.add("$ " + this.monto);
		
		return campos;
	}
}
