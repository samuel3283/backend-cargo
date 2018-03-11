package pe.com.boox.cargo.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class VehiculoTransporte {

	private Integer codigo;;
	private String placa;
	private String marca;
	private String modelo;
	private String anio;
	private String capacidad;
	@JsonInclude(Include.NON_NULL)
	private String tpropiedad;
	@JsonInclude(Include.NON_NULL)
	private String soat;
	@JsonInclude(Include.NON_NULL)
	private String exttpropiedad;
	@JsonInclude(Include.NON_NULL)
	private String extsoat;
	private String tipo;
	private String tipoDes;
	private String categoria;
	private String alto;
	private String ancho;
	private String largo;
	@JsonInclude(Include.NON_NULL)
	private String foto1;
	@JsonInclude(Include.NON_NULL)
	private String extfoto1;
	@JsonInclude(Include.NON_NULL)
	private String foto2;
	@JsonInclude(Include.NON_NULL)
	private String foto3;
	@JsonInclude(Include.NON_NULL)
	private String fechaRegistro;
	@JsonInclude(Include.NON_NULL)
	private String fechaModifica;
	@JsonInclude(Include.NON_NULL)
	private Integer estado;
	@JsonInclude(Include.NON_NULL)
	private UsuarioTransporte usuarioTransporte;
	@JsonInclude(Include.NON_NULL)
	private List<BeanUtil> tipoCarga;
	@JsonInclude(Include.NON_NULL)
	private List<BeanUtil> listaTipoCarga;
	@JsonInclude(Include.NON_NULL)
	private Chofer chofer;
	@JsonProperty("venceSoat")
	private String vencimientoSoat;
	@JsonProperty("venceRt")	
	private String vencimientoRt;
	@JsonProperty("mantenimiento")
	private String mantenimientoPreventivo;
	private String kilometraje;
	private String horometro;

	public VehiculoTransporte() {
		super();
		this.estado = new Integer(1);
		this.usuarioTransporte = new UsuarioTransporte();
		this.tipoCarga = new ArrayList<BeanUtil>();
		this.listaTipoCarga = new ArrayList<BeanUtil>();
		this.chofer = new Chofer();
	}


	public Chofer getChofer() {
		return chofer;
	}


	public void setChofer(Chofer chofer) {
		this.chofer = chofer;
	}


	public Integer getCodigo() {
		return codigo;
	}


	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}


	public String getPlaca() {
		return placa;
	}


	public void setPlaca(String placa) {
		this.placa = placa;
	}


	public String getTpropiedad() {
		return tpropiedad;
	}


	public void setTpropiedad(String tpropiedad) {
		this.tpropiedad = tpropiedad;
	}


	public String getSoat() {
		return soat;
	}


	public void setSoat(String soat) {
		this.soat = soat;
	}


	public String getFoto1() {
		return foto1;
	}


	public void setFoto1(String foto1) {
		this.foto1 = foto1;
	}


	public String getFoto2() {
		return foto2;
	}


	public void setFoto2(String foto2) {
		this.foto2 = foto2;
	}


	public String getFoto3() {
		return foto3;
	}


	public void setFoto3(String foto3) {
		this.foto3 = foto3;
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


	public String getTipo() {
		return tipo;
	}


	public void setTipo(String tipo) {
		this.tipo = tipo;
	}


	public String getCategoria() {
		return categoria;
	}


	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}


	public String getAlto() {
		return alto;
	}


	public void setAlto(String alto) {
		this.alto = alto;
	}


	public String getAncho() {
		return ancho;
	}


	public void setAncho(String ancho) {
		this.ancho = ancho;
	}


	public String getLargo() {
		return largo;
	}


	public void setLargo(String largo) {
		this.largo = largo;
	}


	public List<BeanUtil> getTipoCarga() {
		return tipoCarga;
	}


	public void setTipoCarga(List<BeanUtil> tipoCarga) {
		this.tipoCarga = tipoCarga;
	}


	public String getTipoDes() {
		return tipoDes;
	}


	public void setTipoDes(String tipoDes) {
		this.tipoDes = tipoDes;
	}


	public String getExttpropiedad() {
		return exttpropiedad;
	}


	public void setExttpropiedad(String exttpropiedad) {
		this.exttpropiedad = exttpropiedad;
	}


	public String getExtsoat() {
		return extsoat;
	}


	public void setExtsoat(String extsoat) {
		this.extsoat = extsoat;
	}


	public String getExtfoto1() {
		return extfoto1;
	}


	public void setExtfoto1(String extfoto1) {
		this.extfoto1 = extfoto1;
	}
	
	
	public List<BeanUtil> getListaTipoCarga() {
		return listaTipoCarga;
	}


	public void setListaTipoCarga(List<BeanUtil> listaTipoCarga) {
		this.listaTipoCarga = listaTipoCarga;
	}


	public String getMarca() {
		return marca;
	}


	public void setMarca(String marca) {
		this.marca = marca;
	}


	public String getModelo() {
		return modelo;
	}


	public void setModelo(String modelo) {
		this.modelo = modelo;
	}


	public String getAnio() {
		return anio;
	}


	public void setAnio(String anio) {
		this.anio = anio;
	}


	public String getCapacidad() {
		return capacidad;
	}


	public void setCapacidad(String capacidad) {
		this.capacidad = capacidad;
	}


	public String getVencimientoSoat() {
		return vencimientoSoat;
	}


	public void setVencimientoSoat(String vencimientoSoat) {
		this.vencimientoSoat = vencimientoSoat;
	}


	public String getVencimientoRt() {
		return vencimientoRt;
	}


	public void setVencimientoRt(String vencimientoRt) {
		this.vencimientoRt = vencimientoRt;
	}


	public String getMantenimientoPreventivo() {
		return mantenimientoPreventivo;
	}


	public void setMantenimientoPreventivo(String mantenimientoPreventivo) {
		this.mantenimientoPreventivo = mantenimientoPreventivo;
	}


	public String getKilometraje() {
		return kilometraje;
	}


	public void setKilometraje(String kilometraje) {
		this.kilometraje = kilometraje;
	}


	public String getHorometro() {
		return horometro;
	}


	public void setHorometro(String horometro) {
		this.horometro = horometro;
	}


	@Override
	public String toString() {
		return "VehiculoTransporte [codigo=" + codigo + ", placa=" + placa + ", marca=" + marca + ", modelo=" + modelo
				+ ", anio=" + anio + ", capacidad=" + capacidad + ", tpropiedad=" + tpropiedad + ", soat=" + soat
				+ ", exttpropiedad=" + exttpropiedad + ", extsoat=" + extsoat + ", tipo=" + tipo + ", tipoDes="
				+ tipoDes + ", categoria=" + categoria + ", alto=" + alto + ", ancho=" + ancho + ", largo=" + largo
				+ ", foto1=" + foto1 + ", extfoto1=" + extfoto1 + ", foto2=" + foto2 + ", foto3=" + foto3
				+ ", fechaRegistro=" + fechaRegistro + ", fechaModifica=" + fechaModifica + ", estado=" + estado
				+ ", usuarioTransporte=" + usuarioTransporte + ", tipoCarga=" + tipoCarga + ", listaTipoCarga="
				+ listaTipoCarga + "]";
	}



}
