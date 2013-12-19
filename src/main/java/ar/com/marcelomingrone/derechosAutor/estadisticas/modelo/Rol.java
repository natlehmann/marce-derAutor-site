package ar.com.marcelomingrone.derechosAutor.estadisticas.modelo;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;

@Entity
public class Rol extends Entidad {
	
	private static final long serialVersionUID = -5540105729788702092L;
	
	@Column(nullable=false, unique=true)
	private String nombre;
	
	@ManyToMany(mappedBy="roles", fetch=FetchType.LAZY)
	private Set<Usuario> usuarios;
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public Set<Usuario> getUsuarios() {
		return usuarios;
	}
	
	public void setUsuarios(Set<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

}
