package ar.com.marcelomingrone.derechosAutor.estadisticas.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class ErrorEnvioNewsletter extends Entidad {

	private static final long serialVersionUID = 7798637210172219716L;
	

	@Column(nullable=false, length=512)
	private String error;
	
	@ManyToOne
	private EnvioNewsletter envioNewsletter;
	
	public ErrorEnvioNewsletter() {}

	public ErrorEnvioNewsletter(String error) {
		this.error = error;
	}
	

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public EnvioNewsletter getEnvioNewsletter() {
		return envioNewsletter;
	}

	public void setEnvioNewsletter(EnvioNewsletter envioNewsletter) {
		this.envioNewsletter = envioNewsletter;
	}
	
	

}
