package ar.com.marcelomingrone.derechosAutor.estadisticas.modelo;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
public class ReceptorNewsletter extends Entidad implements Listable {

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

	public static String getCampoOrdenamiento(int indiceColumna) {
		
		switch (indiceColumna) {
		case 0:
			return "r.usuario.nombre";

		case 1:
			return "r.usuario.email";
			
		case 2:
			return "r.fechaApertura";
		}
		
		return null;
	}
	
	@Override
	@Transient
	public List<String> getCamposAsList() {
		
		List<String> resultado = new LinkedList<>();
		resultado.add(this.usuario.getNombre());
		resultado.add(this.usuario.getEmail());
		resultado.add(this.fechaApertura != null ? format.format(this.fechaApertura) :  "");
		
		return resultado;
	}

}
