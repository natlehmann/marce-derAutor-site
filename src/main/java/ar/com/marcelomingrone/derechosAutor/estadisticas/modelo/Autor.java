package ar.com.marcelomingrone.derechosAutor.estadisticas.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Autor implements Serializable {
	
	private static final long serialVersionUID = 1427453454082466290L;

	@Id
	private Long id;

	@Column(nullable=false)
	private String nombre;
	
	public Autor() {}
	
	public Autor(Long id, String nombre) {
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
