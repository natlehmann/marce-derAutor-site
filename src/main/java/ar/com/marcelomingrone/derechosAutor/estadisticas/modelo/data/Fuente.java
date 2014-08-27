package ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.data;

import java.io.Serializable;
import java.util.Comparator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Sources_All")
public class Fuente implements Serializable {
	
	private static final long serialVersionUID = 5814976955024248628L;

	@Id
	@Column(name="SourcesID", insertable=false, updatable=false)
	private Long id;

	@Column(nullable=false, name="UniqueName", insertable=false, updatable=false)
	private String nombre;
	
	@Column(name="CountriesID", insertable=false, updatable=false)
	private Long idPais;
	
	@Column(name="SourcesTypesID", insertable=false, updatable=false)
	private Long idTipoFuente;
	
	@Column(name="SourcesID_Parent", insertable=false, updatable=false)
	private Long idFuentePadre;
	
	@Column(name="SourcesCode", insertable=false, updatable=false)
	private String codigo;
	
	@Column(name="AuthorSocietyName", insertable=false, updatable=false)
	private String nombreSociedadAutores;
	
	@Column(name="CustomerName", insertable=false, updatable=false)
	private String nombreCliente;
	
	@Column(name="Type", insertable=false, updatable=false)
	private String tipo;
	
	@Column(name="ISOCountryCode", insertable=false, updatable=false)
	private String codigoISOPais;
	
	@Column(name="CountryName", insertable=false, updatable=false)
	private String nombrePais;
	
	@Column(name="OfficialCountryName", insertable=false, updatable=false)
	private String nombreOficialPais;
	
	@Column(name="CurrenciesID", insertable=false, updatable=false)
	private Long idMoneda;
	
	@Column(name="Nationality", insertable=false, updatable=false)
	private String nacionalidad;
	
	@Column(name="FlagIMG", insertable=false, updatable=false)
	private String imagenBandera;
	
	
	public Fuente() {}
	
	public Fuente(Long id, String nombre) {
		this.setId(id);
		this.nombre = nombre;
	}

	public Fuente(Long idFuente) {
		this.id = idFuente;
	}

	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdPais() {
		return idPais;
	}

	public void setIdPais(Long idPais) {
		this.idPais = idPais;
	}

	public Long getIdTipoFuente() {
		return idTipoFuente;
	}

	public void setIdTipoFuente(Long idTipoFuente) {
		this.idTipoFuente = idTipoFuente;
	}

	public Long getIdFuentePadre() {
		return idFuentePadre;
	}

	public void setIdFuentePadre(Long idFuentePadre) {
		this.idFuentePadre = idFuentePadre;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombreSociedadAutores() {
		return nombreSociedadAutores;
	}

	public void setNombreSociedadAutores(String nombreSociedadAutores) {
		this.nombreSociedadAutores = nombreSociedadAutores;
	}

	public String getNombreCliente() {
		return nombreCliente;
	}

	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getCodigoISOPais() {
		return codigoISOPais;
	}

	public void setCodigoISOPais(String codigoISOPais) {
		this.codigoISOPais = codigoISOPais;
	}

	public String getNombrePais() {
		return nombrePais;
	}

	public void setNombrePais(String nombrePais) {
		this.nombrePais = nombrePais;
	}

	public String getNombreOficialPais() {
		return nombreOficialPais;
	}

	public void setNombreOficialPais(String nombreOficialPais) {
		this.nombreOficialPais = nombreOficialPais;
	}

	public Long getIdMoneda() {
		return idMoneda;
	}

	public void setIdMoneda(Long idMoneda) {
		this.idMoneda = idMoneda;
	}

	public String getNacionalidad() {
		return nacionalidad;
	}

	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}

	public String getImagenBandera() {
		return imagenBandera;
	}

	public void setImagenBandera(String imagenBandera) {
		this.imagenBandera = imagenBandera;
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
		Fuente other = (Fuente) obj;
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
	
	public static class ComparadorPorNombre implements Comparator<Fuente> {

		@Override
		public int compare(Fuente o1, Fuente o2) {
			return o1.getNombre().compareTo(o2.getNombre());
		}
		
	}
}
