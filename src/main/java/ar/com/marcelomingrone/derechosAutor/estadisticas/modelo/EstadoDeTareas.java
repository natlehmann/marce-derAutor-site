package ar.com.marcelomingrone.derechosAutor.estadisticas.modelo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

@Entity
public class EstadoDeTareas extends Entidad {
	
	private static final long serialVersionUID = -7748233282423382225L;

	@ManyToOne
	private Autor autor;
	
	@NotNull @Size(max=255) @NotBlank
	private String asunto;
	
	@ManyToOne
	private Fuente fuente;
	
	private String estado;
	
	private String prioridad;
	
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	private Date fecha;
	
	@Column(length=1024)
	@NotNull @Size(max=1024) @NotBlank
	private String descripcion;
	
	@Column(length=512)
	@NotNull @Size(max=512) @NotBlank
	private String comentario;

	public Autor getAutor() {
		return autor;
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}

	public String getAsunto() {
		return asunto;
	}

	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}

	public Fuente getFuente() {
		return fuente;
	}

	public void setFuente(Fuente fuente) {
		this.fuente = fuente;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getPrioridad() {
		return prioridad;
	}

	public void setPrioridad(String prioridad) {
		this.prioridad = prioridad;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	
	@Transient
	public String getDescripcionCorta() {
		
		if (this.descripcion != null && this.descripcion.length() > 200) {
			
			String corta = this.descripcion.substring(0, 200);
			int indiceUltimoEspacio = corta.lastIndexOf(" ");
			
			if (indiceUltimoEspacio > 0) {
				corta = corta.substring(0, indiceUltimoEspacio);
			}
			
			return corta + "... <a href='#' onclick='verMas(this," + this.getId() + ")'>+</a>";
		}
		
		return this.descripcion;
	}
	
	public static enum Estado {
		ABIERTO,
		CERRADO;
		
		public static Estado getFromString(String nombre) {
			Estado resultado = null;
			
			for (Estado estado : Estado.values()) {
				if (nombre.equalsIgnoreCase(estado.name())) {
					resultado = estado;
				}
			}
			
			return resultado;
		}
	}
	
	public static enum Prioridad {
		BAJA,
		MEDIA,
		ALTA;
		
		public static Prioridad getFromString(String nombre) {
			Prioridad resultado = null;
			
			for (Prioridad prioridad : Prioridad.values()) {
				if (nombre.equalsIgnoreCase(prioridad.name())) {
					resultado = prioridad;
				}
			}
			
			return resultado;
		}
	}

}
