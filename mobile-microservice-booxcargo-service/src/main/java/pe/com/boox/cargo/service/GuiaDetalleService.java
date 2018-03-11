package pe.com.boox.cargo.service;

import java.util.List;

import pe.com.boox.cargo.model.Guia;
import pe.com.boox.cargo.model.GuiaDetalle;

public interface GuiaDetalleService {

	void insertGuiaDetalle(GuiaDetalle guia) throws Exception;
	List<GuiaDetalle> listGuiaDetalle(GuiaDetalle guia) throws Exception;
	
}
