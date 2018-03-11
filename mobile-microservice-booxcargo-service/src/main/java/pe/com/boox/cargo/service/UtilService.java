package pe.com.boox.cargo.service;

import java.util.List;
import pe.com.boox.cargo.model.BeanUtil;

public interface UtilService {

	List<BeanUtil> lstDepartamento() throws Exception;
	List<BeanUtil> lstProvincia(String departamento) throws Exception;
	List<BeanUtil> lstDistrito(String departamento, String provincia) throws Exception;

}
