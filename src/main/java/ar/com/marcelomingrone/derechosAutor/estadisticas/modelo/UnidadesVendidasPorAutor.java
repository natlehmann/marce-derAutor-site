package ar.com.marcelomingrone.derechosAutor.estadisticas.modelo;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class UnidadesVendidasPorAutor extends Entidad {

	private static final long serialVersionUID = 6732625702256780282L;

	private long cantidadUnidades;
	
	private Integer anio;
	
	private Integer trimestre;
	
	@ManyToOne
	private Pais pais;
	
	@ManyToOne
	private Autor autor;
	
	public UnidadesVendidasPorAutor(){}

	public UnidadesVendidasPorAutor(Autor autor, Pais pais, Integer anio, Integer trimestre, 
			long cantidadUnidades) {
		super();
		this.autor = autor;
		this.pais = pais;
		this.anio = anio;
		this.trimestre = trimestre;
		this.cantidadUnidades = cantidadUnidades;
	}

	public Long getCantidadUnidades() {
		return cantidadUnidades;
	}

	public void setCantidadUnidades(Long cantidadUnidades) {
		this.cantidadUnidades = cantidadUnidades;
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
