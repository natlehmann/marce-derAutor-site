package ar.com.marcelomingrone.derechosAutor.estadisticas.modelo;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
public class PuntoAuditoria extends Entidad implements Comparable<PuntoAuditoria> {
	
	private static final long serialVersionUID = 8172666382691455610L;

	@ManyToOne
	private ItemAuditoria itemAuditoria;
	
	private int puntajeAsignado;
	
	@ManyToOne
	private VisitaTecnica visitaTecnica;

	public ItemAuditoria getItemAuditoria() {
		return itemAuditoria;
	}

	public void setItemAuditoria(ItemAuditoria itemAuditoria) {
		this.itemAuditoria = itemAuditoria;
	}

	public int getPuntajeAsignado() {
		return puntajeAsignado;
	}

	public void setPuntajeAsignado(int puntajeAsignado) {
		this.puntajeAsignado = puntajeAsignado;
	}
	
	public VisitaTecnica getVisitaTecnica() {
		return visitaTecnica;
	}
	
	public void setVisitaTecnica(VisitaTecnica visitaTecnica) {
		this.visitaTecnica = visitaTecnica;
	}
	
	@Transient
	public Double getPuntajePonderado() {
		
		Double puntajePonderado = null;
		
		if (this.puntajeAsignado >= 0 && this.puntajeAsignado <= 10 && this.itemAuditoria != null) {
			puntajePonderado = this.puntajeAsignado * (double)this.itemAuditoria.getPuntaje() / 100D;
		}
		
		return puntajePonderado;
		
	}

	@Override
	public int compareTo(PuntoAuditoria otro) {
		if (this.getItemAuditoria().getOrden() < otro.getItemAuditoria().getOrden() ) {
			return -1;
			
		} else {
			if (this.getItemAuditoria().getOrden() == otro.getItemAuditoria().getOrden() ) {
				return 0;
			}
		}
		
		return 1;
	}
	

}
