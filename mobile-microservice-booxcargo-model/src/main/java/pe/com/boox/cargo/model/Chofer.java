package pe.com.boox.cargo.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class Chofer {

	private Integer codigo;
	private Integer codigoUsuarioTransporte;
	private String nombre;
	private String apellido;
	private String brevete;
	private String tipo;
	private String telefono;
	private String email;
	private String password;
	private String tipoDocumento;
	private String numDocumento;
	private String foto;
	private String fechaRegistro;
	private String fechaModifica;
	private Integer estado;
	private String fotoBrevete;
	private String extBrevete;
	@JsonInclude(Include.NON_NULL)
	private UsuarioTransporte usuarioTransporte;
	@JsonInclude(Include.NON_NULL)
	private String olvido;
	@JsonProperty("expiracion")
	private String expiracionBrevete;
	@JsonProperty("multas")
	private String multa;
	@JsonProperty("igrave")
	private String infraccionGrave;
	@JsonProperty("imgrave")
	private String infraccionMuyGrave;
	
	public Chofer() {
		super();
		this.estado = new Integer(1);
		this.usuarioTransporte = new UsuarioTransporte();
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}



	public Integer getCodigoUsuarioTransporte() {
		return codigoUsuarioTransporte;
	}

	public void setCodigoUsuarioTransporte(Integer codigoUsuarioTransporte) {
		this.codigoUsuarioTransporte = codigoUsuarioTransporte;
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

	public String getBrevete() {
		return brevete;
	}

	public void setBrevete(String brevete) {
		this.brevete = brevete;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
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

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
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

	public UsuarioTransporte getUsuarioTransporte() {
		return usuarioTransporte;
	}

	public void setUsuarioTransporte(UsuarioTransporte usuarioTransporte) {
		this.usuarioTransporte = usuarioTransporte;
	}

	public String getFotoBrevete() {
		return fotoBrevete;
	}

	public void setFotoBrevete(String fotoBrevete) {
		this.fotoBrevete = fotoBrevete;
	}

	public String getExtBrevete() {
		return extBrevete;
	}

	public void setExtBrevete(String extBrevete) {
		this.extBrevete = extBrevete;
	}

	public String getOlvido() {
		return olvido;
	}

	public void setOlvido(String olvido) {
		this.olvido = olvido;
	}

	public String getMulta() {
		return multa;
	}

	public void setMulta(String multa) {
		this.multa = multa;
	}

	public String getInfraccionGrave() {
		return infraccionGrave;
	}

	public void setInfraccionGrave(String infraccionGrave) {
		this.infraccionGrave = infraccionGrave;
	}

	public String getInfraccionMuyGrave() {
		return infraccionMuyGrave;
	}

	public void setInfraccionMuyGrave(String infraccionMuyGrave) {
		this.infraccionMuyGrave = infraccionMuyGrave;
	}

	public String getExpiracionBrevete() {
		return expiracionBrevete;
	}

	public void setExpiracionBrevete(String expiracionBrevete) {
		this.expiracionBrevete = expiracionBrevete;
	}

	@Override
	public String toString() {
		return "Chofer [codigo=" + codigo + ", codigoUsuarioTransporte=" + codigoUsuarioTransporte + ", nombre="
				+ nombre + ", apellido=" + apellido + ", brevete=" + brevete + ", tipo=" + tipo + ", telefono="
				+ telefono + ", email=" + email + ", password=" + password + ", tipoDocumento=" + tipoDocumento
				+ ", numDocumento=" + numDocumento + ", foto=" + foto + ", fechaRegistro=" + fechaRegistro
				+ ", fechaModifica=" + fechaModifica + ", estado=" + estado + ", fotoBrevete=" + fotoBrevete
				+ ", extBrevete=" + extBrevete + ", usuarioTransporte=" + usuarioTransporte + ", olvido=" + olvido
				+ ", expiracionBrevete=" + expiracionBrevete + ", multa=" + multa + ", infraccionGrave="
				+ infraccionGrave + ", infraccionMuyGrave=" + infraccionMuyGrave + "]";
	}




		
}
