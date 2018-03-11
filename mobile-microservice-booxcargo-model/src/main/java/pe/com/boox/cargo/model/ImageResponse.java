package pe.com.boox.cargo.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * Clase encargada de mostrar los Json de Respuesta
 * 
 * @author Snavarro
 * @since 15/09/2016
 */

public class ImageResponse {
	
	private String imagen;
	private String extension;
	
	/**
	 * Constructor generico
	 */
	public ImageResponse() {
		super();
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	
}
