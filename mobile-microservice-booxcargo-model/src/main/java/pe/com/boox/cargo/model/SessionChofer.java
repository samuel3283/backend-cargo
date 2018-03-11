package pe.com.boox.cargo.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class SessionChofer {

	@JsonIgnore
	private Integer codigo;
	private Integer idConductor;
	private String token;
	private String device;
	private String deviceType;
	
	private String brevete;
	private String nombre;
	private String apellido;
	private String email;
	private String tipoDocumento;
	private String numDocumento;
	private String telefono;
	@JsonInclude(Include.NON_NULL)
	private String foto;
	@JsonInclude(Include.NON_NULL)
	private String imagen;
	@JsonInclude(Include.NON_NULL)
	private String extension;
	@JsonInclude(Include.NON_NULL)
	private String fechaRegistro;
	@JsonInclude(Include.NON_NULL)
	private String fechaModifica;
	@JsonInclude(Include.NON_NULL)
	private String fechaExpira;
	@JsonIgnore
	private Integer estado;
	@JsonIgnore
	private String perfil;
	private Boolean indNuevo;

	private Map<String, Object> mapa;
	@JsonInclude(Include.NON_NULL)
	private VehiculoTransporte vehiculo;
	
	public SessionChofer() {
		this.codigo = new Integer(0);
		this.estado = new Integer(0);
		this.mapa = null;
		this.vehiculo=new VehiculoTransporte();
	}

	
	public VehiculoTransporte getVehiculo() {
		return vehiculo;
	}


	public void setVehiculo(VehiculoTransporte vehiculo) {
		this.vehiculo = vehiculo;
	}


	public Integer getIdConductor() {
		return idConductor;
	}

	public void setIdConductor(Integer idConductor) {
		this.idConductor = idConductor;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getBrevete() {
		return brevete;
	}

	public void setBrevete(String brevete) {
		this.brevete = brevete;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getNumDocumento() {
		return numDocumento;
	}

	public void setNumDocumento(String numDocumento) {
		this.numDocumento = numDocumento;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
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

	public String getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(String fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getFechaModifica() {
		return fechaModifica;
	}

	public void setFechaModifica(String fechaModifica) {
		this.fechaModifica = fechaModifica;
	}

	public String getFechaExpira() {
		return fechaExpira;
	}

	public void setFechaExpira(String fechaExpira) {
		this.fechaExpira = fechaExpira;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public String getPerfil() {
		return perfil;
	}

	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}

	public Map<String, Object> getMapa() {
		return mapa;
	}

	public void setMapa(Map<String, Object> mapa) {
		this.mapa = mapa;
	}

	public Boolean getIndNuevo() {
		return indNuevo;
	}

	public void setIndNuevo(Boolean indNuevo) {
		this.indNuevo = indNuevo;
	}

	@Override
	public String toString() {
		return "SessionChofer [codigo=" + codigo + ", token=" + token + ", device=" + device + ", deviceType="
				+ deviceType + ", brevete=" + brevete + ", nombre=" + nombre + ", apellido=" + apellido + ", email="
				+ email + ", tipoDocumento=" + tipoDocumento + ", numDocumento=" + numDocumento + ", telefono="
				+ telefono + ", foto=" + foto + ", imagen=" + imagen + ", extension=" + extension + ", fechaRegistro="
				+ fechaRegistro + ", fechaModifica=" + fechaModifica + ", fechaExpira=" + fechaExpira + ", estado="
				+ estado + ", perfil=" + perfil + ", indNuevo=" + indNuevo + ", mapa=" + mapa + "]";
	}


	
}
