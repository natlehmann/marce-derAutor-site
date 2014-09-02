package ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.data;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.Derecho;

@Entity
@Table(name="Rights")
public class DerechoExterno implements Serializable, Derecho {
	
	private static final long serialVersionUID = -7609645649807916121L;
	
	@Id
	@Column(name="RightName", updatable=false, insertable=false)
	private String nombre;
	
	@Column(name="RightsID", updatable=false, insertable=false)
	private Long id;
	
	@Column(name="RightsID_Parent", updatable=false, insertable=false)
	private Long idDerechoPadre;
	
	@Column(name="RightsCode", updatable=false, insertable=false)
	private String codigo;
	
	@Column(name="RightUnit", updatable=false, insertable=false)
	private String unidad;
	
	@Column(name="IncomeTypeCode", updatable=false, insertable=false)
	private String codigoTipoIngreso;
	
	
	public DerechoExterno(){}
	
	public DerechoExterno(String nombre) {
		this.nombre = nombre;
	}

	public DerechoExterno(String nombre, Long id, Long idDerechoPadre) {
		super();
		this.nombre = nombre;
		this.id = id;
		this.idDerechoPadre = idDerechoPadre;
	}

	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	@Transient
	public boolean isModificable() {
		return false;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdDerechoPadre() {
		return idDerechoPadre;
	}

	public void setIdDerechoPadre(Long idDerechoPadre) {
		this.idDerechoPadre = idDerechoPadre;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getUnidad() {
		return unidad;
	}

	public void setUnidad(String unidad) {
		this.unidad = unidad;
	}

	public String getCodigoTipoIngreso() {
		return codigoTipoIngreso;
	}

	public void setCodigoTipoIngreso(String codigoTipoIngreso) {
		this.codigoTipoIngreso = codigoTipoIngreso;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
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
		DerechoExterno other = (DerechoExterno) obj;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return this.nombre;
	}

	@Override
	public int compareTo(Derecho otro) {
		return this.nombre.compareTo(otro.getNombre());
	}

	@Override
	@Transient
	public List<String> getCamposAsList() {
		
		List<String> resultado = new LinkedList<>();
		resultado.add(this.nombre);
		resultado.add(this.getLinksModificarEliminar());
		
		return resultado;
	}

	@Transient
	public String getLinksModificarEliminar() {
		return "";
	}
	
}
