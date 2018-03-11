package pe.com.boox.cargo.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class Guia {
	
	private Integer codigo;
	private String ruc;
	private String numeroGuia;
	private String flete;
	private String fechaEmision;
	private String fechaIniTraslado;
	private String fechaFinTraslado;
	private String horaIniTraslado;
	private String horaFinTraslado;
	@JsonInclude(Include.NON_NULL)
	private Integer codigoChofer;
	@JsonInclude(Include.NON_NULL)
	private String viaTipoOrigen;
	@JsonInclude(Include.NON_NULL)
	private String viaNombreOrigen;
	@JsonInclude(Include.NON_NULL)
	private String numeroOrigen;
	@JsonInclude(Include.NON_NULL)
	private String interiorOrigen;
	@JsonInclude(Include.NON_NULL)
	private String zonaOrigen;
	@JsonInclude(Include.NON_NULL)
	private String distritoOrigen;
	@JsonInclude(Include.NON_NULL)
	private String provinciaOrigen;
	@JsonInclude(Include.NON_NULL)
	private String departamentoOrigen;
	@JsonInclude(Include.NON_NULL)
	private String viaTipoDestino;
	@JsonInclude(Include.NON_NULL)
	private String viaNombreDestino;
	@JsonInclude(Include.NON_NULL)
	private String numeroDestino;
	@JsonInclude(Include.NON_NULL)
	private String interiorDestino;
	@JsonInclude(Include.NON_NULL)
	private String zonaDestino;
	@JsonInclude(Include.NON_NULL)
	private String distritoDestino;
	@JsonInclude(Include.NON_NULL)
	private String provinciaDestino;
	@JsonInclude(Include.NON_NULL)
	private String departamentoDestino;
	@JsonInclude(Include.NON_NULL)
	private String razonSocialRemitente;
	@JsonInclude(Include.NON_NULL)
	private String rucRemitente;
	@JsonInclude(Include.NON_NULL)
	private String tipoDocRemitente;
	@JsonInclude(Include.NON_NULL)
	private String numeroDocRemitente;
	@JsonInclude(Include.NON_NULL)
	private String nombreContactoRemitente;
	@JsonInclude(Include.NON_NULL)
	private String telefonoContactoRemitente;
	@JsonInclude(Include.NON_NULL)
	private String razonSocialDestinatario;
	@JsonInclude(Include.NON_NULL)
	private String rucDestinatario;
	@JsonInclude(Include.NON_NULL)
	private String tipoDocDestinatario;
	@JsonInclude(Include.NON_NULL)
	private String numeroDocDestinatario;
	@JsonInclude(Include.NON_NULL)
	private String nombreContactoDestinatario;
	@JsonInclude(Include.NON_NULL)
	private String telefonoContactoDestinatario;
	private Integer estado;
	private String fecRegistro;
	private List<GuiaDetalle> listado;
	@JsonInclude(Include.NON_NULL)
	private String desViaTipoOrigen;
	@JsonInclude(Include.NON_NULL)
	private String desViaTipoDestino;
	@JsonInclude(Include.NON_NULL)
	private String razonSocial;
	@JsonInclude(Include.NON_NULL)
	private String desZonaOrigen;
	@JsonInclude(Include.NON_NULL)
	private String desDistritoOrigen;
	@JsonInclude(Include.NON_NULL)
	private String desProvinciaOrigen;
	@JsonInclude(Include.NON_NULL)
	private String desDepartamentoOrigen;
	@JsonInclude(Include.NON_NULL)
	private String desZonaDestino;
	@JsonInclude(Include.NON_NULL)
	private String desDistritoDestino;
	@JsonInclude(Include.NON_NULL)
	private String desProvinciaDestino;
	@JsonInclude(Include.NON_NULL)
	private String desDepartamentoDestino;
	@JsonInclude(Include.NON_NULL)
	private String alerta;
	
	private String desEstado;
	@JsonInclude(Include.NON_NULL)
	private Subasta subasta;
	@JsonInclude(Include.NON_NULL)
	private List<Subasta> lstSubasta;;
	@JsonInclude(Include.NON_NULL)
	private List<GuiaEstado> alertas;
	
	private double latOri;
	private double lngOri;
	private double latDes;
	private double lngDes;
	
	private Ruta rutaOri;
	@JsonInclude(Include.NON_NULL)
	private List<Ruta> rutas;
	@JsonInclude(Include.NON_NULL)
	private List<Position> position;
	private List<Location> recorrido;
	@JsonInclude(Include.NON_NULL)
	private List<Coordenadas> coordenadas;
	private Ruta rutaDes;
	@JsonInclude(Include.NON_NULL)
	private List<Location> location;
	private String direccionOrigen;
	private String direccionDestino;
	@JsonInclude(Include.NON_NULL)
	private String nota;
	@JsonInclude(Include.NON_NULL)
	private VehiculoTransporte vehiculo;
	@JsonInclude(Include.NON_NULL)
	private Integer verificacionInicio;
	@JsonInclude(Include.NON_NULL)
	private Integer verificacionFin;	
	
	@JsonIgnore
	private boolean showSubasta;	
	@JsonIgnore
	private boolean showDetalle;	
	
	
	public Guia() {
		super();
		showSubasta = Boolean.FALSE;
		showDetalle = Boolean.FALSE;
		codigo = new Integer(1);
		listado = new ArrayList<GuiaDetalle>();
		lstSubasta = new ArrayList<Subasta>();
		this.subasta = new Subasta();
		rutaOri = new Ruta();
		rutaDes = new Ruta();
		rutas = new ArrayList<Ruta>();
		recorrido = new ArrayList<Location>();
		position = new ArrayList<Position>();
		location = new ArrayList<Location>();
		vehiculo = new VehiculoTransporte();
		alertas = new ArrayList<GuiaEstado>();
	}
	
	
	
	public boolean isShowDetalle() {
		return showDetalle;
	}



	public void setShowDetalle(boolean showDetalle) {
		this.showDetalle = showDetalle;
	}



	public boolean isShowSubasta() {
		return showSubasta;
	}



	public void setShowSubasta(boolean showSubasta) {
		this.showSubasta = showSubasta;
	}



	public Integer getCodigoChofer() {
		return codigoChofer;
	}


	public void setCodigoChofer(Integer codigoChofer) {
		this.codigoChofer = codigoChofer;
	}


	public List<GuiaEstado> getAlertas() {
		return alertas;
	}


	public void setAlertas(List<GuiaEstado> alertas) {
		this.alertas = alertas;
	}


	public List<Location> getLocation() {
		return location;
	}


	public void setLocation(List<Location> location) {
		this.location = location;
	}


	public List<Position> getPosition() {
		return position;
	}


	public void setPosition(List<Position> position) {
		this.position = position;
	}


	public List<Subasta> getLstSubasta() {
		return lstSubasta;
	}


	public void setLstSubasta(List<Subasta> lstSubasta) {
		this.lstSubasta = lstSubasta;
	}


	public List<GuiaDetalle> getListado() {
		return listado;
	}

	public void setListado(List<GuiaDetalle> listado) {
		this.listado = listado;
	}

	public String getFecRegistro() {
		return fecRegistro;
	}

	public void setFecRegistro(String fecRegistro) {
		this.fecRegistro = fecRegistro;
	}

	public Integer getEstado() {
		return estado;
	}
	public void setEstado(Integer estado) {
		this.estado = estado;
	}
	public String getFechaEmision() {
		return fechaEmision;
	}
	public void setFechaEmision(String fechaEmision) {
		this.fechaEmision = fechaEmision;
	}
	public String getFechaIniTraslado() {
		return fechaIniTraslado;
	}
	public void setFechaIniTraslado(String fechaIniTraslado) {
		this.fechaIniTraslado = fechaIniTraslado;
	}
	public String getViaTipoOrigen() {
		return viaTipoOrigen;
	}
	public void setViaTipoOrigen(String viaTipoOrigen) {
		this.viaTipoOrigen = viaTipoOrigen;
	}
	public String getViaNombreOrigen() {
		return viaNombreOrigen;
	}
	public void setViaNombreOrigen(String viaNombreOrigen) {
		this.viaNombreOrigen = viaNombreOrigen;
	}
	public String getNumeroOrigen() {
		return numeroOrigen;
	}
	public void setNumeroOrigen(String numeroOrigen) {
		this.numeroOrigen = numeroOrigen;
	}
	public String getInteriorOrigen() {
		return interiorOrigen;
	}
	public void setInteriorOrigen(String interiorOrigen) {
		this.interiorOrigen = interiorOrigen;
	}
	public String getZonaOrigen() {
		return zonaOrigen;
	}
	public void setZonaOrigen(String zonaOrigen) {
		this.zonaOrigen = zonaOrigen;
	}
	public String getDistritoOrigen() {
		return distritoOrigen;
	}
	public void setDistritoOrigen(String distritoOrigen) {
		this.distritoOrigen = distritoOrigen;
	}
	public String getProvinciaOrigen() {
		return provinciaOrigen;
	}
	public void setProvinciaOrigen(String provinciaOrigen) {
		this.provinciaOrigen = provinciaOrigen;
	}
	public String getDepartamentoOrigen() {
		return departamentoOrigen;
	}
	public void setDepartamentoOrigen(String departamentoOrigen) {
		this.departamentoOrigen = departamentoOrigen;
	}
	public String getViaTipoDestino() {
		return viaTipoDestino;
	}
	public void setViaTipoDestino(String viaTipoDestino) {
		this.viaTipoDestino = viaTipoDestino;
	}
	public String getViaNombreDestino() {
		return viaNombreDestino;
	}
	public void setViaNombreDestino(String viaNombreDestino) {
		this.viaNombreDestino = viaNombreDestino;
	}
	public String getNumeroDestino() {
		return numeroDestino;
	}
	public void setNumeroDestino(String numeroDestino) {
		this.numeroDestino = numeroDestino;
	}
	public String getInteriorDestino() {
		return interiorDestino;
	}
	public void setInteriorDestino(String interiorDestino) {
		this.interiorDestino = interiorDestino;
	}
	public String getZonaDestino() {
		return zonaDestino;
	}
	public void setZonaDestino(String zonaDestino) {
		this.zonaDestino = zonaDestino;
	}
	public String getDistritoDestino() {
		return distritoDestino;
	}
	public void setDistritoDestino(String distritoDestino) {
		this.distritoDestino = distritoDestino;
	}
	public String getProvinciaDestino() {
		return provinciaDestino;
	}
	public void setProvinciaDestino(String provinciaDestino) {
		this.provinciaDestino = provinciaDestino;
	}
	public String getDepartamentoDestino() {
		return departamentoDestino;
	}
	public void setDepartamentoDestino(String departamentoDestino) {
		this.departamentoDestino = departamentoDestino;
	}
	public String getRazonSocialRemitente() {
		return razonSocialRemitente;
	}
	public void setRazonSocialRemitente(String razonSocialRemitente) {
		this.razonSocialRemitente = razonSocialRemitente;
	}
	public String getRucRemitente() {
		return rucRemitente;
	}
	public void setRucRemitente(String rucRemitente) {
		this.rucRemitente = rucRemitente;
	}
	public String getTipoDocRemitente() {
		return tipoDocRemitente;
	}
	public void setTipoDocRemitente(String tipoDocRemitente) {
		this.tipoDocRemitente = tipoDocRemitente;
	}
	public String getNumeroDocRemitente() {
		return numeroDocRemitente;
	}
	public void setNumeroDocRemitente(String numeroDocRemitente) {
		this.numeroDocRemitente = numeroDocRemitente;
	}
	public String getRazonSocialDestinatario() {
		return razonSocialDestinatario;
	}
	public void setRazonSocialDestinatario(String razonSocialDestinatario) {
		this.razonSocialDestinatario = razonSocialDestinatario;
	}
	public String getRucDestinatario() {
		return rucDestinatario;
	}
	public void setRucDestinatario(String rucDestinatario) {
		this.rucDestinatario = rucDestinatario;
	}
	public String getTipoDocDestinatario() {
		return tipoDocDestinatario;
	}
	public void setTipoDocDestinatario(String tipoDocDestinatario) {
		this.tipoDocDestinatario = tipoDocDestinatario;
	}
	public String getNumeroDocDestinatario() {
		return numeroDocDestinatario;
	}
	public void setNumeroDocDestinatario(String numeroDocDestinatario) {
		this.numeroDocDestinatario = numeroDocDestinatario;
	}
	public String getRuc() {
		return ruc;
	}
	public void setRuc(String ruc) {
		this.ruc = ruc;
	}
	public String getNumeroGuia() {
		return numeroGuia;
	}
	public void setNumeroGuia(String numeroGuia) {
		this.numeroGuia = numeroGuia;
	}
	
	public Integer getCodigo() {
		return codigo;
	}
	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	
	public String getFlete() {
		return flete;
	}

	public void setFlete(String flete) {
		this.flete = flete;
	}

	public String getFechaFinTraslado() {
		return fechaFinTraslado;
	}

	public void setFechaFinTraslado(String fechaFinTraslado) {
		this.fechaFinTraslado = fechaFinTraslado;
	}

	public String getHoraIniTraslado() {
		return horaIniTraslado;
	}

	public void setHoraIniTraslado(String horaIniTraslado) {
		this.horaIniTraslado = horaIniTraslado;
	}

	public String getHoraFinTraslado() {
		return horaFinTraslado;
	}

	public void setHoraFinTraslado(String horaFinTraslado) {
		this.horaFinTraslado = horaFinTraslado;
	}

	public String getNombreContactoRemitente() {
		return nombreContactoRemitente;
	}

	public void setNombreContactoRemitente(String nombreContactoRemitente) {
		this.nombreContactoRemitente = nombreContactoRemitente;
	}

	public String getTelefonoContactoRemitente() {
		return telefonoContactoRemitente;
	}

	public void setTelefonoContactoRemitente(String telefonoContactoRemitente) {
		this.telefonoContactoRemitente = telefonoContactoRemitente;
	}

	public String getNombreContactoDestinatario() {
		return nombreContactoDestinatario;
	}

	public void setNombreContactoDestinatario(String nombreContactoDestinatario) {
		this.nombreContactoDestinatario = nombreContactoDestinatario;
	}

	public String getTelefonoContactoDestinatario() {
		return telefonoContactoDestinatario;
	}

	public void setTelefonoContactoDestinatario(String telefonoContactoDestinatario) {
		this.telefonoContactoDestinatario = telefonoContactoDestinatario;
	}
	
	public String getDesViaTipoOrigen() {
		return desViaTipoOrigen;
	}

	public void setDesViaTipoOrigen(String desViaTipoOrigen) {
		this.desViaTipoOrigen = desViaTipoOrigen;
	}

	public String getDesViaTipoDestino() {
		return desViaTipoDestino;
	}

	public void setDesViaTipoDestino(String desViaTipoDestino) {
		this.desViaTipoDestino = desViaTipoDestino;
	}

	
	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	
	
	public String getDesZonaOrigen() {
		return desZonaOrigen;
	}

	public void setDesZonaOrigen(String desZonaOrigen) {
		this.desZonaOrigen = desZonaOrigen;
	}

	public String getDesDistritoOrigen() {
		return desDistritoOrigen;
	}

	public void setDesDistritoOrigen(String desDistritoOrigen) {
		this.desDistritoOrigen = desDistritoOrigen;
	}

	public String getDesProvinciaOrigen() {
		return desProvinciaOrigen;
	}

	public void setDesProvinciaOrigen(String desProvinciaOrigen) {
		this.desProvinciaOrigen = desProvinciaOrigen;
	}

	public String getDesDepartamentoOrigen() {
		return desDepartamentoOrigen;
	}

	public void setDesDepartamentoOrigen(String desDepartamentoOrigen) {
		this.desDepartamentoOrigen = desDepartamentoOrigen;
	}

	public String getDesZonaDestino() {
		return desZonaDestino;
	}

	public void setDesZonaDestino(String desZonaDestino) {
		this.desZonaDestino = desZonaDestino;
	}

	public String getDesDistritoDestino() {
		return desDistritoDestino;
	}

	public void setDesDistritoDestino(String desDistritoDestino) {
		this.desDistritoDestino = desDistritoDestino;
	}

	public String getDesProvinciaDestino() {
		return desProvinciaDestino;
	}

	public void setDesProvinciaDestino(String desProvinciaDestino) {
		this.desProvinciaDestino = desProvinciaDestino;
	}

	public String getDesDepartamentoDestino() {
		return desDepartamentoDestino;
	}

	public void setDesDepartamentoDestino(String desDepartamentoDestino) {
		this.desDepartamentoDestino = desDepartamentoDestino;
	}

	public String getDesEstado() {
		return desEstado;
	}

	public void setDesEstado(String desEstado) {
		this.desEstado = desEstado;
	}
	
	
	public Subasta getSubasta() {
		return subasta;
	}

	public void setSubasta(Subasta subasta) {
		this.subasta = subasta;
	}

	
	public double getLatOri() {
		return latOri;
	}


	public void setLatOri(double latOri) {
		this.latOri = latOri;
	}


	public double getLngOri() {
		return lngOri;
	}


	public void setLngOri(double lngOri) {
		this.lngOri = lngOri;
	}


	public double getLatDes() {
		return latDes;
	}


	public void setLatDes(double latDes) {
		this.latDes = latDes;
	}


	public double getLngDes() {
		return lngDes;
	}


	public void setLngDes(double lngDes) {
		this.lngDes = lngDes;
	}


	public Ruta getRutaOri() {
		return rutaOri;
	}


	public void setRutaOri(Ruta rutaOri) {
		this.rutaOri = rutaOri;
	}


	public List<Ruta> getRutas() {
		return rutas;
	}


	public void setRutas(List<Ruta> rutas) {
		this.rutas = rutas;
	}


	public Ruta getRutaDes() {
		return rutaDes;
	}


	public void setRutaDes(Ruta rutaDes) {
		this.rutaDes = rutaDes;
	}


	public List<Location> getRecorrido() {
		return recorrido;
	}


	public void setRecorrido(List<Location> recorrido) {
		this.recorrido = recorrido;
	}

	

	public String getDireccionOrigen() {
		return direccionOrigen;
	}


	public void setDireccionOrigen(String direccionOrigen) {
		this.direccionOrigen = direccionOrigen;
	}


	public String getDireccionDestino() {
		return direccionDestino;
	}


	public void setDireccionDestino(String direccionDestino) {
		this.direccionDestino = direccionDestino;
	}


	public String getNota() {
		return nota;
	}


	public void setNota(String nota) {
		this.nota = nota;
	}


	public String getAlerta() {
		return alerta;
	}


	public void setAlerta(String alerta) {
		this.alerta = alerta;
	}


	public VehiculoTransporte getVehiculo() {
		return vehiculo;
	}


	public void setVehiculo(VehiculoTransporte vehiculo) {
		this.vehiculo = vehiculo;
	}


	public Integer getVerificacionInicio() {
		return verificacionInicio;
	}


	public void setVerificacionInicio(Integer verificacionInicio) {
		this.verificacionInicio = verificacionInicio;
	}


	public Integer getVerificacionFin() {
		return verificacionFin;
	}


	public void setVerificacionFin(Integer verificacionFin) {
		this.verificacionFin = verificacionFin;
	}


	public List<Coordenadas> getCoordenadas() {
		return coordenadas;
	}


	public void setCoordenadas(List<Coordenadas> coordenadas) {
		this.coordenadas = coordenadas;
	}


	@Override
	public String toString() {
		return "Guia [codigo=" + codigo + ", ruc=" + ruc + ", numeroGuia=" + numeroGuia + ", flete=" + flete
				+ ", fechaEmision=" + fechaEmision + ", fechaIniTraslado=" + fechaIniTraslado + ", fechaFinTraslado="
				+ fechaFinTraslado + ", horaIniTraslado=" + horaIniTraslado + ", horaFinTraslado=" + horaFinTraslado
				+ ", codigoChofer=" + codigoChofer + ", viaTipoOrigen=" + viaTipoOrigen + ", viaNombreOrigen="
				+ viaNombreOrigen + ", numeroOrigen=" + numeroOrigen + ", interiorOrigen=" + interiorOrigen
				+ ", zonaOrigen=" + zonaOrigen + ", distritoOrigen=" + distritoOrigen + ", provinciaOrigen="
				+ provinciaOrigen + ", departamentoOrigen=" + departamentoOrigen + ", viaTipoDestino=" + viaTipoDestino
				+ ", viaNombreDestino=" + viaNombreDestino + ", numeroDestino=" + numeroDestino + ", interiorDestino="
				+ interiorDestino + ", zonaDestino=" + zonaDestino + ", distritoDestino=" + distritoDestino
				+ ", provinciaDestino=" + provinciaDestino + ", departamentoDestino=" + departamentoDestino
				+ ", razonSocialRemitente=" + razonSocialRemitente + ", rucRemitente=" + rucRemitente
				+ ", tipoDocRemitente=" + tipoDocRemitente + ", numeroDocRemitente=" + numeroDocRemitente
				+ ", nombreContactoRemitente=" + nombreContactoRemitente + ", telefonoContactoRemitente="
				+ telefonoContactoRemitente + ", razonSocialDestinatario=" + razonSocialDestinatario
				+ ", rucDestinatario=" + rucDestinatario + ", tipoDocDestinatario=" + tipoDocDestinatario
				+ ", numeroDocDestinatario=" + numeroDocDestinatario + ", nombreContactoDestinatario="
				+ nombreContactoDestinatario + ", telefonoContactoDestinatario=" + telefonoContactoDestinatario
				+ ", estado=" + estado + ", fecRegistro=" + fecRegistro + ", listado=" + listado + ", desViaTipoOrigen="
				+ desViaTipoOrigen + ", desViaTipoDestino=" + desViaTipoDestino + ", razonSocial=" + razonSocial
				+ ", desZonaOrigen=" + desZonaOrigen + ", desDistritoOrigen=" + desDistritoOrigen
				+ ", desProvinciaOrigen=" + desProvinciaOrigen + ", desDepartamentoOrigen=" + desDepartamentoOrigen
				+ ", desZonaDestino=" + desZonaDestino + ", desDistritoDestino=" + desDistritoDestino
				+ ", desProvinciaDestino=" + desProvinciaDestino + ", desDepartamentoDestino=" + desDepartamentoDestino
				+ ", alerta=" + alerta + ", desEstado=" + desEstado + ", subasta=" + subasta + ", lstSubasta="
				+ lstSubasta + ", alertas=" + alertas + ", latOri=" + latOri + ", lngOri=" + lngOri + ", latDes="
				+ latDes + ", lngDes=" + lngDes + ", rutaOri=" + rutaOri + ", rutas=" + rutas + ", position=" + position
				+ ", recorrido=" + recorrido + ", rutaDes=" + rutaDes + ", location=" + location + ", direccionOrigen="
				+ direccionOrigen + ", direccionDestino=" + direccionDestino + ", nota=" + nota + ", vehiculo="
				+ vehiculo + ", verificacionInicio=" + verificacionInicio + ", verificacionFin=" + verificacionFin
				+ "]";
	}




}
