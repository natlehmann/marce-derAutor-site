package ar.com.marcelomingrone.derechosAutor.estadisticas.modelo;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class RankingArtistasMasEjecutados extends Entidad {
	
	private static final long serialVersionUID = -1334219960075941782L;

	private Long ranking;
	
	@ManyToOne(cascade=CascadeType.MERGE)
	private Pais pais;
	
	private int trimestre;
	
	private int anio;
	
	@ManyToOne(cascade=CascadeType.MERGE)
	private Autor autor;
	
	private Long cantidadUnidades;
	
	private Double montoPercibido;

	public Long getRanking() {
		return ranking;
	}

	public void setRanking(Long ranking) {
		this.ranking = ranking;
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

	public Autor getAutor() {
		return autor;
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}

	public Long getCantidadUnidades() {
		return cantidadUnidades;
	}

	public void setCantidadUnidades(Long cantidadUnidades) {
		this.cantidadUnidades = cantidadUnidades;
	}

	public Double getMontoPercibido() {
		return montoPercibido;
	}

	public void setMontoPercibido(Double montoPercibido) {
		this.montoPercibido = montoPercibido;
	}
	
	

}
