package pe.com.boox.cargo.dao.repository;

import java.util.List;

import pe.com.boox.cargo.model.Guia;
import pe.com.boox.cargo.model.GuiaDetalle;

public interface GuiaDetalleRepository {

	void insertGuiaDetalle(GuiaDetalle guia) throws Exception;
	public void deleteGuiaDetalle(GuiaDetalle guia) throws Exception;
	public GuiaDetalle getGuiaDetalle(GuiaDetalle guia) throws Exception;
	public List<GuiaDetalle> listGuiaDetalle(GuiaDetalle guia) throws Exception;
	
}
