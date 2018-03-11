package pe.com.boox.cargo.dao.repository;

import java.util.List;

import pe.com.boox.cargo.model.UsuarioTransporte;

public interface UsuarioTransporteRepository {

	void insertUsuarioTransporte(UsuarioTransporte request) throws Exception;
	void updateFotoUsuarioTransporte(UsuarioTransporte request) throws Exception;
	void updateOlvido(UsuarioTransporte request) throws Exception;
	void updatePassword(UsuarioTransporte request) throws Exception;
	UsuarioTransporte getUsuarioTransporte(UsuarioTransporte request) throws Exception;
	UsuarioTransporte getUsuarioTransporteByEmail(UsuarioTransporte request) throws Exception;
	UsuarioTransporte getLoginUsuarioTransporte(UsuarioTransporte request) throws Exception;
	List<UsuarioTransporte> validaCodeEmail(UsuarioTransporte request) throws Exception;
	
}
