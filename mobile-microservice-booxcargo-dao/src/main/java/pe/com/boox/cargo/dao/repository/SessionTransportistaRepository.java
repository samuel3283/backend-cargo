package pe.com.boox.cargo.dao.repository;

import pe.com.boox.cargo.model.SessionTransportista;

public interface SessionTransportistaRepository {

	void insertSession(SessionTransportista session) throws Exception;
	SessionTransportista getSessionByToken(SessionTransportista session) throws Exception;
	void deleteSession(SessionTransportista session) throws Exception;

	
}
