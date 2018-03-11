package pe.com.boox.cargo.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class Subasta {
	
	private Integer codigo;
	@JsonInclude(Include.NON_NULL)
	private Integer estado;
	private String monto;
	@JsonInclude(Include.NON_NULL)
	private Integer codigoChofer;
	private String placa;
	@JsonInclude(Include.NON_NULL)
	private String brevete;
	@JsonInclude(Include.NON_NULL)
	private String fecRegistro;
	@JsonInclude(Include.NON_NULL)
	private Integer codigoGuia;
	@JsonInclude(Include.NON_NULL)
	private Integer codigoUsuarioTransporte;
	@JsonInclude(Include.NON_NULL)
	private UsuarioTransporte usuarioTransporte;
	@JsonInclude(Include.NON_NULL)
	private VehiculoTransporte vehiculoTransporte;
	//private Chofer chofer;
	
	
	public Subasta() {
		super();
		estado = new Integer(1);
		//usuarioTransporte = new UsuarioTransporte();
		//chofer = new Chofer();
		vehiculoTransporte = new VehiculoTransporte();
	}


	public Integer getCodigoChofer() {
		return codigoChofer;
	}


	public void setCodigoChofer(Integer codigoChofer) {
		this.codigoChofer = codigoChofer;
	}


	public UsuarioTransporte getUsuarioTransporte() {
		return usuarioTransporte;
	}


	public void setUsuarioTransporte(UsuarioTransporte usuarioTransporte) {
		this.usuarioTransporte = usuarioTransporte;
	}


	public Integer getCodigoUsuarioTransporte() {
		return codigoUsuarioTransporte;
	}


	public void setCodigoUsuarioTransporte(Integer codigoUsuarioTransporte) {
		this.codigoUsuarioTransporte = codigoUsuarioTransporte;
	}


	public Integer getCodigoGuia() {
		return codigoGuia;
	}


	public void setCodigoGuia(Integer codigoGuia) {
		this.codigoGuia = codigoGuia;
	}


	public Integer getCodigo() {
		return codigo;
	}


	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}


	public Integer getEstado() {
		return estado;
	}


	public void setEstado(Integer estado) {
		this.estado = estado;
	}


	public String getMonto() {
		return monto;
	}


	public void setMonto(String monto) {
		this.monto = monto;
	}


	public String getFecRegistro() {
		return fecRegistro;
	}


	public void setFecRegistro(String fecRegistro) {
		this.fecRegistro = fecRegistro;
	}


	public String getPlaca() {
		return placa;
	}


	public void setPlaca(String placa) {
		this.placa = placa;
	}


	public String getBrevete() {
		return brevete;
	}


	public void setBrevete(String brevete) {
		this.brevete = brevete;
	}


	public VehiculoTransporte getVehiculoTransporte() {
		return vehiculoTransporte;
	}
	

	public void setVehiculoTransporte(VehiculoTransporte vehiculoTransporte) {
		this.vehiculoTransporte = vehiculoTransporte;
	}


	@Override
	public String toString() {
		return "Subasta [codigo=" + codigo + ", estado=" + estado + ", monto=" + monto + ", placa=" + placa
				+ ", brevete=" + brevete + ", fecRegistro=" + fecRegistro + ", codigoGuia=" + codigoGuia
				+ ", codigoUsuarioTransporte=" + codigoUsuarioTransporte + ", vehiculoTransporte=" + vehiculoTransporte
				+ "]";
	}


	
}
