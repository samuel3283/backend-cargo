package pe.com.boox.cargo.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Session {

	@JsonIgnore
	private Integer codigo;
	private String token;
	private String device;
	private String deviceType;

	private String ruc;
	private String nombre;
	private String apellido;
	private String email;
	private String tipoDocumento;
	private String numDocumento;
	private String telefono;
	@JsonIgnore
	private String fechaRegistro;
	@JsonIgnore
	private String fechaModifica;
	@JsonIgnore
	private String fechaExpira;
	@JsonIgnore
	private Integer estado;
	@JsonIgnore
	private String perfil;
	private Empresa empresa;

	private Map<String, Object> mapa;
	
	public Session() {
		this.codigo = new Integer(0);
		this.estado = new Integer(0);
		this.mapa = null;
		this.empresa = new Empresa();
		//this.mapa = new HashMap<String, Object>();
	}

	
	public Empresa getEmpresa() {
		return empresa;
	}


	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}


	public Map<String, Object> getMapa() {
		return mapa;
	}


	public void setMapa(Map<String, Object> mapa) {
		this.mapa = mapa;
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


	public String getRuc() {
		return ruc;
	}


	public void setRuc(String ruc) {
		this.ruc = ruc;
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


	public String getFechaExpira() {
		return fechaExpira;
	}


	public void setFechaExpira(String fechaExpira) {
		this.fechaExpira = fechaExpira;
	}

	
}
