package pe.com.boox.cargo.model;

public class Empresa {

	private Integer codigo;;
	private String razonSocial;
	private String ruc;
	private Integer estado;
	private String fecRegistro;
	
	public Empresa() {
		super();
		this.estado=new Integer(1);
		this.codigo = new Integer(0);
	}
	
	public Integer getCodigo() {
		return codigo;
	}
	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}
	public String getRazonSocial() {
		return razonSocial;
	}
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	public String getRuc() {
		return ruc;
	}
	public void setRuc(String ruc) {
		this.ruc = ruc;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public String getFecRegistro() {
		return fecRegistro;
	}

	public void setFecRegistro(String fecRegistro) {
		this.fecRegistro = fecRegistro;
	}

	@Override
	public String toString() {
		return "Empresa [codigo=" + codigo + ", razonSocial=" + razonSocial + ", ruc=" + ruc + ", estado=" + estado
				+ ", fecRegistro=" + fecRegistro + "]";
	}
	
	
}
