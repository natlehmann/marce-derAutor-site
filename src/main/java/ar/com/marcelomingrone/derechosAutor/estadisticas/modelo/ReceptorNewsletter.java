package ar.com.marcelomingrone.derechosAutor.estadisticas.modelo;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class ReceptorNewsletter extends Entidad {

	private static final long serialVersionUID = -6077527835494159033L;

	@ManyToOne(optional=false)
	private Usuario usuario;
	
	@ManyToOne(optional=false)
	private EnvioNewsletter envioNewsletter;
	
	private Date fechaApertura;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public EnvioNewsletter getEnvioNewsletter() {
		return envioNewsletter;
	}

	public void setEnvioNewsletter(EnvioNewsletter envioNewsletter) {
		this.envioNewsletter = envioNewsletter;
	}

	public Date getFechaApertura() {
		return fechaApertura;
	}

	public void setFechaApertura(Date fechaApertura) {
		this.fechaApertura = fechaApertura;
	}

}
