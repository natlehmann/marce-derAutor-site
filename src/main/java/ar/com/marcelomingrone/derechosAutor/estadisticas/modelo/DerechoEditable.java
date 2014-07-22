package ar.com.marcelomingrone.derechosAutor.estadisticas.modelo;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

@Entity
public class DerechoEditable implements Serializable, Derecho {

	private static final long serialVersionUID = -8183891542385822911L;
	
	@Id
	@NotNull @Size(max=255) @NotBlank
	private String nombre;
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	@Transient
	public List<String> getCamposAsList() {
		
		List<String> resultado = new LinkedList<>();
		resultado.add(this.nombre);
		resultado.add(this.getLinksModificarEliminar());
		
		return resultado;
	}

	@Override
	@Transient
	public boolean isModificable() {
		return true;
	}

	@Override
	@Transient
	public String getLinksModificarEliminar() {
		
		return "<a href='#' onclick=\"confirmarEliminar('" + this.nombre 
				+ "')\" class='eliminar-link' title='Eliminar'></a>";
	}

	@Override
	public int compareTo(Derecho otro) {
		return this.nombre.compareTo(otro.getNombre());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
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
		DerechoEditable other = (DerechoEditable) obj;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return this.nombre;
	}
}
