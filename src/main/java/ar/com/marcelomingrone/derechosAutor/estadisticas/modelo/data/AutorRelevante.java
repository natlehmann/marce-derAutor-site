package ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="VIEW_AuthorsIds")
public class AutorRelevante implements Serializable {

	private static final long serialVersionUID = -2548111755490308919L;
	
	@Id
	@Column(name="OwnersID")
	private Long idAutor;
	
	public Long getIdAutor() {
		return idAutor;
	}
	
	public void setIdAutor(Long idAutor) {
		this.idAutor = idAutor;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idAutor == null) ? 0 : idAutor.hashCode());
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
		AutorRelevante other = (AutorRelevante) obj;
		if (idAutor == null) {
			if (other.idAutor != null)
				return false;
		} else if (!idAutor.equals(other.idAutor))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "autor " + this.idAutor;
	}

}
