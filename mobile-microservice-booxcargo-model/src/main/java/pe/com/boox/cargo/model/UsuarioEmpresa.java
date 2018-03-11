package pe.com.boox.cargo.model;

public class UsuarioEmpresa {

	private Integer codigo;;
	private String ruc;
	private String nombre;
	private String apellido;
	private String email;
	private String password;
	private String tipoDocumento;
	private String numDocumento;
	private String telefono;
	private String fechaRegistro;
	private String fechaModifica;
	private Integer estado;
	private Empresa empresa;
	private String perfil;
	
	public UsuarioEmpresa() {
		super();
		this.estado = new Integer(1);
		this.empresa = new Empresa();
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
	@Override
	public String toString() {
		return "UsuarioEmpresa [codigo=" + codigo + ", ruc=" + ruc + ", nombre=" + nombre + ", apellido=" + apellido
				+ ", email=" + email + ", password=" + password + ", tipoDocumento=" + tipoDocumento + ", numDocumento="
				+ numDocumento + ", telefono=" + telefono + ", fechaRegistro=" + fechaRegistro + ", fechaModifica="
				+ fechaModifica + ", estado=" + estado + ", empresa=" + empresa.toString() + ", perfil=" + perfil + "]";
	}

	
}
