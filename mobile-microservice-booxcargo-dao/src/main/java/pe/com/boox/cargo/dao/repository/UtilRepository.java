package pe.com.boox.cargo.dao.repository;

import java.util.List;

import pe.com.boox.cargo.model.BeanUtil;

public interface UtilRepository {

	void insertTipoCarga(BeanUtil request,Integer idVehiculo) throws Exception;
	public List<BeanUtil> listTipoCarga(Integer request) throws Exception;
	void deleteTipoCarga(Integer request) throws Exception;
	List<BeanUtil> listDepartamento() throws Exception;
	List<BeanUtil> listProvincia(String departamento) throws Exception;
	List<BeanUtil> listDistrito(String departamento, String provincia) throws Exception;
	
}
