package pe.com.boox.cargo.service;

import pe.com.boox.cargo.core.BooxException;
import pe.com.boox.cargo.model.UsuarioEmpresa;

public interface UsuarioEmpresaService {

	void insertUsuarioEmpresa(UsuarioEmpresa usuarioEmpresa) throws Exception;
	UsuarioEmpresa getUsuarioEmpresa(UsuarioEmpresa usuarioEmpresa) throws Exception;
	UsuarioEmpresa getLoginUsuarioEmpresa(UsuarioEmpresa usuarioEmpresa) throws Exception;
	
}
