package ar.com.marcelomingrone.derechosAutor.estadisticas.modelo;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

@Entity
public class Newsletter extends Entidad {
	
	private static final long serialVersionUID = -2052208950694120931L;
	
	@Column(nullable=false)
	@NotNull @Size(max=255) @NotBlank
	private String subject;
	
	@Column(nullable=false)
	@NotNull @NotBlank
	@Lob
	private String contenido;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	private Date fechaCreacion;
	
	@OneToMany(mappedBy="newsletter", cascade=CascadeType.ALL)
	private List<EnvioNewsletter> envios;

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}
	
	public List<EnvioNewsletter> getEnvios() {
		return envios;
	}
	
	public void setEnvios(List<EnvioNewsletter> envios) {
		this.envios = envios;
	}
	
	public void agregarEnvio(EnvioNewsletter envio) {
		if (this.envios == null) {
			this.envios = new LinkedList<>();
		}
		this.envios.add(envio);
		envio.setNewsletter(this);
	}
	
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	
	public Date getFechaCreacion() {
		return fechaCreacion;
	}
	
	@Override
	public List<String> getCamposAsList() {
		
		List<String> resultado = new LinkedList<>();
		resultado.add(format.format(this.fechaCreacion));
		resultado.add(this.subject);
		resultado.add(this.getDescripcionCorta());
		resultado.add(super.getLinksModificarDuplicarEliminar() 
				+ this.getLinkProbarEnvio() + this.getLinkVerInfo() + this.getLinkEnviar());
		
		return resultado;
	}
	
	@Override
	@Transient
	public String getDescripcion() {
		return this.contenido.replaceAll("\\<.*?>", "");
	}
	
	@Transient
	private String getLinkVerInfo() {
		return "<a href='verInfo?id=" + this.getId() + "' class='ver-info-link' title='Ver info'></a> ";
	}

	@Transient
	private String getLinkProbarEnvio() {
		return "<a href='probarEnvio?id=" + this.getId() + "' class='probar-envio-link' title='Probar envío'></a> ";
	}

	@Transient
	public String getLinkEnviar() {
		return "<a href='#' onclick='confirmarEnviar(" + this.getId() + ")' class='enviar-link' title='Enviar'></a> ";
	}

	@Transient
	public static String getCampoOrdenamiento(int indiceColumna) {
		
		switch(indiceColumna) {
		
		case 0:
			return "fechaCreacion";
		case 1:
			return "subject";
		case 2:
			return "contenido";
		default:
			return null;
		}
	}

}
