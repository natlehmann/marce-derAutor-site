package ar.com.marcelomingrone.derechosAutor.estadisticas.modelo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
public class FechaDestacada extends Entidad {
	
	private static final long serialVersionUID = -2402582787082167671L;
	
	private static transient SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

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
	
	@Override
	public List<String> getCamposAsList() {
		
		List<String> resultado = new LinkedList<>();
		resultado.add(format.format(this.fecha));
		resultado.add(this.descripcion);
		resultado.add(super.getLinksModificarEliminar());
		
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
