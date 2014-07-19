package ar.com.marcelomingrone.derechosAutor.estadisticas.modelo;

import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.data.Autor;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.data.Cancion;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.data.DerechoExterno;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.data.Fuente;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.data.Pais;

public class DatosCancion extends Entidad {
	
	private static final long serialVersionUID = 918845849093099784L;

	private Long companyId;
	
	private Pais pais;
	
	private int trimestre;
	
	private int anio;
	
	private int formatId;
	
	private Autor autor;
	
	private Cancion cancion;
	
	private Fuente fuente;
	
	private DerechoExterno derecho;
	
	private long cantidadUnidades;
	
	private double montoPercibido;

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}

	public int getTrimestre() {
		return trimestre;
	}

	public void setTrimestre(int trimestre) {
		this.trimestre = trimestre;
	}

	public int getAnio() {
		return anio;
	}

	public void setAnio(int anio) {
		this.anio = anio;
	}

	public int getFormatId() {
		return formatId;
	}

	public void setFormatId(int formatId) {
		this.formatId = formatId;
	}

	public Autor getAutor() {
		return autor;
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}

	public Cancion getCancion() {
		return cancion;
	}

	public void setCancion(Cancion cancion) {
		this.cancion = cancion;
	}

	public Fuente getFuente() {
		return fuente;
	}

	public void setFuente(Fuente fuente) {
		this.fuente = fuente;
	}

	public long getCantidadUnidades() {
		return cantidadUnidades;
	}

	public void setCantidadUnidades(long cantidadUnidades) {
		this.cantidadUnidades = cantidadUnidades;
	}

	public double getMontoPercibido() {
		return montoPercibido;
	}

	public void setMontoPercibido(double montoPercibido) {
		this.montoPercibido = montoPercibido;
	}
	
	public void setDerecho(DerechoExterno derecho) {
		this.derecho = derecho;
	}
	
	public DerechoExterno getDerecho() {
		return derecho;
	}
	

}
