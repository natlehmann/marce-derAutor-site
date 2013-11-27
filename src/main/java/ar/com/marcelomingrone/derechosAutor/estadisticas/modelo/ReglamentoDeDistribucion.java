package ar.com.marcelomingrone.derechosAutor.estadisticas.modelo;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
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
	@ManyToOne(optional=false)
	private Fuente fuente;
	
	@NotNull
	@ManyToOne(optional=false)
	private Derecho derecho;
	
	@NotNull @Size(max=512) @NotBlank
	@Column(length=512, nullable=false)
	private String descripcion;
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date fecha;

	public Fuente getFuente() {
		return fuente;
	}

	public void setFuente(Fuente fuente) {
		this.fuente = fuente;
	}

	public Derecho getDerecho() {
		return derecho;
	}

	public void setDerecho(Derecho derecho) {
		this.derecho = derecho;
	}

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
			return "derecho.nombre";
			
		case 1:
			return "descripcion";
			
		case 2:
			return "fecha";
		}
		
		return "fuente.nombre";
	}
	
	@Override
	@Transient
	public List<String> getCamposAsList() {
		
		List<String> resultado = new LinkedList<>();
		resultado.add(this.derecho.getNombre());
		resultado.add(this.descripcion);
		resultado.add(format.format(this.fecha));
		resultado.add(this.fuente.getNombre());
		resultado.add(super.getLinksModificarEliminar());
		
		return resultado;
	}

}
