package ar.com.mmingrone.estadisticasDerechosAutor

import grails.transaction.Transactional

@Transactional
class ImportarService {

    def importarArchivo(def inputStream) {

	inputStream.eachCsvLine { tokens ->
		println tokens[0]
		println tokens[1]
		println tokens[2]
		println tokens[3]
       }

	return "todo ok"
    }
}
