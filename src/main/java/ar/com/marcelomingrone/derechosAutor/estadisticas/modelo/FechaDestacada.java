package ar.com.marcelomingrone.derechosAutor.estadisticas.modelo;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

@Entity
public class FechaDestacada extends Entidad {
	
	private static final long serialVersionUID = -2402582787082167671L;
	
	@NotNull
	private Date fecha;
	
	@NotNull @Size(max=255) @NotBlank
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
	
	@Override
	public List<String> getCamposAsList() {
		
		List<String> resultado = new LinkedList<>();
		resultado.add(format.format(this.fecha));
		resultado.add(this.descripcion);
		resultado.add(super.getLinksModificarDuplicarEliminar());
		
		return resultado;
	}

	@Transient
	public static String getCampoOrdenamiento(int indiceColumna) {
		
		switch(indiceColumna) {
		
		case 0:
			return "fecha";
		case 1:
			return "descripcion";
		default:
			return null;
		}
	}

}
