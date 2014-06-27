package ar.com.marcelomingrone.derechosAutor.estadisticas.modelo;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import ar.com.marcelomingrone.derechosAutor.estadisticas.controllers.Utils;

@MappedSuperclass
public class Ranking extends Entidad {
	
	private static final long serialVersionUID = -1334219960075941782L;

	private Long ranking;
	
	@ManyToOne
	private Pais pais;
	
	private Integer trimestre;
	
	private Integer anio;
	
	@ManyToOne
	private Autor autor;
	
	private Long cantidadUnidades;
	
	private Double montoPercibido;
	
	public Ranking() {}
	
	public Ranking(Integer trimestre, Integer anio, Long idPais, Long ranking, 
			Long idAutor, String nombreAutor, Long cantidadUnidades, Double montoPercibido) {
		this.trimestre = trimestre;
		this.anio = anio;
		this.pais = new Pais(idPais);
		this.ranking = ranking;
		this.autor = new Autor(idAutor, nombreAutor);
		this.cantidadUnidades = cantidadUnidades;
		this.montoPercibido = montoPercibido;
	}
	

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
		campos.add("<a href='#' onclick='irAbsoluto(\"canciones/" + this.autor.getId() + "\")'>" + this.autor.getNombre() + "</a>");
		campos.add(String.valueOf(cantidadUnidades));
		campos.add("$ " + Utils.formatear(this.montoPercibido));
		
		return campos;
	}
	
	public void setIdPais(Long idPais) {
		if (this.pais == null) {
			this.pais = new Pais();
		}
		
		this.pais.setId(idPais);
	}
	

	public void setIdAutor(Long idAutor) {
		if (this.autor == null) {
			this.autor = new Autor();
		}
		
		this.autor.setId(idAutor);
	}
	
	public void setNombreAutor(String nombreAutor) {
		if (this.autor == null) {
			this.autor = new Autor();
		}
		
		this.autor.setNombre(nombreAutor);
	}
}
