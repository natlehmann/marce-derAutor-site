package ar.com.marcelomingrone.derechosAutor.estadisticas.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class PercibidoPorAutor extends Entidad {
	
	private static final long serialVersionUID = -2953162102484559154L;

	private Double monto;
	
	@Column(nullable=false)
	private Integer anio;
	
	@Column(nullable=false)
	private Integer trimestre;
	
	@ManyToOne(optional=false)
	private Pais pais;
	
	@ManyToOne(optional=false)
	private Autor autor;
	
	public PercibidoPorAutor() {}

	public PercibidoPorAutor(Autor autor, Pais pais, Integer anio,
			Integer trimestre, Double monto) {
		
		this.autor = autor;
		this.pais = pais;
		this.anio = anio;
		this.trimestre = trimestre;
		this.monto = monto;
	}

	public Double getMonto() {
		return monto;
	}

	public void setMonto(Double monto) {
		this.monto = monto;
	}

	public Integer getAnio() {
		return anio;
	}

	public void setAnio(Integer anio) {
		this.anio = anio;
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

	public Autor getAutor() {
		return autor;
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}
	

}
