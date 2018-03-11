package pe.com.boox.cargo.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

 /**
 * Clase encargada de Mapear los Json de Respuesta
 * 
 * @author Snavarro
 * @param <T>
 * @since 15/09/2016
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionRs<T> {
	@JsonProperty("codRpta")
	private String codigoError = "0000";

	@JsonProperty("desRpta")
	private String descripcion = "Respuesta Exitosa";

	@JsonProperty("detRpta")
	@JsonInclude(Include.NON_NULL)
	private T respuesta;

	/**
	 * Constructor generico
	*/
	public TransactionRs() {
		super();
	}

	public String getCodigoError() {
		return codigoError;
	}

	public void setCodigoError(String codigoError) {
		this.codigoError = codigoError;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public T getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(T respuesta) {
		this.respuesta = respuesta;
	}

	@Override
	public String toString() {
		return "TransactionRs [codigoError=" + codigoError + ", descripcion=" + descripcion + ", respuesta=" + respuesta
				+ "]";
	}

}
