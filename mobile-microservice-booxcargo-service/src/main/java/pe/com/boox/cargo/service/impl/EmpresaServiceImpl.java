package pe.com.boox.cargo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.com.boox.cargo.dao.repository.EmpresaRepository;
import pe.com.boox.cargo.model.Empresa;
import pe.com.boox.cargo.service.EmpresaService;

@Service
public class EmpresaServiceImpl implements EmpresaService {

	@Autowired
	EmpresaRepository empresaRepository;

	@Override
	public void insertEmpresa(Empresa empresa) throws Exception {
		
		empresaRepository.insertEmpresa(empresa);
	}

	@Override
	public Empresa getEmpresa(Empresa empresa) throws Exception {
		
		return empresaRepository.getEmpresa(empresa);
	}

	
}
