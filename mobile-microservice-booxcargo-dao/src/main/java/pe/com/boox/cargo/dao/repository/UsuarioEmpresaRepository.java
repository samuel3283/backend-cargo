package pe.com.boox.cargo.dao.repository;

import pe.com.boox.cargo.model.UsuarioEmpresa;

public interface UsuarioEmpresaRepository {

	void insertUsuarioEmpresa(UsuarioEmpresa usuarioEmpresa) throws Exception;
	UsuarioEmpresa getUsuarioEmpresa(UsuarioEmpresa usuarioEmpresa) throws Exception;
	UsuarioEmpresa getLoginUsuarioEmpresa(UsuarioEmpresa usuarioEmpresa) throws Exception;
	
}
