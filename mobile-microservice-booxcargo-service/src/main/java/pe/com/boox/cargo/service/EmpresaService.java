package pe.com.boox.cargo.service;

import pe.com.boox.cargo.model.Empresa;

public interface EmpresaService {

	void insertEmpresa(Empresa empresa) throws Exception;
	Empresa getEmpresa(Empresa empresa) throws Exception;
	
}
