package ar.com.marcelomingrone.derechosAutor.estadisticas.modelo;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class HistorialImportacion extends Entidad {

	private static final long serialVersionUID = 3481949363439096457L;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date inicio;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date fin;
	
	private Long cantidadRegistrosMontos;
	
	private Long cantidadRegistrosEjecuciones;
	
	private String estado;
	
	private String resultadoEjecucion;

	public Date getInicio() {
		return inicio;
	}

	public void setInicio(Date inicio) {
		this.inicio = inicio;
	}

	public Date getFin() {
		return fin;
	}

	public void setFin(Date fin) {
		this.fin = fin;
	}

	public Long getCantidadRegistrosMontos() {
		return cantidadRegistrosMontos;
	}

	public void setCantidadRegistrosMontos(Long cantidadRegistrosMontos) {
		this.cantidadRegistrosMontos = cantidadRegistrosMontos;
	}

	public Long getCantidadRegistrosEjecuciones() {
		return cantidadRegistrosEjecuciones;
	}

	public void setCantidadRegistrosEjecuciones(Long cantidadRegistrosEjecuciones) {
		this.cantidadRegistrosEjecuciones = cantidadRegistrosEjecuciones;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getResultadoEjecucion() {
		return resultadoEjecucion;
	}

	public void setResultadoEjecucion(String resultadoEjecucion) {
		this.resultadoEjecucion = resultadoEjecucion;
	}

	public static String getCampoOrdenamiento(int indice) {
		
		switch (indice) {
		case 0:
			return "inicio";
			
		case 1:
			return "fin";
			
		case 2:
			return "cantidadRegistrosMontos";
			
		case 3:
			return "cantidadRegistrosEjecuciones";
			
		case 4:
			return "estado";
			
		case 5:
			return "resultadoEjecucion";

		default:
			return null;
		}
	}
	
	@Override
	public List<String> getCamposAsList() {
		
		List<String> resultado = new LinkedList<>();
		
		resultado.add(inicio != null ? datetimeFormat.format(inicio) : "");
		resultado.add(fin != null ? datetimeFormat.format(fin) : "");
		resultado.add(cantidadRegistrosMontos != null ? String.valueOf(cantidadRegistrosMontos) : "");
		resultado.add(cantidadRegistrosEjecuciones != null ? String.valueOf(cantidadRegistrosEjecuciones) : "");
		resultado.add(estado);
		resultado.add(resultadoEjecucion);
		
		return resultado;
	}
	
	
	public static enum Estado {
		
		EJECUTADO,
		NO_EJECUTADO;
	}
}
