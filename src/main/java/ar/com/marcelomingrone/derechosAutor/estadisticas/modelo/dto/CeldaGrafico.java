package ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.dto;

import java.io.Serializable;

public class CeldaGrafico<T> implements Serializable {
	
	private static final long serialVersionUID = 146169590356886758L;

	/**
	 * Valor
	 */
	private T v;
	
	/**
	 * Version formateada del valor
	 */
	private String f;
	
	public CeldaGrafico(T valor) {
		this.v = valor;
	}

	public T getV() {
		return v;
	}

	public void setV(T v) {
		this.v = v;
	}

	public String getF() {
		return f;
	}

	public void setF(String f) {
		this.f = f;
	}
	

}
