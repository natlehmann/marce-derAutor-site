package ar.com.marcelomingrone.derechosAutor.estadisticas.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class DatosCancion extends Entidad {
	
	private static final long serialVersionUID = 918845849093099784L;

	private Long companyId;
	
	@Column(name="pais_id")
	private Long idPais;
	
	private String nombrePais;
	
	private int trimestre;
	
	private int anio;
	
	@Column(name="autor_id")
	private Long idAutor;
	
	private String nombreAutor;
	
	@Column(name="cancion_id")
	private Long idCancion;
	
	private String nombreCancion;
	
	@Column(name="fuente_id")
	private Long idFuente;
	
	private String nombreFuente;
	
	@Column(name="derecho_nombre")
	private String nombreDerechoExterno;
	
	private long cantidadUnidades;
	
	private double montoPercibido;
	
	public DatosCancion() {}
	
	

	public DatosCancion(Long companyId, Long idPais, String nombrePais,
			int trimestre, int anio, Long idAutor, String nombreAutor,
			Long idCancion, String nombreCancion, Long idFuente,
			String nombreFuente, String nombreDerechoExterno,
			long cantidadUnidades, double montoPercibido) {
		super();
		this.companyId = companyId;
		this.idPais = idPais;
		this.nombrePais = nombrePais;
		this.trimestre = trimestre;
		this.anio = anio;
		this.idAutor = idAutor;
		this.nombreAutor = nombreAutor;
		this.idCancion = idCancion;
		this.nombreCancion = nombreCancion;
		this.idFuente = idFuente;
		this.nombreFuente = nombreFuente;
		this.nombreDerechoExterno = nombreDerechoExterno;
		this.cantidadUnidades = cantidadUnidades;
		this.montoPercibido = montoPercibido;
	}



	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Long getIdPais() {
		return idPais;
	}
	
	public void setIdPais(Long idPais) {
		this.idPais = idPais;
	}
	
	public String getNombrePais() {
		return nombrePais;
	}
	
	public void setNombrePais(String nombrePais) {
		this.nombrePais = nombrePais;
	}

	public int getTrimestre() {
		return trimestre;
	}

	public void setTrimestre(int trimestre) {
		this.trimestre = trimestre;
	}

	public int getAnio() {
		return anio;
	}

	public void setAnio(int anio) {
		this.anio = anio;
	}

	public Long getIdAutor() {
		return idAutor;
	}
	
	public void setIdAutor(Long idAutor) {
		this.idAutor = idAutor;
	}
	
	public String getNombreAutor() {
		return nombreAutor;
	}
	
	public void setNombreAutor(String nombreAutor) {
		this.nombreAutor = nombreAutor;
	}
	
	public Long getIdCancion() {
		return idCancion;
	}
	
	public void setIdCancion(Long idCancion) {
		this.idCancion = idCancion;
	}
	
	public String getNombreCancion() {
		return nombreCancion;
	}
	
	public void setNombreCancion(String nombreCancion) {
		this.nombreCancion = nombreCancion;
	}
	
	public Long getIdFuente() {
		return idFuente;
	}
	
	public void setIdFuente(Long idFuente) {
		this.idFuente = idFuente;
	}
	
	public String getNombreFuente() {
		return nombreFuente;
	}
	
	public void setNombreFuente(String nombreFuente) {
		this.nombreFuente = nombreFuente;
	}

	public long getCantidadUnidades() {
		return cantidadUnidades;
	}

	public void setCantidadUnidades(long cantidadUnidades) {
		this.cantidadUnidades = cantidadUnidades;
	}

	public double getMontoPercibido() {
		return montoPercibido;
	}

	public void setMontoPercibido(double montoPercibido) {
		this.montoPercibido = montoPercibido;
	}
	
	public String getNombreDerechoExterno() {
		return nombreDerechoExterno;
	}
	
	public void setNombreDerechoExterno(String nombreDerechoExterno) {
		this.nombreDerechoExterno = nombreDerechoExterno;
	}
	

}
