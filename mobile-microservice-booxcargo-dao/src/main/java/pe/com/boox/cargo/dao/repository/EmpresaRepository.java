package pe.com.boox.cargo.dao.repository;

import pe.com.boox.cargo.model.Chofer;
import pe.com.boox.cargo.model.Empresa;

public interface EmpresaRepository {

	void insertEmpresa(Empresa empresa) throws Exception;
	Empresa getEmpresa(Empresa empresa) throws Exception;
	
}
