package ar.com.marcelomingrone.derechosAutor.estadisticas.modelo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

@MappedSuperclass
public class Entidad implements Serializable, Listable {
	
	private static final long serialVersionUID = -8982130090046767829L;
	
	protected static transient SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
	
	
	@Id
	@GeneratedValue
	private Long id;
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getId() {
		return id;
	}
	
	@Transient
	public String getDescripcionCorta() {
		
		if (this.getDescripcion() != null && this.getDescripcion().length() > 200) {
			
			String corta = this.getDescripcion().substring(0, 100);
			int indiceUltimoEspacio = corta.lastIndexOf(" ");
			
			if (indiceUltimoEspacio > 0) {
				corta = corta.substring(0, indiceUltimoEspacio);
			}
			
			return corta + "... <a href='#' onclick='verMas(this," + this.getId() + ")' class='ver-mas-link' title='Ver más'></a>";
		}
		
		return this.getDescripcion();
	}
	
	public String getLinkReducirDescripcion() {
		return " <a href='#' onclick='reducirDescripcion(this," + this.getId() + ")' class='ver-menos-link' title='Reducir'></a>";
	}

	@Transient
	public String getDescripcion() {
		return "";
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
		Entidad other = (Entidad) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return this.getClass().getName() + "(id:" + this.id + ")";
	}

	@Transient
	public List<String> getCamposAsList() {
		return new LinkedList<String>();
	}

	@Transient
	public String getLinksModificarEliminar() {
		return this.getLinkModificar() + this.getLinkEliminar();
	}
	
	@Transient
	public String getLinksModificarDuplicarEliminar() {
		return this.getLinkModificar() + this.getLinkDuplicar() + this.getLinkEliminar();
	}
	
	@Transient
	public String getLinkModificar() {
		return "<a href='modificar?id=" + this.id + "' class='modificar-link' title='Modificar'></a> ";
	}
	
	@Transient
	public String getLinkEliminar() {
		return "<a href='#' onclick='confirmarEliminar(" + this.id + ")' class='eliminar-link' title='Eliminar'></a>";
	}
	
	@Transient
	public String getLinkDuplicar() {
		return "<a href='duplicar?id=" + this.id + "' class='duplicar-link' title='Duplicar'></a> ";
	}
	
	@Transient
	protected boolean esAdministrador() {

		SecurityContext context = SecurityContextHolder.getContext();
        if (context == null) {
            return false;
        }

        Authentication authentication = context.getAuthentication();
        if (authentication == null) {
            return false;
        }

        for (GrantedAuthority auth : authentication.getAuthorities()) {
            if ("administrador".equals(auth.getAuthority())) {
                return true;
            }
        }

        return false;
    }
	
}
