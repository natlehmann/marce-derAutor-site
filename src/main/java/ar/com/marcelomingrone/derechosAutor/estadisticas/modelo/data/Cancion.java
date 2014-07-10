package ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.data;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="Works")
public class Cancion implements Serializable {
	
	private static final long serialVersionUID = -2409001237715985018L;

	@Id
	@Column(name="WorksID")
	private Long id;

	@Column(nullable=false, name="WorkName", insertable=false, updatable=false)
	private String nombre;
	
	@Column(name="DateofCopyright", insertable=false, updatable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaCopyright;
	
	@Column(name="GenresID", insertable=false, updatable=false)
	private Long idGenero;
	
	@Column(name="LyricAdaptation", insertable=false, updatable=false)
	private String adaptacionLetra;
	
	@Column(name="MusicArrangement", insertable=false, updatable=false)
	private String arregloMusica;
	
	@Column(name="WorksID_Original", insertable=false, updatable=false)
	private Long idOriginal;
	
	@Column(name="ISWC", insertable=false, updatable=false)
	private String iswc;
	
	@Column(name="LanguagesID", insertable=false, updatable=false)
	private Long idLenguaje;
	
	@Column(name="Duration", insertable=false, updatable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date duracion;
	
	@Column(name="PerformersID", insertable=false, updatable=false)
	private Long idAutor;
	
	@Column(name="WorksID_Replaced", insertable=false, updatable=false)
	private Long idCancionReemplazada;
	
	@Column(name="CountriesID", insertable=false, updatable=false)
	private Long idPais;
	
	
	
	public Cancion() {}
	
	public Cancion(Long id, String nombre) {
		this.setId(id);
		this.nombre = nombre;
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
	

	public Date getFechaCopyright() {
		return fechaCopyright;
	}

	public void setFechaCopyright(Date fechaCopyright) {
		this.fechaCopyright = fechaCopyright;
	}

	public Long getIdGenero() {
		return idGenero;
	}

	public void setIdGenero(Long idGenero) {
		this.idGenero = idGenero;
	}

	public String getAdaptacionLetra() {
		return adaptacionLetra;
	}

	public void setAdaptacionLetra(String adaptacionLetra) {
		this.adaptacionLetra = adaptacionLetra;
	}

	public String getArregloMusica() {
		return arregloMusica;
	}

	public void setArregloMusica(String arregloMusica) {
		this.arregloMusica = arregloMusica;
	}

	public Long getIdOriginal() {
		return idOriginal;
	}

	public void setIdOriginal(Long idOriginal) {
		this.idOriginal = idOriginal;
	}

	public String getIswc() {
		return iswc;
	}

	public void setIswc(String iswc) {
		this.iswc = iswc;
	}

	public Long getIdLenguaje() {
		return idLenguaje;
	}

	public void setIdLenguaje(Long idLenguaje) {
		this.idLenguaje = idLenguaje;
	}

	public Date getDuracion() {
		return duracion;
	}

	public void setDuracion(Date duracion) {
		this.duracion = duracion;
	}

	public Long getIdAutor() {
		return idAutor;
	}

	public void setIdAutor(Long idAutor) {
		this.idAutor = idAutor;
	}

	public Long getIdCancionReemplazada() {
		return idCancionReemplazada;
	}

	public void setIdCancionReemplazada(Long idCancionReemplazada) {
		this.idCancionReemplazada = idCancionReemplazada;
	}

	public Long getIdPais() {
		return idPais;
	}

	public void setIdPais(Long idPais) {
		this.idPais = idPais;
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
		Cancion other = (Cancion) obj;
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
