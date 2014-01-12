package ar.com.marcelomingrone.derechosAutor.estadisticas.modelo;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
public class EnvioNewsletter extends Entidad {
	
	private static final long serialVersionUID = -4178979650510726649L;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	private Date fechaEnvio;
	
	@ManyToOne(optional=false)
	private Newsletter newsletter;
	
	@OneToMany(mappedBy="envioNewsletter", cascade=CascadeType.ALL)
	private List<ReceptorNewsletter> receptores;
	
	@OneToMany(mappedBy="envioNewsletter", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private List<ErrorEnvioNewsletter> errores;
	
	private transient long cantidadReceptores;
	
	private transient long cantidadReceptoresActivos;

	public Date getFechaEnvio() {
		return fechaEnvio;
	}

	public void setFechaEnvio(Date fechaEnvio) {
		this.fechaEnvio = fechaEnvio;
	}

	public Newsletter getNewsletter() {
		return newsletter;
	}

	public void setNewsletter(Newsletter newsletter) {
		this.newsletter = newsletter;
	}

	public List<ReceptorNewsletter> getReceptores() {
		return receptores;
	}

	public void setReceptores(List<ReceptorNewsletter> receptores) {
		this.receptores = receptores;
	}

	public void agregarReceptor(Usuario usuario) {
		if (this.receptores == null) {
			this.receptores = new LinkedList<>();
		}
		
		ReceptorNewsletter receptorNewsletter = new ReceptorNewsletter();
		receptorNewsletter.setUsuario(usuario);
		receptorNewsletter.setEnvioNewsletter(this);
		
		this.receptores.add(receptorNewsletter);
	}
	
	public List<ErrorEnvioNewsletter> getErrores() {
		return errores;
	}
	
	public void setErrores(List<ErrorEnvioNewsletter> errores) {
		this.errores = errores;
	}
	
	public void agregarErrorEnvio(ErrorEnvioNewsletter error) {
		if (this.errores == null) {
			this.errores = new LinkedList<>();
		}
		this.errores.add(error);
		error.setEnvioNewsletter(this);
	}

	public void setCantidadReceptores(long cantidadReceptores) {
		this.cantidadReceptores = cantidadReceptores;		
	}
	
	public void setCantidadReceptoresActivos(long cantidadReceptoresActivos) {
		this.cantidadReceptoresActivos = cantidadReceptoresActivos;
	}
	
	@Transient
	public long getCantidadReceptores() {
		return cantidadReceptores;
	}
	
	@Transient
	public long getCantidadReceptoresActivos() {
		return cantidadReceptoresActivos;
	}
}
