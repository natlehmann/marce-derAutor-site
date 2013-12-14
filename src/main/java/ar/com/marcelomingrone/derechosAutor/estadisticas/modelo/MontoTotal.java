package ar.com.marcelomingrone.derechosAutor.estadisticas.modelo;

import java.io.Serializable;
import java.util.Comparator;

import javax.persistence.Transient;

public class MontoTotal implements Serializable{
	
	private static final long serialVersionUID = 404715572876004689L;
	
	private String clave = "anio";

	private Pais pais;
	
	private Integer trimestre;
	
	private Integer anio;
	
	private Double monto;
	
	public MontoTotal() {}
	
	public MontoTotal(Integer anio, Double monto) {
		this.clave = "anio";
		this.anio = anio;
		this.monto = monto;
	}
	
	public MontoTotal(Integer anio, Integer trimestre, Double monto) {
		this.clave = "trimestre";
		this.anio = anio;
		this.monto = monto;
		this.trimestre = trimestre;
	}
	
	public MontoTotal(Integer anio, Integer trimestre, Pais pais, Double monto) {
		this.clave = "pais";
		this.anio = anio;
		this.monto = monto;
		this.trimestre = trimestre;
		this.pais = pais;
	}


	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}

	public Integer getTrimestre() {
		return trimestre;
	}

	public void setTrimestre(Integer trimestre) {
		this.trimestre = trimestre;
	}

	public Integer getAnio() {
		return anio;
	}

	public void setAnio(Integer anio) {
		this.anio = anio;
	}

	public Double getMonto() {
		return monto;
	}

	public void setMonto(Double monto) {
		this.monto = monto;
	}
	
	public String getClave() {
		return clave;
	}
	
	public void setClave(String clave) {
		this.clave = clave;
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if (obj == null || !(obj instanceof MontoTotal) ) {
			return false;
		}
		
		MontoTotal otro = (MontoTotal)obj;
		
		switch(this.clave) {
		
		case "anio" :
			return otro.getAnio() != null && this.getAnio() != null && otro.getAnio().equals(this.getAnio());
		
		case "trimestre" :
			return otro.getTrimestre() != null && this.getTrimestre() != null
				&& otro.getTrimestre().equals(this.getTrimestre());
		
		case "pais" :
			return otro.getPais() != null && this.getPais() != null && this.getPais().equals(otro.getPais());
		}
		
		return false;
	}
	
	public static class ComparadorPorAnio implements Comparator<MontoTotal> {

		@Override
		public int compare(MontoTotal o1, MontoTotal o2) {
			return o1.getAnio().compareTo(o2.getAnio());
		}		
	}
	
	public static class ComparadorPorTrimestre implements Comparator<MontoTotal> {

		@Override
		public int compare(MontoTotal o1, MontoTotal o2) {
			return o1.getTrimestre().compareTo(o2.getTrimestre());
		}		
	}
	
	public static class ComparadorPorPais implements Comparator<MontoTotal> {

		@Override
		public int compare(MontoTotal o1, MontoTotal o2) {
			return o1.getPais().getNombre().compareTo(o2.getPais().getNombre());
		}		
	}

	public double calcularPorcentaje(double total) {
		
		double porcentaje = 0.0;
		if (this.monto != null && total > 0) {
			
			porcentaje = this.monto * 100 / total;
		}
		
		return porcentaje;
	}

	@Transient
	public String getCriterio() {
		
		switch(this.clave) {
		
		case "anio" :
			return String.valueOf(this.getAnio());
		
		case "trimestre" :
			return String.valueOf(this.getTrimestre());
		
		case "pais" :
			return this.getPais().getNombre();
		}
		
		return null;
	}

}
