package ar.com.marcelomingrone.derechosAutor.estadisticas.modelo;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class EnvioNewsletter extends Entidad {
	
	private static final long serialVersionUID = -4178979650510726649L;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	private Date fechaEnvio;
	
	@ManyToOne(optional=false)
	private Newsletter newsletter;
	
	@ManyToMany
	@JoinTable(name="EnvioNewsletter_Usuario", joinColumns=@JoinColumn(name="envioNewsletter_id"), 
		inverseJoinColumns=@JoinColumn(name="usuario_id"))
	private List<Usuario> receptores;

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

	public List<Usuario> getReceptores() {
		return receptores;
	}

	public void setReceptores(List<Usuario> receptores) {
		this.receptores = receptores;
	}

	public void agregarReceptor(Usuario receptor) {
		if (this.receptores == null) {
			this.receptores = new LinkedList<>();
		}
		this.receptores.add(receptor);
	}
}
