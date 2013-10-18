package ar.com.mmingrone.estadisticasDerechosAutor

class ImportarController {

    def importarService

    def index() { }

	def upload() {
	    def f = request.getFile('archivo')
	    if (f.empty) {
		flash.message = 'file cannot be empty'
		render(view: 'index')
		return
	    }

	    String resultado = importarService.importarArchivo(f.getInputStream()) 
	    render(view: 'exito', model:['tirar':resultado]) 
    }
}
