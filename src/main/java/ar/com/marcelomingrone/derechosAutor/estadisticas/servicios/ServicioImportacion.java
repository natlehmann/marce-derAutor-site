package ar.com.marcelomingrone.derechosAutor.estadisticas.servicios;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import ar.com.marcelomingrone.derechosAutor.estadisticas.dao.AutorDao;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.Autor;
import au.com.bytecode.opencsv.CSVReader;

@Service
public class ServicioImportacion {
	
	private static Log log = LogFactory.getLog(ServicioImportacion.class);
	
	@Autowired
	private AutorDao autorDao;

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
	
	
	public String importarDatos(InputStream inputStream) {
		
		// TODO: PRIMERO BORRAR TODO !!!!
		
		StringBuffer resultado = new StringBuffer();
		
		CSVReader csvReader = new CSVReader(new InputStreamReader(inputStream));
		
		try {
			long contadorLineas = 1;
		
			String[] linea = csvReader.readNext();
			while (linea != null) {
				
				if(linea.length < CANTIDAD_CAMPOS) {
                    resultado.append("La linea ").append(contadorLineas)
                    	.append(" no pudo procesarse porque tiene menos de ")
                    	.append(CANTIDAD_CAMPOS).append(" campos.")
                    	.append(SEPARADOR_LINEAS);

				} else {
					
                    try {
                    		Long idAutor = parsearIdAutor(linea[ARTIST_ID_INDEX], contadorLineas);
                            String nombreAutor = linea[ARTIST_NAME_INDEX];

                            Autor autor = new Autor(idAutor, nombreAutor);
                            autorDao.guardar(autor);
                            
                    } catch (ImportacionException e) {
                            resultado.append(e.getMessage()).append(SEPARADOR_LINEAS);
                    }
				}

				linea = csvReader.readNext();
				contadorLineas++;
			}
			
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


	private Long parsearIdAutor(String idAutor, long contadorLineas) throws ImportacionException {
		
		Long id = null;
		
		if (!StringUtils.isEmpty(idAutor)) {
			
			try {
				id = Long.parseLong(idAutor);
				
			} catch (NumberFormatException e) {
				throw new ImportacionException("Error en linea " + contadorLineas + 
						": El id de autor no es un entero válido");
			}
		}
		
		return id;
	}

}
