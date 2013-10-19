package ar.com.marcelomingrone.derechosAutor.estadisticas.servicios;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import ar.com.marcelomingrone.derechosAutor.estadisticas.dao.AutorDao;
import ar.com.marcelomingrone.derechosAutor.estadisticas.dao.PaisDao;
import ar.com.marcelomingrone.derechosAutor.estadisticas.dao.UnidadesVendidasPorAutorDao;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.Autor;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.Pais;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.UnidadesVendidasPorAutor;
import au.com.bytecode.opencsv.CSVReader;

@Service
public class ServicioImportacion {
	
	private static Log log = LogFactory.getLog(ServicioImportacion.class);
	
	@Autowired
	private AutorDao autorDao;
	
	@Autowired
	private PaisDao paisDao;
	
	@Autowired
	private UnidadesVendidasPorAutorDao unidadesVendidasPorAutorDao;

	private static final int COMPANY_ID_INDEX = 0;
	private static final int COUNTRY_ID_INDEX = 1;
	private static final int COUNTRY_NAME_INDEX = 2;
	private static final int START_DATE_INDEX = 3;
	private static final int FORMAT_ID_INDEX = 4;
	private static final int WORK_ID_INDEX = 5;
	private static final int WORK_NAME_INDEX = 6;
	private static final int ARTIST_ID_INDEX = 7;
	private static final int ARTIST_NAME_INDEX = 8;
	private static final int SOURCE_NAME_INDEX = 9;
	private static final int COPYRIGHT_SHARE_INDEX = 10;
	private static final int UNITS_INDEX = 11;
	private static final int EXTERNAL_CODE_INDEX = 12;

	private static final int CANTIDAD_CAMPOS = 13;

	private static final String SEPARADOR_LINEAS = "<br/>";
	
	private NumberFormat formateadorDecimales;
	
	private SimpleDateFormat formateadorFechas;
	
	public ServicioImportacion() {
		
		formateadorDecimales = NumberFormat.getNumberInstance(Locale.ENGLISH);
		formateadorDecimales.setGroupingUsed(true);
		
		formateadorFechas = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	}
	
	
	public String importarDatos(InputStream inputStream) {
		
		// TODO: PRIMERO BORRAR TODO !!!!
		
		StringBuffer resultado = new StringBuffer();
		
		CSVReader csvReader = new CSVReader(new InputStreamReader(inputStream));
		
		try {
			long contadorLineas = 1;
			
			Map<Integer, Map<Integer, Map<Pais, Map<Autor, Long>>>> acumuladorUnidades = new HashMap<>();
		
			String[] linea = csvReader.readNext();
			while (linea != null) {
				
				if(linea.length < CANTIDAD_CAMPOS) {
                    resultado.append("La linea ").append(contadorLineas)
                    	.append(" no pudo procesarse porque tiene menos de ")
                    	.append(CANTIDAD_CAMPOS).append(" campos.")
                    	.append(SEPARADOR_LINEAS);

				} else {
					
                    try {
                    		long idAutor = parsearLong(linea[ARTIST_ID_INDEX], contadorLineas, "id de autor");
                            String nombreAutor = linea[ARTIST_NAME_INDEX];

                            Autor autor = new Autor(idAutor, nombreAutor);
                            autorDao.guardar(autor);
                            
                            long idPais = parsearLong(linea[COUNTRY_ID_INDEX], contadorLineas, "id de pais");
                            String nombrePais = linea[COUNTRY_NAME_INDEX];
                            
                            Pais pais = new Pais(idPais, nombrePais);
                            paisDao.guardar(pais);
                            
                            Date fecha = parsearFecha(linea[START_DATE_INDEX], contadorLineas, "fecha");
                            
                            int anio = getAnio(fecha);
                            int trimestre = getTrimestre(fecha);
                            
                            long unidades = parsearLong(linea[UNITS_INDEX], contadorLineas, "unidades");
                            long externalCode = parsearLong(linea[EXTERNAL_CODE_INDEX], contadorLineas, "externalCode");
                            double copyrightShares = parsearMoneda(linea[COPYRIGHT_SHARE_INDEX], contadorLineas, "copyrightShares");
                            
                            long cantidadUnidades = (long) Math.floor(unidades * externalCode * (copyrightShares/100));
                            
                            acumularCantidadUnidades(acumuladorUnidades, anio, trimestre, pais, autor, cantidadUnidades);
                            
                    } catch (ImportacionException e) {
                            resultado.append(e.getMessage()).append(SEPARADOR_LINEAS);
                    }
				}

				linea = csvReader.readNext();
				contadorLineas++;
			}
			
			persistirAcumuladores(acumuladorUnidades);
			
			if (resultado.toString().equals("")) {
				resultado.append("El archivo se ha importado correctamente.");
			}
			
		} catch (IOException e) {
			resultado.append("No se ha podido leer el archivo.");
			log.error(e.getMessage(), e);
			
		} finally {
			try {
				csvReader.close();
			} catch (IOException e) {
				log.error(e.getMessage(), e);
			}
		}
		
		return resultado.toString();
	}


