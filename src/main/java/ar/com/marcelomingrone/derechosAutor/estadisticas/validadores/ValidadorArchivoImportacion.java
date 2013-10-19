package ar.com.marcelomingrone.derechosAutor.estadisticas.validadores;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.ArchivoImportacion;

@Component
public class ValidadorArchivoImportacion implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return ArchivoImportacion.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errores) {
		
		ArchivoImportacion archivo = (ArchivoImportacion)target;
		 
		if(archivo.getFile().getSize()==0){
			errores.rejectValue("file", "archivo.seleccionar");
		}
		
	}

}
