package ar.com.marcelomingrone.derechosAutor.estadisticas.modelo;

import org.springframework.web.multipart.MultipartFile;

public class ArchivoImportacion {
	
	private MultipartFile file;

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

}