	private void persistirAcumuladores(
			Map<Integer, Map<Integer, Map<Pais, Map<Autor, Long>>>> acumuladorUnidades) {
		
		List<UnidadesVendidasPorAutor> unidades = new LinkedList<>();
		
		for (Integer anio : acumuladorUnidades.keySet()) {
			
			Map<Integer, Map<Pais, Map<Autor, Long>>> porTrimestre = acumuladorUnidades.get(anio);
			for (Integer trimestre : porTrimestre.keySet()) {
				
				Map<Pais, Map<Autor, Long>> porPais = porTrimestre.get(trimestre);
				for (Pais pais : porPais.keySet()) {
					
					Map<Autor, Long> porAutor = porPais.get(pais);
					for (Autor autor : porAutor.keySet()) {
						
						Long cantidad = porAutor.get(autor);
						UnidadesVendidasPorAutor unidad = new UnidadesVendidasPorAutor(
								autor, pais, anio, trimestre, cantidad);
						
						unidades.add(unidad);
					}
				}
			}
		}
		
		unidadesVendidasPorAutorDao.guardar(unidades);
		
	}


	private void acumularCantidadUnidades(
			Map<Integer, Map<Integer, Map<Pais, Map<Autor, Long>>>> acumuladorUnidades,
			int anio, int trimestre, Pais pais, Autor autor,
			long cantidadUnidades) {
		
		Map<Integer, Map<Pais, Map<Autor, Long>>> valoresPorAnio = acumuladorUnidades.get(Integer.valueOf(anio));
		
		if (valoresPorAnio == null) {
			valoresPorAnio = new HashMap<>();
			acumuladorUnidades.put(Integer.valueOf(anio), valoresPorAnio);
		}
		
		Map<Pais, Map<Autor, Long>> valoresPorTrimestre = valoresPorAnio.get(Integer.valueOf(trimestre));
		
		if (valoresPorTrimestre == null) {
			valoresPorTrimestre = new HashMap<>();
			valoresPorAnio.put(Integer.valueOf(trimestre), valoresPorTrimestre);
		}
		
		Map<Autor, Long> valoresPorPais = valoresPorTrimestre.get(pais);
		
		if (valoresPorPais == null) {
			valoresPorPais = new HashMap<>();
			valoresPorTrimestre.put(pais, valoresPorPais);
		}
		
		Long cantidadPorAutor = valoresPorPais.get(autor);
		
		if (cantidadPorAutor == null) {
			cantidadPorAutor = new Long(cantidadUnidades);
			
		} else {
			cantidadPorAutor = new Long(cantidadPorAutor.longValue() + cantidadUnidades);
		}
		
		valoresPorPais.put(autor, cantidadPorAutor);
	}


	private int getTrimestre(Date fecha) {
		
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


	private Date parsearFecha(String valor, long contadorLineas, String nombreCampo) 
			throws ImportacionException {
		
		Date fecha = null;
		
		if (!StringUtils.isEmpty(valor)) {
			
			try {
				fecha = formateadorFechas.parse(valor);
				
			} catch (ParseException e) {
				throw new ImportacionException("Error en linea " + contadorLineas + 
						": El " + nombreCampo + " no es una fecha válida");
			}
		}
		
		return fecha;
	}


	private double parsearMoneda(String valor, long contadorLineas,
			String nombreCampo) throws ImportacionException {
		
		double moneda = -1;
		
		if (!StringUtils.isEmpty(valor)) {
			
			try {
				moneda = formateadorDecimales.parse(valor).doubleValue();
				
			} catch (ParseException e) {
				throw new ImportacionException("Error en linea " + contadorLineas + 
						": El " + nombreCampo + " no es válido");
			}
		}
		
		return moneda;
	}


	private long parsearLong(String idStr, long contadorLineas, String nombreCampo) 
			throws ImportacionException {
		
		long id = -1;
		
		if (!StringUtils.isEmpty(idStr)) {
			
			try {
				id = Long.parseLong(idStr);
				
			} catch (NumberFormatException e) {
				throw new ImportacionException("Error en linea " + contadorLineas + 
						": El " + nombreCampo + " no es un entero válido");
			}
		}
		
		return id;
	}

}
