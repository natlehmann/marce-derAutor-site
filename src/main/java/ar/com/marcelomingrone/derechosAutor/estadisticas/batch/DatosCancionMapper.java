package ar.com.marcelomingrone.derechosAutor.estadisticas.batch;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;

import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.Autor;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.Cancion;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.DatosCancion;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.Derecho;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.Fuente;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.Pais;

public class DatosCancionMapper implements FieldSetMapper<DatosCancion> {
	
	private SimpleDateFormat formateadorFechas = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	public DatosCancion mapFieldSet(FieldSet fieldSet) throws BindException {
		
		DatosCancion datosCancion = new DatosCancion();
		int i = 0;
		
		try {
			datosCancion.setCompanyId(fieldSet.readLong(i++));
			
			Pais pais = new Pais(fieldSet.readLong(i++), fieldSet.readString(i++));
			datosCancion.setPais(pais);
		
			Date fecha = formateadorFechas.parse(fieldSet.readString(i++));
			datosCancion.setTrimestre(getTrimestre(fecha));
			datosCancion.setAnio(getAnio(fecha));
			
			datosCancion.setFormatId(fieldSet.readInt(i++));
			
			Long idCancion = null;
			try {				
				idCancion = fieldSet.readLong(i++);
				
			} catch (NumberFormatException e) {}
			
			String nombreCancion = fieldSet.readString(i++);
			
			if (!StringUtils.isEmpty(nombreCancion)) {
				Cancion cancion = new Cancion(idCancion, nombreCancion);
				datosCancion.setCancion(cancion);
			}
			
			Long idAutor = null;
			try {
				idAutor = fieldSet.readLong(i++);
				
			} catch (NumberFormatException e) {}
			
			String nombreAutor = fieldSet.readString(i++);
			
			if (!StringUtils.isEmpty(nombreAutor)) {
				Autor autor = new Autor(idAutor, nombreAutor);
				datosCancion.setAutor(autor);
			}
			
			Fuente fuente = new Fuente(fieldSet.readLong(i++), fieldSet.readString(i++));
			datosCancion.setFuente(fuente);
			
			String nombreDerecho = fieldSet.readString(i++);
			Derecho derecho = new Derecho(nombreDerecho != null ? nombreDerecho.toUpperCase() : null);
			derecho.setModificable(false);
			datosCancion.setDerecho(derecho);
			
			double copyRightShares = fieldSet.readDouble(i++);
			long unidades = fieldSet.readLong(i++);
			long externalCode = fieldSet.readLong(i++);
			double currencyFactor = fieldSet.readDouble(i++);
			double localCurrency = fieldSet.readDouble(i++);
			
			long cantidadUnidades = (long) Math.floor(unidades * externalCode * (copyRightShares/100));
			
			double monto = currencyFactor * localCurrency * (copyRightShares/100);
			
			datosCancion.setCantidadUnidades(cantidadUnidades);
			datosCancion.setMontoPercibido(monto);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new BindException(datosCancion, "datosCancion");
		}
		
		return datosCancion;
		
	}
	
	
	int getTrimestre(Date fecha) {
		
		int trimestre = -1;
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha);
		
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		
		calendar.set(Calendar.MONTH, 3);
		Date inicioSegundoTrimestre = calendar.getTime();
		
		if (fecha.before(inicioSegundoTrimestre)) {
			
			trimestre = 1;
			
		} else {
		
			calendar.set(Calendar.MONTH, 6);
			Date inicioTercerTrimestre = calendar.getTime();
			
			if (fecha.before(inicioTercerTrimestre)) {
				
				trimestre = 2;
				
			} else {
			
				calendar.set(Calendar.MONTH, 9);
				Date inicioCuartoTrimestre = calendar.getTime();
				
				if (fecha.before(inicioCuartoTrimestre)) {
					
					trimestre = 3;
				
				} else {
					
					trimestre = 4;
				}
			}
		}
		
		return trimestre;
		
	}


	private int getAnio(Date fecha) {
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha);
		return calendar.get(Calendar.YEAR);
	}

}
