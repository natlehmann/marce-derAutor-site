package ar.com.mmingrone.estadisticasDerechosAutor

import grails.transaction.Transactional

@Transactional
class ImportarService {

	static final int COMPANY_ID_INDEX = 0
	static final int COUNTRY_ID_INDEX = 1
	static final int COUNTRY_NAME_INDEX = 2
	static final int START_DATE_INDEX = 3
	static final int FORMAT_ID_INDEX = 4
	static final int WORK_ID_INDEX = 5
	static final int WORK_NAME_INDEX = 6
	static final int ARTIST_ID_INDEX = 7
	static final int ARTIST_NAME_INDEX = 8
	static final int SOURCE_NAME_INDEX = 9
	static final int COPYRIGHT_SHARE_INDEX = 10
	static final int UNITS_INDEX = 11
	static final int EXTERNAL_CODE_INDEX = 12

	static final int CANTIDAD_CAMPOS = 13

	static final String SEPARADOR_LINEAS = "<br/>"

    def importarArchivo(def inputStream) {

	StringBuffer resultado = new StringBuffer()
	long contadorLineas = 1

	inputStream.eachCsvLine { tokens ->
		
		if(tokens.length < CANTIDAD_CAMPOS) {
			resultado.append("La linea ").append(contadorLineas).append(" no pudo procesarse porque tiene menos de ").append(CANTIDAD_CAMPOS).append(" campos.").append(SEPARADOR_LINEAS)

		} else {
			try {

				Long idAutor = parsearIdAutor(tokens[ARTIST_ID_INDEX], contadorLineas)
				String nombreAutor = tokens[ARTIST_NAME_INDEX]
println "autor id vale ${idAutor}"
println "autor name vale ${nombreAutor}"

				Autor autor = new Autor(idAutor, nombreAutor)
println "le pido el id al autor ${autor.id}"
				autor.save()

			} catch (Exception e) {
				resultado.append(e.getMessage()).append(SEPARADOR_LINEAS)
			}
		}

		contadorLineas++
       }

	return resultado.toString()
    }

	private def parsearIdAutor(def idAutor, def contadorLineas) {
println "Recibi ${idAutor}"
		Long id = null
		if (idAutor) {
			try {
				id = Long.parseLong(idAutor)
println "ahora parseo ${id}"
			} catch (NumberFormatException e) {

				throw new Exception("Error en linea " + contadorLineas + ": El id de autor no es un entero válido") 
			}
		}

		return id 
	}


}
