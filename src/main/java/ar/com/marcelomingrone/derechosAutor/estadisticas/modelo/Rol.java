package ar.com.marcelomingrone.derechosAutor.estadisticas.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Rol extends Entidad {
	
	private static final long serialVersionUID = -5540105729788702092L;
	
	@Column(nullable=false, unique=true)
	private String nombre;
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
