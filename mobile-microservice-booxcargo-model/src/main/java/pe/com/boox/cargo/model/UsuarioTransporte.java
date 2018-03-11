package pe.com.boox.cargo.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class UsuarioTransporte {

	private Integer codigo;;
	private String ruc;
	private String nombre;
	private String apellido;
	private String email;
	private String password;
	private String tipoDocumento;
	private String numDocumento;
	private String telefono;
	private String foto;
	@JsonInclude(Include.NON_NULL)
	private String fechaRegistro;
	@JsonInclude(Include.NON_NULL)
	private String fechaModifica;
	@JsonInclude(Include.NON_NULL)
	private Integer estado;
	@JsonInclude(Include.NON_NULL)
	private Empresa empresa;
	@JsonInclude(Include.NON_NULL)
	private String perfil;
	@JsonInclude(Include.NON_NULL)
	private String olvido;
	@JsonInclude(Include.NON_NULL)
	private List<VehiculoTransporte> listaVehiculo;
	
	public UsuarioTransporte() {
		super();
		this.estado = new Integer(1);
		this.empresa = new Empresa();
		this.listaVehiculo = new ArrayList<VehiculoTransporte>();
	}
	
	public Integer getCodigo() {
		return codigo;
	}
	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
	public Empresa getEmpresa() {
		return empresa;
	}
	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
	public String getPerfil() {
		return perfil;
	}
	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}
	public String getFoto() {
		return foto;
	}
	public void setFoto(String foto) {
		this.foto = foto;
	}
	
	
	public List<VehiculoTransporte> getListaVehiculo() {
		return listaVehiculo;
	}

	public void setListaVehiculo(List<VehiculoTransporte> listaVehiculo) {
		this.listaVehiculo = listaVehiculo;
	}

	public String getOlvido() {
		return olvido;
	}

	public void setOlvido(String olvido) {
		this.olvido = olvido;
	}

	@Override
	public String toString() {
		return "UsuarioTransporte [codigo=" + codigo + ", ruc=" + ruc + ", nombre=" + nombre + ", apellido=" + apellido
				+ ", email=" + email + ", password=" + password + ", tipoDocumento=" + tipoDocumento + ", numDocumento="
				+ numDocumento + ", telefono=" + telefono + ", foto=" + foto + ", fechaRegistro=" + fechaRegistro
				+ ", fechaModifica=" + fechaModifica + ", estado=" + estado + ", empresa=" + empresa + ", perfil="
				+ perfil + ", olvido=" + olvido + ", listaVehiculo=" + listaVehiculo + "]";
	}

	



	
}
