package pe.com.boox.cargo.model;

public class GuiaEstado {
	
	private Integer codigo;
	private Integer codigoGuia;
	private Integer estado;
	private String nota;
	private String desEstado;
	private String fecRegistro;
	
	
	public GuiaEstado() {
		super();
		estado = new Integer(0);
		codigo = new Integer(0);
		codigoGuia = new Integer(0);
	}


	public String getDesEstado() {
		return desEstado;
	}


	public void setDesEstado(String desEstado) {
		this.desEstado = desEstado;
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


	public Integer getEstado() {
		return estado;
	}


	public void setEstado(Integer estado) {
		this.estado = estado;
	}


	public String getNota() {
		return nota;
	}


	public void setNota(String nota) {
		this.nota = nota;
	}


	public String getFecRegistro() {
		return fecRegistro;
	}


	public void setFecRegistro(String fecRegistro) {
		this.fecRegistro = fecRegistro;
	}


	@Override
	public String toString() {
		return "GuiaEstado [codigo=" + codigo + ", codigoGuia=" + codigoGuia + ", estado=" + estado + ", nota=" + nota
				+ ", fecRegistro=" + fecRegistro + "]";
	}



	
}
