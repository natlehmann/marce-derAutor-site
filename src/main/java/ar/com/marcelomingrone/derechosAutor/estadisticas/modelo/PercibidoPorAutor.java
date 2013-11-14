package ar.com.marcelomingrone.derechosAutor.estadisticas.modelo;

import java.io.Serializable;

public class PercibidoPorAutor implements Serializable {
	
	private static final long serialVersionUID = -2953162102484559154L;

	private Double monto;
	
	private String nombreAutor;
	
	public PercibidoPorAutor() {}

	public PercibidoPorAutor(String nombreAutor, Double monto) {
		super();
		this.nombreAutor = nombreAutor;
		this.monto = monto;
	}


	public Double getMonto() {
		return monto;
	}

	public void setMonto(Double monto) {
		this.monto = monto;
	}

	public String getNombreAutor() {
		return nombreAutor;
	}
	
	public void setNombreAutor(String nombreAutor) {
		this.nombreAutor = nombreAutor;
	}
	
	@Override
	public String toString() {
		return this.nombreAutor + " " + this.monto;
	}
	

}
