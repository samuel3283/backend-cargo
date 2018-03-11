package pe.com.boox.cargo.service;

import java.util.List;
import java.util.Map;

import pe.com.boox.cargo.model.Alertas;
import pe.com.boox.cargo.model.Guia;
import pe.com.boox.cargo.model.Ruta;
import pe.com.boox.cargo.model.Subasta;

public interface GuiaService {

	void insertRuta(Ruta ruta, String token) throws Exception;
	void addCoordenadas(Ruta ruta, String token) throws Exception;
	void addCoordenadasV2(Ruta ruta, String token) throws Exception;
	void addLstCoordenadas(List<Ruta> ruta, String token) throws Exception;
	String insertGuia(Guia guia, String token) throws Exception;
	void updateIndicadorInicio(Guia guia, String token) throws Exception;
	void updateIndicadorFin(Guia guia, String token) throws Exception;
	void updateGuia(Guia guia, String token) throws Exception;
	public void updateEstadoGuia(Guia guia, String token) throws Exception;
	void insertGuia(Guia guia) throws Exception;
	Guia getGuia(Guia guia) throws Exception;
	Guia getGuiaV2(Guia guia) throws Exception;
	List<Guia> listGuia(Guia guia) throws Exception;
	List<Guia> listVerifyGuia(Guia guia, String token) throws Exception;
	List<Guia> listMiGuia(Guia guia, String token) throws Exception;
	List<Guia> listGuia(Guia guia, String token) throws Exception;
	List<Guia> listGuiaSubasta(Guia guia, String token) throws Exception;
	List<Guia> listGuiaUltimos(Guia guia, String token) throws Exception;
	List<Guia> listGuiaAll(Guia guia, String token) throws Exception;
	List<Guia> listGuiaMiViajeV2(Guia guia, String token) throws Exception;
	List<Guia> listGuiaMiViaje(Guia guia, String token) throws Exception;
	Integer getCantidadGuia(Guia guia) throws Exception;
	void iniciarRuta(Guia guia, String token) throws Exception ;
	void updateEstadoRuta(Guia guia, String token, Integer estado) throws Exception;
	public List<Guia> listChoferMiViaje(String token,Integer estado) throws Exception;	
	public List<Guia> listChoferMiViajeV2(String token,Integer estado) throws Exception;	
	public List<Alertas> listAlerta(String ruc) throws Exception;
	public List<Alertas> listAlertaTransporte(String token) throws Exception ;
	public List<Guia> listMiGuiaViaje(Guia guia, String token) throws Exception;
	public List<Guia> listMisCargas(String token) throws Exception;	
	Map<String, Object> rptGuiaMensual(String token) throws Exception;
	public List<Map<String, Object>> rptGuiaTIempos(String token) throws Exception;
}
