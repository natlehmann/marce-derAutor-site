package ar.com.marcelomingrone.derechosAutor.estadisticas.modelo;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

@Entity
public class DerechoExternoReplica implements Derecho,Serializable {

	private static final long serialVersionUID = 7897124538591230242L;
	
	@Id
	private Long id;

	private String nombre;
	
	@ManyToOne
	private DerechoExternoReplica padre;
	
	@OneToMany(mappedBy="padre", fetch=FetchType.EAGER)
	private List<DerechoExternoReplica> hijos;
	
	@Transient
	private int nivel;
	
	@Transient
	private MontoTotalPorDerecho montoPorDerecho;
	
	
	public DerechoExternoReplica() {}
	
	public DerechoExternoReplica(Long id) {
		this.id = id;
	}
	
	public DerechoExternoReplica(Long id, String nombre, Long idPadre){
		this.id = id;
		this.nombre = nombre;
		this.padre = new DerechoExternoReplica(idPadre);
	}
	
	public DerechoExternoReplica(String nombre) {
		this.nombre = nombre;
	}

	public DerechoExternoReplica(Long id, String nombre) {
		this.id = id;
		this.nombre = nombre;
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public DerechoExternoReplica getPadre() {
		return padre;
	}

	public void setPadre(DerechoExternoReplica padre) {
		this.padre = padre;
	}

	public List<DerechoExternoReplica> getHijos() {
		return hijos;
	}

	public void setHijos(List<DerechoExternoReplica> hijos) {
		this.hijos = hijos;
	}
	
	public void addHijo(DerechoExternoReplica hijo) {

		if (this.hijos == null) {
			this.hijos = new LinkedList<>();
		}
		this.hijos.add(hijo);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DerechoExternoReplica other = (DerechoExternoReplica) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return this.nombre;
	}
	
	public int getNivel() {
		return nivel;
	}
	
	public void setNivel(int nivel) {
		this.nivel = nivel;
	}
	
	public void setMontoPorDerecho(MontoTotalPorDerecho montoPorDerecho) {
		this.montoPorDerecho = montoPorDerecho;
	}
	
	public MontoTotalPorDerecho getMontoPorDerecho() {
		if (this.montoPorDerecho != null) {
			return this.montoPorDerecho;
		}
		
		return new MontoTotalPorDerecho(this, false);
	}

	public boolean tieneMonto() {
		
		if (this.montoPorDerecho != null) {
			return true;
			
		} else {
			
			boolean hijoConMonto = false;
			
			if (this.hijos != null) {
				Iterator<DerechoExternoReplica> it = this.hijos.iterator();
				while (it.hasNext() && !hijoConMonto) {
					
					DerechoExternoReplica hijo = it.next();
					hijoConMonto = hijo.tieneMonto();
				}
			}
			
			return hijoConMonto;
		}
	}

	@Override
	public List<String> getCamposAsList() {
		return null;
	}

	@Override
	public int compareTo(Derecho otro) {
		return this.nombre.compareTo(otro.getNombre());
	}

	@Override
	public boolean isModificable() {
		return false;
	}

	@Override
	public String getLinksModificarEliminar() {
		return null;
	}


}
