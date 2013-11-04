package ar.com.marcelomingrone.derechosAutor.estadisticas.modelo;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class Usuario extends Entidad {
	
	private static final long serialVersionUID = -3577120786983842957L;

	@Column(nullable=false, unique=true)
	private String nombre;
	
	@Column(nullable=false)
	private String password;
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name="Usuario_Rol", joinColumns=@JoinColumn(name="usuario_id"), inverseJoinColumns=@JoinColumn(name="rol_id"))
	private Set<Rol> roles;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	

}
