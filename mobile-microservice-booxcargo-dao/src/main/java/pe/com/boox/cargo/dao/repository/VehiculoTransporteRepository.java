package pe.com.boox.cargo.dao.repository;

import java.util.List;

import pe.com.boox.cargo.model.Chofer;
import pe.com.boox.cargo.model.Guia;
import pe.com.boox.cargo.model.VehiculoTransporte;

public interface VehiculoTransporteRepository {

	void insertVehiculoTransporte(VehiculoTransporte request) throws Exception;
	void updateVehiculoTransporte(VehiculoTransporte request) throws Exception;
	void asignaChofer(VehiculoTransporte request) throws Exception;
	public void updateAdjuntoVehiculoTransporte(VehiculoTransporte request, String foto, String tipo) throws Exception;
	VehiculoTransporte getVehiculoTransporte(VehiculoTransporte request) throws Exception;
	public List<VehiculoTransporte> listVehiculoTransporte(VehiculoTransporte request) throws Exception;
	public Integer getMaxIdVehiculoTransporte(VehiculoTransporte request) throws Exception;
	VehiculoTransporte getVehiculoByUserAndPlaca(VehiculoTransporte request) throws Exception;
	public VehiculoTransporte getVehiculoByChofer(Chofer request) throws Exception;
	VehiculoTransporte getVehiculoByGuia(Guia request) throws Exception;
}
