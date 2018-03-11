package pe.com.boox.cargo.model;

public class Alertas {

	private Integer codigo;
	private Integer codigoGuia;
	private String alerta;
	private Integer estado;
	private String desEstado;
	
	private String fecRegistro;
	private String numGuia;
	
	//@JsonInclude(Include.NON_NULL)
	//@JsonProperty("expiracion")
	
	public Alertas() {
		super();
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public Integer getCodigoGuia() {
		return codigoGuia;
	}

	public void setCodigoGuia(Integer codigoGuia) {
		this.codigoGuia = codigoGuia;
	}

	public String getAlerta() {
		return alerta;
	}

	public void setAlerta(String alerta) {
		this.alerta = alerta;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public String getDesEstado() {
		return desEstado;
	}

	public void setDesEstado(String desEstado) {
		this.desEstado = desEstado;
	}

	public String getFecRegistro() {
		return fecRegistro;
	}

	public void setFecRegistro(String fecRegistro) {
		this.fecRegistro = fecRegistro;
	}

	public String getNumGuia() {
		return numGuia;
	}

	public void setNumGuia(String numGuia) {
		this.numGuia = numGuia;
	}

	@Override
	public String toString() {
		return "Alertas [codigo=" + codigo + ", codigoGuia=" + codigoGuia + ", alerta=" + alerta + ", estado=" + estado
				+ ", desEstado=" + desEstado + ", fecRegistro=" + fecRegistro + ", numGuia=" + numGuia + "]";
	}




		
}
