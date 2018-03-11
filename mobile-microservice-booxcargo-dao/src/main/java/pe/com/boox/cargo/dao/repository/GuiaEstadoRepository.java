package pe.com.boox.cargo.dao.repository;

import java.util.List;

import pe.com.boox.cargo.model.Alertas;
import pe.com.boox.cargo.model.Guia;
import pe.com.boox.cargo.model.GuiaEstado;

public interface GuiaEstadoRepository {

	void insertaGuiaEstado(Guia guia) throws Exception;
	public GuiaEstado getGuiaEstado(Guia guia) throws Exception;
	public List<GuiaEstado> listGuiaEstado(Guia guia) throws Exception;
	public List<Alertas> listAlerta(String ruc) throws Exception;
	public List<Alertas> listAlertaTransporte(Integer codigoUsuarioTransporte) throws Exception;
	public List<GuiaEstado> listGuiaEstadoAlerta(Guia guia) throws Exception;	
}
