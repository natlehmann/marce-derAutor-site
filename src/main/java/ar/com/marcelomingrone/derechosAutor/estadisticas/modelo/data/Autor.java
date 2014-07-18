package ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.data;

import java.io.Serializable;
import java.util.Comparator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Owners_All")
public class Autor implements Serializable {
	
	private static final long serialVersionUID = 1427453454082466290L;

	@Id
	@Column(name="OwnersID")
	private Long id;

	@Column(nullable=false, name="UniqueName", insertable=false, updatable=false)
	private String nombre;
	
	@Column(name="CountriesID", insertable=false, updatable=false)
	private Long idPais;
	
	@Column(name="CAE_IPI_Number", insertable=false, updatable=false)
	private String numeroCAE_IPI;
	
	@Column(name="PublisherName", insertable=false, updatable=false)
	private String nombrePublicador;
	
	@Column(name="WriterName", insertable=false, updatable=false)
	private String nombreEscritor;
	
	@Column(name="thirdpartiesName", insertable=false, updatable=false)
	private String nombreTerceros;
	
	@Column(name="PerformerName", insertable=false, updatable=false)
	private String nombreArtista;
	
	@Column(name="Type", insertable=false, updatable=false)
	private String tipo;
	
	
	public Autor() {}
	
	public Autor(Long id, String nombre) {
		this.setId(id);
		this.nombre = nombre;
	}

	public Autor(Long id) {
		this.setId(id);
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

	public String getNumeroCAE_IPI() {
		return numeroCAE_IPI;
	}

	public void setNumeroCAE_IPI(String numeroCAE_IPI) {
		this.numeroCAE_IPI = numeroCAE_IPI;
	}

	public String getNombrePublicador() {
		return nombrePublicador;
	}

	public void setNombrePublicador(String nombrePublicador) {
		this.nombrePublicador = nombrePublicador;
	}

	public String getNombreEscritor() {
		return nombreEscritor;
	}

	public void setNombreEscritor(String nombreEscritor) {
		this.nombreEscritor = nombreEscritor;
	}

	public String getNombreTerceros() {
		return nombreTerceros;
	}

	public void setNombreTerceros(String nombreTerceros) {
		this.nombreTerceros = nombreTerceros;
	}

	public String getNombreArtista() {
		return nombreArtista;
	}

	public void setNombreArtista(String nombreArtista) {
		this.nombreArtista = nombreArtista;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
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
		Autor other = (Autor) obj;
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
	
	public static class ComparadorPorNombre implements Comparator<Autor> {

		@Override
		public int compare(Autor autor1, Autor autor2) {
			return autor1.getNombre().compareTo(autor2.getNombre());
		}
		
	}
}
