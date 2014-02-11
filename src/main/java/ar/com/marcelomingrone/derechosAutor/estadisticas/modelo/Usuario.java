package ar.com.marcelomingrone.derechosAutor.estadisticas.modelo;

import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

@Entity
public class Usuario extends Entidad implements Listable {
	
	private static final long serialVersionUID = -3577120786983842957L;

	@NotNull @Size(max=255) @NotBlank
	private String nombreApellido;
	
	@Column(unique=true, nullable=true)
	private String username;
	
	@Column(nullable=false)
	private String password;
	
	@Email @NotBlank @Size(max=255)
	private String email;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaBaja;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaEliminacion;
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name="Usuario_Rol", joinColumns=@JoinColumn(name="usuario_id"), inverseJoinColumns=@JoinColumn(name="rol_id"))
	private Set<Rol> roles;

	public String getNombreApellido() {
		return nombreApellido;
	}
	
	public void setNombreApellido(String nombreApellido) {
		this.nombreApellido = nombreApellido;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public Date getFechaBaja() {
		return fechaBaja;
	}
	
	public void setFechaBaja(Date fechaBaja) {
		this.fechaBaja = fechaBaja;
	}
	
	public Date getFechaEliminacion() {
		return fechaEliminacion;
	}
	
	public void setFechaEliminacion(Date fechaEliminacion) {
		this.fechaEliminacion = fechaEliminacion;
	}

	public static String getCampoOrdenamiento(int indice) {
		
		switch (indice) {
		case 0:
			return "nombreApellido";

		case 1:
			return "email";
			
		case 2:
			return "fechaBaja";
		}
		
		return null;
	}
	
	@Override
	public List<String> getCamposAsList() {
		
		List<String> campos = new LinkedList<>();
		campos.add(this.nombreApellido);
		campos.add(this.email);
		campos.add(this.fechaBaja != null ? format.format(this.fechaBaja) :  "");
		campos.add(getLinksModificarEliminar());
		
		return campos;
	}
	
	@Transient
	public String getLinkEliminar() {
		return "<a href='#' onclick='confirmarEliminarUsuario(" 
				+ this.getId() + ")' class='eliminar-link' title='Eliminar'></a>";
	}

	public void agregarRol(Rol rol) {
		if (this.roles == null) {
			this.roles = new HashSet<>();
		}
		
		this.roles.add(rol);		
	}

}
