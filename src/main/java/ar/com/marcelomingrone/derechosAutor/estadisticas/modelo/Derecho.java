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
public class Derecho implements Serializable, Comparable<Derecho>, Listable {
	
	private static final long serialVersionUID = -7609645649807916121L;
	
	@Id
	@NotNull @Size(max=255) @NotBlank
	private String nombre;
	
	private boolean modificable;
	
	public Derecho(){}
	
	public Derecho(String nombre) {
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public boolean isModificable() {
		return modificable;
	}
	
	public void setModificable(boolean modificable) {
		this.modificable = modificable;
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
		Derecho other = (Derecho) obj;
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

	@Override
	public int compareTo(Derecho otro) {
		return this.nombre.compareTo(otro.nombre);
	}

	@Override
	@Transient
	public List<String> getCamposAsList() {
		
		List<String> resultado = new LinkedList<>();
		resultado.add(this.nombre);
		resultado.add(this.getLinksModificarEliminar());
		
		return resultado;
	}

	private String getLinksModificarEliminar() {
		
		if (this.modificable) {
			return "<a href='#' onclick=\"confirmarEliminar('" + this.nombre + "')\">Eliminar</a>";
		
		} else {
			return "";
		}
	}

}
