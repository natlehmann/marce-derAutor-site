package ar.com.marcelomingrone.derechosAutor.estadisticas.modelo;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class Ranking extends Entidad {
	
	private static final long serialVersionUID = -1334219960075941782L;

	private Long ranking;
	
	@ManyToOne(cascade=CascadeType.MERGE)
	private Pais pais;
	
	private Integer trimestre;
	
	private Integer anio;
	
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

	public Integer getTrimestre() {
		return trimestre;
	}

	public void setTrimestre(Integer trimestre) {
		this.trimestre = trimestre;
	}

	public Integer getAnio() {
		return anio;
	}

	public void setAnio(Integer anio) {
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
	
	@Override
	public List<String> getCamposAsList() {
		
		List<String> campos = new LinkedList<>();
		campos.add(this.ranking + ". ");
		campos.add(this.autor.getNombre());
		campos.add(String.valueOf(cantidadUnidades));
		campos.add("$ " + this.montoPercibido);
		
		return campos;
	}
	

}
