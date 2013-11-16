package ar.com.marcelomingrone.derechosAutor.estadisticas.modelo;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

@MappedSuperclass
public class Entidad implements Serializable {
	
	private static final long serialVersionUID = -8982130090046767829L;
	
	
	@Id
	@GeneratedValue
	private Long id;
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getId() {
		return id;
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
		Entidad other = (Entidad) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return this.getClass().getName() + "(id:" + this.id + ")";
	}

	@Transient
	public List<String> getCamposAsList() {
		return new LinkedList<String>();
	}

	@Transient
	public String getLinksModificarEliminar() {
		return "<a href='modificar?id=" + this.id + "'>Modificar</a>";
	}
	
}
