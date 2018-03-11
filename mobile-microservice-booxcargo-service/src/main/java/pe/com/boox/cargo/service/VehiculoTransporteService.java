package pe.com.boox.cargo.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import pe.com.boox.cargo.model.Guia;
import pe.com.boox.cargo.model.HeaderRq;
import pe.com.boox.cargo.model.VehiculoTransporte;

public interface VehiculoTransporteService {
	
	Integer insertVehiculoTransporte(VehiculoTransporte request, String token) throws Exception;
	Integer updateVehiculoTransporte(VehiculoTransporte request, String token) throws Exception;
	VehiculoTransporte getVehiculoTransporte(VehiculoTransporte request) throws Exception;
	public List<VehiculoTransporte> listVehiculoTransporte(VehiculoTransporte request, String token) throws Exception;
	public List<VehiculoTransporte> listShortVehiculoTransporte(VehiculoTransporte request, String token) throws Exception;
	void uploadFile(String tipo, HeaderRq headerRq, MultipartFile uploadfile) throws Exception;
	public void asignaChofer(VehiculoTransporte request,String token) throws Exception;
	VehiculoTransporte getVehiculoByGuia(Guia request) throws Exception;
	
}
