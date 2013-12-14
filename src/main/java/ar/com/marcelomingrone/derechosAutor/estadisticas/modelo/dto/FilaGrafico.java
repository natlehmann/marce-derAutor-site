package ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.dto;

import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@SuppressWarnings("rawtypes")
public class FilaGrafico implements Serializable {
	
	private static final long serialVersionUID = 774993312438621804L;
	
	private List<CeldaGrafico> c;
	
	public FilaGrafico() {
	}
	
	public FilaGrafico(CeldaGrafico ... celdas) {
		this.c = Arrays.asList(celdas);
	}
	
	public List<CeldaGrafico> getC() {
		return c;
	}
	
	public void setC(List<CeldaGrafico> c) {
		this.c = c;
	}
	
	public void agregarCelda(CeldaGrafico celda) {
		if (this.c == null) {
			this.c = new LinkedList<>();
		}
		
		this.c.add(celda);
	}

}
