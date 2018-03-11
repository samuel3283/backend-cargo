package pe.com.boox.cargo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.com.boox.cargo.dao.repository.GuiaDetalleRepository;
import pe.com.boox.cargo.dao.repository.GuiaRepository;
import pe.com.boox.cargo.model.Guia;
import pe.com.boox.cargo.model.GuiaDetalle;
import pe.com.boox.cargo.service.GuiaDetalleService;
import pe.com.boox.cargo.service.GuiaService;

@Service
public class GuiaDetalleServiceImpl implements GuiaDetalleService {

	@Autowired
	GuiaDetalleRepository guiaDetalleRepository;
	
	@Override
	public void insertGuiaDetalle(GuiaDetalle guia) throws Exception {
		
		guiaDetalleRepository.insertGuiaDetalle(guia);
	}

	@Override
	public List<GuiaDetalle> listGuiaDetalle(GuiaDetalle guia) throws Exception {
		
		return guiaDetalleRepository.listGuiaDetalle(guia);
	}

	
}
