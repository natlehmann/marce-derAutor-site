package ar.com.marcelomingrone.derechosAutor.estadisticas.modelo;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

@Entity
public class ItemAuditoria extends Entidad {
	
	private static final long serialVersionUID = -2329843813260982157L;

	@NotNull @Size(max=255) @NotBlank
	private String nombre;
	
	@NotNull
	private int orden;
	
	@NotNull
	private int puntaje;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getOrden() {
		return orden;
	}

	public void setOrden(int orden) {
		this.orden = orden;
	}

	public int getPuntaje() {
		return puntaje;
	}

	public void setPuntaje(int puntaje) {
		this.puntaje = puntaje;
	}
	

}
