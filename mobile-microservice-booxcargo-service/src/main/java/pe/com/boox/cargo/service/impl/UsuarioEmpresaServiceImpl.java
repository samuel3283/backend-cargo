package pe.com.boox.cargo.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import pe.com.boox.cargo.core.BooxException;
import pe.com.boox.cargo.dao.repository.EmpresaRepository;
import pe.com.boox.cargo.dao.repository.UsuarioEmpresaRepository;
import pe.com.boox.cargo.model.Empresa;
import pe.com.boox.cargo.model.Mail;
import pe.com.boox.cargo.model.UsuarioEmpresa;
import pe.com.boox.cargo.service.EmailService;
import pe.com.boox.cargo.service.UsuarioEmpresaService;

@Service
public class UsuarioEmpresaServiceImpl implements UsuarioEmpresaService {

	private final Logger logger = LoggerFactory
			.getLogger(UsuarioEmpresaServiceImpl.class);

	@Autowired
	UsuarioEmpresaRepository usuarioEmpresaRepository;

	@Autowired
	EmpresaRepository empresaRepository;

	@Autowired 
	private EmailService emailService;
	
    
	@Override
	public void insertUsuarioEmpresa(UsuarioEmpresa usuarioEmpresa) throws Exception {
		
		logger.info("insertUsuarioEmpresa:::"+usuarioEmpresa.getEmpresa().toString());
		Empresa bean = empresaRepository.getEmpresa(usuarioEmpresa.getEmpresa());

		if(bean==null)
			empresaRepository.insertEmpresa(usuarioEmpresa.getEmpresa());
		
		
		UsuarioEmpresa ue = usuarioEmpresaRepository.getUsuarioEmpresa(usuarioEmpresa);
		if(ue!=null) {
			throw new BooxException("5001","Ya existe un usuario registrado con el Email ingresado");
		} else {
		
		usuarioEmpresaRepository.insertUsuarioEmpresa(usuarioEmpresa);

		Mail mail = new Mail();
		mail.setTo(usuarioEmpresa.getEmail());
		mail.setSubject(usuarioEmpresa.getNombre()+", Bienvenido a BooxCargo");		
		//mail.setCc("samuel3283@gmail.com");
		
		Map<String, Object> contents = new HashMap<>();
		contents.put("nombreOperacion", "Registro de Empresa");
		contents.put("nombreCliente", usuarioEmpresa.getNombre());
		
		mail.setContents(contents);
		try {
		logger.info("sendMmail");		
		emailService.sendMail(mail);
		} catch(Exception e) {
			logger.info("Error sendMail:::"+e.getMessage());
		}

		}
		
	}

	@Override
	public UsuarioEmpresa getUsuarioEmpresa(UsuarioEmpresa usuarioEmpresa) throws Exception {
		
		return usuarioEmpresaRepository.getUsuarioEmpresa(usuarioEmpresa);
	}

	@Override
	public UsuarioEmpresa getLoginUsuarioEmpresa(UsuarioEmpresa usuarioEmpresa) throws Exception {
		
		return usuarioEmpresaRepository.getLoginUsuarioEmpresa(usuarioEmpresa);
	}

	
}
