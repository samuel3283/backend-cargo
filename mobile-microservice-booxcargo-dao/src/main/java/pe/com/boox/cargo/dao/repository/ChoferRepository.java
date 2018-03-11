package pe.com.boox.cargo.dao.repository;

import java.util.List;

import pe.com.boox.cargo.model.Chofer;
import pe.com.boox.cargo.model.Empresa;
import pe.com.boox.cargo.model.UsuarioTransporte;

public interface ChoferRepository {

	void insertChofer(Chofer request) throws Exception;
	Integer getMaxIdChofer(Chofer request) throws Exception;
	void updateChofer(Chofer request) throws Exception;
	Chofer getChofer(Chofer request) throws Exception;
	void updatePasswordNew(Chofer request) throws Exception;
	Integer getChoferNew(Chofer request) throws Exception;
	Chofer getLoginChofer(Chofer request) throws Exception;
	Chofer getChoferByDocumento(Chofer request) throws Exception;
	List<Chofer> listChofer(Chofer request) throws Exception;
	void updateAdjunto(Chofer request, String foto) throws Exception;
	void updateOlvido(Chofer request) throws Exception;
	void updatePassword(Chofer request) throws Exception;
	
}
