package pe.com.boox.cargo.service;

import org.springframework.web.multipart.MultipartFile;

import pe.com.boox.cargo.model.HeaderRq;
import pe.com.boox.cargo.model.UsuarioTransporte;

public interface UsuarioTransporteService {
	
	void insertUsuarioTransporte(UsuarioTransporte request) throws Exception;
	UsuarioTransporte getUsuarioTransporte(UsuarioTransporte request) throws Exception;
	UsuarioTransporte getLoginUsuarioTransporte(UsuarioTransporte request) throws Exception;
	public void uploadFile(String tipo, HeaderRq headerRq, MultipartFile uploadfile) throws Exception;
}
