package ar.com.marcelomingrone.derechosAutor.estadisticas.modelo;

import java.util.Date;

import javax.persistence.Entity;

@Entity
public class FechaDestacada extends Entidad {
	
	private static final long serialVersionUID = -2402582787082167671L;

	private Date fecha;
	
	private String descripcion;

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
