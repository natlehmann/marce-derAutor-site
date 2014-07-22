package ar.com.marcelomingrone.derechosAutor.estadisticas.modelo;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

@Entity
public class ReglamentoDeDistribucion extends Entidad {
	
	private static final long serialVersionUID = 4129794852798181760L;

	@NotNull
	@Column(name="fuente_id")
	private Long idFuente;
	
	private String nombreFuente;
	
	@NotNull
	@Column(name="derecho_nombre")
	private String nombreDerecho;
	
	@NotNull @Size(max=512) @NotBlank
	@Column(length=512, nullable=false)
	private String descripcion;
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date fecha;

	public Long getIdFuente() {
		return idFuente;
	}
	
	public void setIdFuente(Long idFuente) {
		this.idFuente = idFuente;
	}
	
	public String getNombreFuente() {
		return nombreFuente;
	}
	
	public void setNombreFuente(String nombreFuente) {
		this.nombreFuente = nombreFuente;
	}

	public String getNombreDerecho() {
		return nombreDerecho;
	}
	
	public void setNombreDerecho(String nombreDerecho) {
		this.nombreDerecho = nombreDerecho;
	}

	@Override
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	@Transient
	public static String getCampoOrdenamiento(int indice) {
		
		switch (indice) {
		case 0:
			return "nombreDerecho";
			
		case 1:
			return "descripcion";
			
		case 2:
			return "fecha";
		}
		
		return "nombreFuente";
	}
	
	@Override
	@Transient
	public List<String> getCamposAsList() {
		
		List<String> resultado = new LinkedList<>();
		resultado.add(this.nombreDerecho);
		resultado.add(this.getDescripcionCorta());
		resultado.add(format.format(this.fecha));
		
		if (esAdministrador()) {
			resultado.add(super.getLinksModificarEliminar());
		}
		
		return resultado;
	}
	
	
	@Transient
	public String getLinkModificar() {
		return "<a href='#' onclick=\"irAbsoluto('admin/reglamentoDeDistribucion/modificar?id=" 
				+ this.getId() + "')\" class='modificar-link' title='Modificar'></a> ";
	}

}
