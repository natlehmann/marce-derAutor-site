package ar.com.marcelomingrone.derechosAutor.estadisticas.modelo;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
public class VisitaTecnica extends Entidad implements Comparable<VisitaTecnica> {
	
	private static final long serialVersionUID = 5996335390063846202L;

	@ManyToOne
	private Fuente fuente;

	@Temporal(TemporalType.TIMESTAMP)
	private Date fecha;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="visitaTecnica", fetch=FetchType.EAGER)
	private List<PuntoAuditoria> puntosAuditoria;

	public Fuente getFuente() {
		return fuente;
	}

	public void setFuente(Fuente fuente) {
		this.fuente = fuente;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public List<PuntoAuditoria> getPuntosAuditoria() {
		return puntosAuditoria;
	}

	public void setPuntosAuditoria(List<PuntoAuditoria> puntosAuditoria) {
		this.puntosAuditoria = puntosAuditoria;
	}

	/**
	 * Busca el punto auditoria que corresponde al item recibido por parametro
	 * @param item
	 * @return
	 */
	@Transient
	public PuntoAuditoria getPuntoAuditoria(ItemAuditoria item) {
		
		PuntoAuditoria resultado = null;
		
		if (this.puntosAuditoria != null) {
			for (PuntoAuditoria punto : this.puntosAuditoria) {
				
				if (punto.getItemAuditoria().equals(item)) {
					resultado = punto;
				}
			}
		}
		
		return resultado;
	}

	@Transient
	public void addPuntoAuditoria(PuntoAuditoria puntoAuditoria) {
		if (this.puntosAuditoria == null) {
			this.puntosAuditoria = new LinkedList<>();
		}
		
		this.puntosAuditoria.add(puntoAuditoria);
		puntoAuditoria.setVisitaTecnica(this);
		
	}
	
	
	@Transient
	public double getPuntajeTotal() {
		
		double sumatoria = 0;
		
		if (this.puntosAuditoria != null) {
		
			for (PuntoAuditoria punto : this.puntosAuditoria) {
				sumatoria += punto.getPuntajePonderado() != null ? punto.getPuntajePonderado() : 0;
			}
		}
		
		return sumatoria;
	}

	@Override
	public int compareTo(VisitaTecnica otra) {
		
		if (this.getPuntajeTotal() < otra.getPuntajeTotal()) {
			return 1;
		}
		
		if (this.getPuntajeTotal() == otra.getPuntajeTotal()) {
			return 0;
		}
		
		return -1;
	}
	

}
