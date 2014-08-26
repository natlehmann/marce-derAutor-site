package ar.com.marcelomingrone.derechosAutor.estadisticas.modelo;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import ar.com.marcelomingrone.derechosAutor.estadisticas.controllers.Utils;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.data.Autor;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.data.Pais;

@MappedSuperclass
public class Ranking extends Entidad {
	
	private static final long serialVersionUID = -1334219960075941782L;

	private Long ranking;
	
	@Column(name="pais_id")
	private Long idPais;
	
	private Integer trimestre;
	
	private Integer anio;
	
	@Column(name="autor_id")
	private Long idAutor;
	
	private String nombreAutor;
	
	private Long cantidadUnidades;
	
	private Double montoPercibido;
	
	public Ranking() {}
	
	public Ranking(Integer trimestre, Integer anio, Long idPais, Long ranking, 
			Long idAutor, String nombreAutor, Long cantidadUnidades, Double montoPercibido) {
		this.trimestre = trimestre;
		this.anio = anio;
		this.idPais = idPais;
		this.ranking = ranking;
		this.idAutor = idAutor;
		this.nombreAutor = nombreAutor;
		this.cantidadUnidades = cantidadUnidades;
		this.montoPercibido = montoPercibido;
	}
	

	public Long getRanking() {
		return ranking;
	}

	public void setRanking(Long ranking) {
		this.ranking = ranking;
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
		campos.add("<a href='#' onclick='irAbsoluto(\"canciones/" + this.idAutor + "\")'>" + this.nombreAutor + "</a>");
		campos.add(String.valueOf(cantidadUnidades));
		campos.add("$ " + Utils.formatear(this.montoPercibido));
		
		return campos;
	}
	
	public void setIdAutor(Long idAutor) {
		this.idAutor = idAutor;
	}
	
	public Long getIdAutor() {
		return idAutor;
	}
	
	public void setIdPais(Long idPais) {
		this.idPais = idPais;
	}
	
	public Long getIdPais() {
		return idPais;
	}
	
	public void setNombreAutor(String nombreAutor) {
		this.nombreAutor = nombreAutor;
	}
	
	public String getNombreAutor() {
		return nombreAutor;
	}
	
	public Autor getAutor() {
		
		Autor autor = null;
		if (this.idAutor != null) {
			autor = new Autor(this.idAutor, this.nombreAutor);
		}
		
		return autor;
	}
	
	public Pais getPais() {
		
		Pais pais = null;
		if (this.idPais != null) {
			pais = new Pais(this.idPais);
		}
		
		return pais;
	}
}
