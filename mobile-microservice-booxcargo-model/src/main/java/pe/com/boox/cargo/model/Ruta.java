package pe.com.boox.cargo.model;

public class Ruta {

	private Integer codigo;
	private Integer codigoGuia;
	private Integer codigoguia;
	private Integer codigoChofer;
	private String lat;
	private String lng;
	private String fecha;
	private String tipo;
	private String alerta;
	private String json;
	private String altura;
	private String velocidad;
	private String aproximacion;
	private String fechaReferencia;
	private String fechaRegistro;

	private Integer estado;
	
	public Ruta() {
		super();
		this.estado = new Integer(1);
	}

	
	public String getFechaRegistro() {
		return fechaRegistro;
	}


	public void setFechaRegistro(String fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}


	public Integer getCodigoguia() {
		return codigoguia;
	}


	public void setCodigoguia(Integer codigoguia) {
		this.codigoguia = codigoguia;
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

	public Integer getCodigoChofer() {
		return codigoChofer;
	}

	public void setCodigoChofer(Integer codigoChofer) {
		this.codigoChofer = codigoChofer;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	public String getAltura() {
		return altura;
	}

	public void setAltura(String altura) {
		this.altura = altura;
	}

	public String getVelocidad() {
		return velocidad;
	}

	public void setVelocidad(String velocidad) {
		this.velocidad = velocidad;
	}

	public String getAproximacion() {
		return aproximacion;
	}

	public void setAproximacion(String aproximacion) {
		this.aproximacion = aproximacion;
	}

	public String getFechaReferencia() {
		return fechaReferencia;
	}

	public void setFechaReferencia(String fechaReferencia) {
		this.fechaReferencia = fechaReferencia;
	}

	public String getAlerta() {
		return alerta;
	}

	public void setAlerta(String alerta) {
		this.alerta = alerta;
	}

	@Override
	public String toString() {
		return "Ruta [codigo=" + codigo + ", codigoGuia=" + codigoGuia + ", codigoChofer=" + codigoChofer + ", lat="
				+ lat + ", lng=" + lng + ", fecha=" + fecha + ", tipo=" + tipo + ", estado=" + estado + "]";
	}


		
}
