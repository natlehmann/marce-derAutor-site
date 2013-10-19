package ar.com.marcelomingrone.derechosAutor.estadisticas.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Autor extends Entidad {
	
	private static final long serialVersionUID = 1427453454082466290L;

	@Column(unique=true)
	private Long idExterno;
	
	private String nombre;
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public Long getIdExterno() {
		return idExterno;
	}
	
	public void setIdExterno(Long idExterno) {
		this.idExterno = idExterno;
	}

}
