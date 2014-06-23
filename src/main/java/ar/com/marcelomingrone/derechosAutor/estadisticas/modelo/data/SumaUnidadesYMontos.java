package ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="VIEW_UnitsAndAmounts")
public class SumaUnidadesYMontos implements Serializable {
	
	private static final long serialVersionUID = 8787451139716411316L;

	@Id
	private Long id;

	private Long companyId;
	
	private Long idPais;
	
	private String nombrePais;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date fecha;
	
	private int formatId;
	
	private Long idCancion;
	
	private String nombreCancion;
	
	private Long idAutor;
	
	private String nombreAutor;
	
	private Long idFuente;
	
	private String nombreFuente;
	
	private String rightName;
	
	private BigDecimal copyRightShares;
	
	private Long unidades;
	
	private Long externalCode;
	
	private Double currencyFactor;
	
	private BigDecimal localCurrency;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public int getFormatId() {
		return formatId;
	}

	public void setFormatId(int formatId) {
		this.formatId = formatId;
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

	public String getRightName() {
		return rightName;
	}

	public void setRightName(String rightName) {
		this.rightName = rightName;
	}

	public BigDecimal getCopyRightShares() {
		return copyRightShares;
	}

	public void setCopyRightShares(BigDecimal copyRightShares) {
		this.copyRightShares = copyRightShares;
	}

	public Long getUnidades() {
		return unidades;
	}

	public void setUnidades(Long unidades) {
		this.unidades = unidades;
	}

	public Long getExternalCode() {
		return externalCode;
	}

	public void setExternalCode(Long externalCode) {
		this.externalCode = externalCode;
	}

	public Double getCurrencyFactor() {
		return currencyFactor;
	}

	public void setCurrencyFactor(Double currencyFactor) {
		this.currencyFactor = currencyFactor;
	}

	public BigDecimal getLocalCurrency() {
		return localCurrency;
	}

	public void setLocalCurrency(BigDecimal localCurrency) {
		this.localCurrency = localCurrency;
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
		SumaUnidadesYMontos other = (SumaUnidadesYMontos) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	

}
