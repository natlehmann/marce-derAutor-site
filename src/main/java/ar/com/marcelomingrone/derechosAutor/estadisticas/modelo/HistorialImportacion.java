package ar.com.marcelomingrone.derechosAutor.estadisticas.modelo;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

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
	
	private String resultado;
	
	@Transient
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

	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
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
}
