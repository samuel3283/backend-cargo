package pe.com.boox.cargo.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * Clase encargada de mostrar los Json de Respuesta
 * 
 * @author Snavarro
 * @since 15/09/2016
 */

public class BeanResponse {
	
	@JsonIgnore
	private String codigo;
	@JsonProperty("resultado")
	private String valor;

	

	
	/**
	 * Constructor generico
	 */
	public BeanResponse() {
		super();
	}

	/**
	 * Constructor que recibe el codigo y valor
	 * @param codigo
	 * @param valor
	 */
	public BeanResponse(String codigo, String valor) {
		super();
		this.codigo = codigo;
		this.valor = valor;
	}

	/**
	 * Constructor que recibe el valor
 	 * @param valor
	 */
	public BeanResponse(String valor) {
		super();
		this.valor = valor;
	}

	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}


	
	
}
