package pe.com.boox.cargo.service.impl;


import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.com.boox.cargo.dao.repository.UtilRepository;
import pe.com.boox.cargo.model.BeanUtil;
import pe.com.boox.cargo.service.GuiaService;
import pe.com.boox.cargo.service.UtilService;


@Service
public class UtilServiceImpl implements UtilService {

	private final Logger logger = LoggerFactory.getLogger(UtilService.class);

	@Autowired
	UtilRepository utilRepository;

	
	@Override
	public List<BeanUtil> lstDepartamento() throws Exception {
		
		return utilRepository.listDepartamento();
		
	}


	@Override
	public List<BeanUtil> lstProvincia(String departamento) throws Exception {
		
		return utilRepository.listProvincia(departamento);
	}


	@Override
	public List<BeanUtil> lstDistrito(String departamento, String provincia) throws Exception {

		return utilRepository.listDistrito(departamento, provincia);
	}
	
}
