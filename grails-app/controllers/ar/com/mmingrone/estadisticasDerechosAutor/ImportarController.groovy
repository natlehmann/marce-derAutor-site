package ar.com.mmingrone.estadisticasDerechosAutor

class ImportarController {

    def index() { }

	def upload() {
	    def f = request.getFile('archivo')
	    if (f.empty) {
		flash.message = 'file cannot be empty'
		render(view: 'index')
		return
	    }

StringBuffer tirar = new StringBuffer();

	    BufferedReader reader = new BufferedReader(new InputStreamReader(f.getInputStream()));
	    String linea = reader.readLine();

	    while(linea != null){
		tirar.append(linea);
		linea = reader.readLine();
	    }

	    //f.transferTo(new File('/some/local/dir/myfile.txt')) 
	    render(view: 'exito', model:['tirar':tirar.toString()]) 
    }
}
