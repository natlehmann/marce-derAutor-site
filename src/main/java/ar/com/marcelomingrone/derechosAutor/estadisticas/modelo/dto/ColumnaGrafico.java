package ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.dto;

import java.io.Serializable;

public class ColumnaGrafico implements Serializable {
	
	private static final long serialVersionUID = 6386225163979024512L;

	private String type;
	
	private String id;
	
	private String label;
	
	private String role;
	
	public ColumnaGrafico(){}
	
	public ColumnaGrafico(String label) {
		this.label = label;
		this.type = TipoColumna.TEXTO.getType();
	}
	
	public ColumnaGrafico(String label, TipoColumna tipo) {
		this.label = label;
		this.type = tipo.getType();
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	public String getRole() {
		return role;
	}
	
	public void setRole(String role) {
		this.role = role;
	}
	
	public static enum TipoColumna {
		
		BOOLEANO	("boolean"),
		NUMERICO	("number"),
		TEXTO		("string"),
		FECHA		("date"),
		FECHA_HORA	("datetime"),
		HORA		("timeofday");
		
		private String type;
		
		private TipoColumna(String type) {
			this.type = type;
		}
		
		public String getType() {
			return type;
		}
	}
	
}
