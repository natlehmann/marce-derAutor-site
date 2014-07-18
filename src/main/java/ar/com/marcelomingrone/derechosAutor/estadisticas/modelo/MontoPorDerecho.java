package ar.com.marcelomingrone.derechosAutor.estadisticas.modelo;

import java.io.Serializable;

import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.data.Fuente;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.data.Pais;

public class MontoPorDerecho implements Serializable {
	
	private static final long serialVersionUID = -525761249398115112L;

	private Fuente fuente;
	
	private Derecho derecho;
	
	private Integer trimestre;
	
	private Pais pais;	
	
	private Integer anio;
	
	private Double monto;

	public MontoPorDerecho(Fuente fuente, Derecho derecho, Integer trimestre,
			Double monto) {
		super();
		this.fuente = fuente;
		this.derecho = derecho;
		this.trimestre = trimestre;
		this.monto = monto;
	}
	
	public MontoPorDerecho(Long idFuente, String nombreFuente, String nombreDerecho,
			Integer trimestre, Double monto) {
		
		Fuente fuente = new Fuente(idFuente, nombreFuente);
		this.setFuente(fuente);
		Derecho derecho = new Derecho(nombreDerecho);
		this.setDerecho(derecho);
		this.trimestre = trimestre;
		this.monto = monto;
	}

	public Fuente getFuente() {
		return fuente;
	}

	public void setFuente(Fuente fuente) {
		this.fuente = fuente;
	}

	public Derecho getDerecho() {
		return derecho;
	}

	public void setDerecho(Derecho derecho) {
		this.derecho = derecho;
	}

	public Integer getTrimestre() {
		return trimestre;
	}

	public void setTrimestre(Integer trimestre) {
		this.trimestre = trimestre;
	}

	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}

	public Integer getAnio() {
		return anio;
	}

	public void setAnio(Integer anio) {
		this.anio = anio;
	}

	public Double getMonto() {
		return monto;
	}

	public void setMonto(Double monto) {
		this.monto = monto;
	}

}
