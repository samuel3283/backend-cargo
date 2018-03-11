package pe.com.boox.cargo.service;

import pe.com.boox.cargo.model.Chofer;
import pe.com.boox.cargo.model.HeaderRq;
import pe.com.boox.cargo.model.Session;
import pe.com.boox.cargo.model.SessionChofer;
import pe.com.boox.cargo.model.SessionTransportista;
import pe.com.boox.cargo.model.UsuarioEmpresa;
import pe.com.boox.cargo.model.UsuarioTransporte;


public interface SessionChoferService {

	SessionChofer validaSession(Chofer request,HeaderRq headerRq) throws Exception;
	SessionChofer loginDirect(Chofer request,HeaderRq headerRq) throws Exception;
	SessionChofer getSessionByToken(String token) throws Exception;
	void deleteSession(String token) throws Exception;
	
	String generaCodeEmail(Chofer request)  throws Exception;
	void validaCodeEmail(Chofer request)  throws Exception;
	void upddateClaveChofer(Chofer request)  throws Exception;
	
}
