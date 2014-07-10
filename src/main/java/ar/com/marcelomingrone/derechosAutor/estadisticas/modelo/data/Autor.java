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
@Table(name="Performers")
public class Autor implements Serializable {
	
	private static final long serialVersionUID = 1427453454082466290L;

	@Id
	@Column(name="PerformersID")
	private Long id;

	@Column(nullable=false, name="PerformerName", insertable=false, updatable=false)
	private String nombre;
	
	@Column(name="Responsible", insertable=false, updatable=false)
	private String responsable;
	
	@Column(name="ConformationDate", insertable=false, updatable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaConformacion;
	
	@Column(name="DisolutionDate", insertable=false, updatable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaDisolucion;
	
	
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
	

	public String getResponsable() {
		return responsable;
	}

	public void setResponsable(String responsable) {
		this.responsable = responsable;
	}

	public Date getFechaConformacion() {
		return fechaConformacion;
	}

	public void setFechaConformacion(Date fechaConformacion) {
		this.fechaConformacion = fechaConformacion;
	}

	public Date getFechaDisolucion() {
		return fechaDisolucion;
	}

	public void setFechaDisolucion(Date fechaDisolucion) {
		this.fechaDisolucion = fechaDisolucion;
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
}
