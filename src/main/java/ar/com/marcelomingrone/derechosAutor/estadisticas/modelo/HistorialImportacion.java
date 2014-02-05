package ar.com.marcelomingrone.derechosAutor.estadisticas.modelo;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import ar.com.marcelomingrone.derechosAutor.estadisticas.servicios.ConversionUtils;

@Entity
public class HistorialImportacion extends Entidad {

	private static final long serialVersionUID = 3481949363439096457L;
	
	private String nombreArchivo;

	@Temporal(TemporalType.TIMESTAMP)
	private Date inicio;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date fin;
	
	/**
	 * en milisegundos
	 */
	private Long duracion;
	
	/**
	 * en bytes
	 */
	private Long tamanioArchivo;
	
	/**
	 * en milisegundos
	 */
	private Long duracionEstimada1024bytes;
	
	private Long duracionEstimada;

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

	public Long getDuracion() {
		return duracion;
	}

	public void setDuracion(Long duracion) {
		this.duracion = duracion;
	}

	public Long getTamanioArchivo() {
		return tamanioArchivo;
	}

	public void setTamanioArchivo(Long tamanioArchivo) {
		this.tamanioArchivo = tamanioArchivo;
	}

	public Long getDuracionEstimada1024bytes() {
		return duracionEstimada1024bytes;
	}

	public void setDuracionEstimada1024bytes(Long duracionEstimada1024bytes) {
		this.duracionEstimada1024bytes = duracionEstimada1024bytes;
	}
	
	public Long getDuracionEstimada() {
		return duracionEstimada;
	}
	
	public void setDuracionEstimada(Long duracionEstimada) {
		this.duracionEstimada = duracionEstimada;
	}
	
	public String getNombreArchivo() {
		return nombreArchivo;
	}
	
	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	public static String getCampoOrdenamiento(int indice) {
		
		switch (indice) {
		case 0:
			return "nombreArchivo";
			
		case 1:
			return "inicio";
			
		case 2:
			return "fin";
			
		case 3:
			return "duracion";
			
		case 4:
			return "tamanioArchivo";

		default:
			return null;
		}
	}
	
	@Override
	public List<String> getCamposAsList() {
		
		List<String> resultado = new LinkedList<>();
		
		resultado.add(this.nombreArchivo);
		resultado.add(inicio != null ? datetimeFormat.format(inicio) : "");
		resultado.add(fin != null ? datetimeFormat.format(fin) : "");
		resultado.add(duracion != null ? ConversionUtils.convertirATexto(this.duracion) : "");
		resultado.add(tamanioArchivo != null ? ConversionUtils.humanReadableByteCount(this.tamanioArchivo) : "");
		
		return resultado;
	}
}
