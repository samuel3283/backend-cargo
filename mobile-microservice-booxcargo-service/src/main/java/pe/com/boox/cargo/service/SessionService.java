package pe.com.boox.cargo.service;

import java.util.Map;

import pe.com.boox.cargo.model.HeaderRq;
import pe.com.boox.cargo.model.Session;
import pe.com.boox.cargo.model.UsuarioEmpresa;


public interface SessionService {

	Session validaSession(UsuarioEmpresa usuarioEmpresa,HeaderRq headerRq) throws Exception;
	Session loginDirect(UsuarioEmpresa usuarioEmpresa,HeaderRq headerRq) throws Exception;
	Session getSessionByToken(String token) throws Exception;
	void deleteSession(String token) throws Exception;
	Map<String, Object> getEstadistica(HeaderRq headerRq) throws Exception;
}
