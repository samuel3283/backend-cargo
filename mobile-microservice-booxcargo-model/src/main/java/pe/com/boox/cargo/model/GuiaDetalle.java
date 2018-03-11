package pe.com.boox.cargo.model;

public class GuiaDetalle {
	
	private Integer codigo;
	private Integer codigoGuia;
	private String descripcion;
	private Integer cantidad;
	private String peso;
	private String unidad;
	private String costo;
	private String tipoCarga;
	private String desTipoCarga;

	private Integer estado;
	private String fecRegistro;
	
	
	public GuiaDetalle() {
		super();
		estado = new Integer(1);
		codigo = new Integer(0);
		codigoGuia = new Integer(0);
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


	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public Integer getCantidad() {
		return cantidad;
	}


	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}


	public String getPeso() {
		return peso;
	}


	public void setPeso(String peso) {
		this.peso = peso;
	}


	public String getUnidad() {
		return unidad;
	}


	public void setUnidad(String unidad) {
		this.unidad = unidad;
	}


	public String getCosto() {
		return costo;
	}


	public void setCosto(String costo) {
		this.costo = costo;
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


	public String getTipoCarga() {
		return tipoCarga;
	}


	public void setTipoCarga(String tipoCarga) {
		this.tipoCarga = tipoCarga;
	}


	public String getDesTipoCarga() {
		return desTipoCarga;
	}


	public void setDesTipoCarga(String desTipoCarga) {
		this.desTipoCarga = desTipoCarga;
	}


	@Override
	public String toString() {
		return "GuiaDetalle [codigo=" + codigo + ", codigoGuia=" + codigoGuia + ", descripcion=" + descripcion
				+ ", cantidad=" + cantidad + ", peso=" + peso + ", unidad=" + unidad + ", costo=" + costo
				+ ", tipoCarga=" + tipoCarga + ", desTipoCarga=" + desTipoCarga + ", estado=" + estado
				+ ", fecRegistro=" + fecRegistro + "]";
	}




	
}
