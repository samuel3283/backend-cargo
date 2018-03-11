package pe.com.boox.cargo.service;

import pe.com.boox.cargo.model.HeaderRq;
import pe.com.boox.cargo.model.Session;
import pe.com.boox.cargo.model.SessionTransportista;
import pe.com.boox.cargo.model.UsuarioEmpresa;
import pe.com.boox.cargo.model.UsuarioTransporte;


public interface SessionTransportistaService {

	SessionTransportista validaSession(UsuarioTransporte request,HeaderRq headerRq) throws Exception;
	SessionTransportista loginDirect(UsuarioTransporte request,HeaderRq headerRq) throws Exception;
	SessionTransportista getSessionByToken(String token) throws Exception;
	void deleteSession(String token) throws Exception;
	
	String generaCodeEmail(UsuarioTransporte request)  throws Exception;
	void validaCodeEmail(UsuarioTransporte request)  throws Exception;
	void upddateClaveConductor(UsuarioTransporte request)  throws Exception;
	
}
