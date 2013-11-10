package ar.com.marcelomingrone.derechosAutor.estadisticas.modelo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class ArchivoImportacion implements Serializable {
	
	private static final long serialVersionUID = 3566003538659724771L;

	private static final long TAMANIO_APROX_UNA_LINEA = 138;
	
	private Date inicio;
	private Date fin;
	
	private InputStream inputStream;
	
	private long cantidadTotalLineasEstimadas;
	
	private long lineaProcesada;
	
	public void setNuevoArchivo(String nombreArchivo) throws FileNotFoundException {
		
		this.limpiar();
		
		File file = new File(nombreArchivo);
		this.inputStream = new FileInputStream(file);
		
		this.cantidadTotalLineasEstimadas = file.getTotalSpace() / TAMANIO_APROX_UNA_LINEA;
	}
	
	
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
	
	public InputStream getInputStream() {
		return inputStream;
	}

	public void limpiar() {
		this.inicio = null;
		this.fin = null;	
		this.lineaProcesada = 0;
	}
	
	public long getProgreso() {
		return this.lineaProcesada * 100 / this.cantidadTotalLineasEstimadas;
	}
	
	public void setLineaProcesada(long lineaProcesada) {
		this.lineaProcesada = lineaProcesada;
	}


}
