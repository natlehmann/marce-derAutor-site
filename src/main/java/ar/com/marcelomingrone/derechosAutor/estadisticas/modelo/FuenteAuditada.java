package ar.com.marcelomingrone.derechosAutor.estadisticas.modelo;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

@Entity
public class FuenteAuditada extends Entidad {	

	private static final long serialVersionUID = -7119190791043848053L;
	
	@NotNull @Size(max=255) @NotBlank
	@Column(nullable=false)
	private String nombre;
	
	public FuenteAuditada() {}
	
	public FuenteAuditada(Long id, String nombre) {
		this.setId(id);
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	@Transient
	@Override
	public List<String> getCamposAsList() {
		
		List<String> campos = new LinkedList<>();
		campos.add(this.nombre);
		campos.add(super.getLinksModificarEliminar());
		
		return campos;
	}

	@Transient
	public static String getCampoOrdenamiento(int indice) {
		return "nombre";
	}
}
