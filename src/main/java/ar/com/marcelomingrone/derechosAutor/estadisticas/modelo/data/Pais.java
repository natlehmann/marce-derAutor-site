package ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Countries")
public class Pais implements Serializable {

	private static final long serialVersionUID = 140546104925383039L;
	
	@Id
	@Column(name="CountriesID", updatable=false, insertable=false)
	private Long id;

	@Column(nullable=false, unique=true, name="CountryName", updatable=false, insertable=false)
	private String nombre;
	
	@Column(length=10, name="ISOCountryCode", updatable=false, insertable=false)
	private String codigo;
	
	@Column(name="OfficialCountryName", updatable=false, insertable=false)
	private String nombreOficial;
	
	@Column(name="Nationality", updatable=false, insertable=false)
	private String nacionalidad;
	
	@Column(name="CurrenciesID", updatable=false, insertable=false)
	private Integer idMoneda;
	
	@Column(name="FlagIMG", updatable=false, insertable=false)
	private String pathBandera;
	
	@Column(name="NumericTISCode", updatable=false, insertable=false)
	private Integer codigoNumerico;
	
	@Column(name="AuthorPerfCollectFactor", updatable=false, insertable=false)
	private Integer collectFactor;
	
	@Column(name="NOPRCode", updatable=false, insertable=false)
	private String codigoNOPR;
	
	public Pais() {}
	
	public Pais(Long id, String nombre) {
		this.id = id;
		this.nombre = nombre;
	}

	public Pais(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombreOficial() {
		return nombreOficial;
	}

	public void setNombreOficial(String nombreOficial) {
		this.nombreOficial = nombreOficial;
	}

	public String getNacionalidad() {
		return nacionalidad;
	}

	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}

	public Integer getIdMoneda() {
		return idMoneda;
	}

	public void setIdMoneda(Integer idMoneda) {
		this.idMoneda = idMoneda;
	}

	public String getPathBandera() {
		return pathBandera;
	}

	public void setPathBandera(String pathBandera) {
		this.pathBandera = pathBandera;
	}

	public Integer getCodigoNumerico() {
		return codigoNumerico;
	}

	public void setCodigoNumerico(Integer codigoNumerico) {
		this.codigoNumerico = codigoNumerico;
	}

	public Integer getCollectFactor() {
		return collectFactor;
	}

	public void setCollectFactor(Integer collectFactor) {
		this.collectFactor = collectFactor;
	}

	public String getCodigoNOPR() {
		return codigoNOPR;
	}

	public void setCodigoNOPR(String codigoNOPR) {
		this.codigoNOPR = codigoNOPR;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pais other = (Pais) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return this.nombre + " (id:" + this.id + ")";
	}
}
