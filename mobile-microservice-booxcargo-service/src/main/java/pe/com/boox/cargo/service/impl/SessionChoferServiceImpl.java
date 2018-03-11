package pe.com.boox.cargo.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import pe.com.boox.cargo.core.BooxException;
import pe.com.boox.cargo.dao.repository.ChoferRepository;
import pe.com.boox.cargo.dao.repository.GuiaRepository;
import pe.com.boox.cargo.dao.repository.SessionChoferRepository;
import pe.com.boox.cargo.dao.repository.SessionRepository;
import pe.com.boox.cargo.dao.repository.SessionTransportistaRepository;
import pe.com.boox.cargo.dao.repository.UsuarioEmpresaRepository;
import pe.com.boox.cargo.dao.repository.UsuarioTransporteRepository;
import pe.com.boox.cargo.dao.repository.VehiculoTransporteRepository;
import pe.com.boox.cargo.model.Chofer;
import pe.com.boox.cargo.model.Guia;
import pe.com.boox.cargo.model.HeaderRq;
import pe.com.boox.cargo.model.Mail;
import pe.com.boox.cargo.model.Session;
import pe.com.boox.cargo.model.SessionChofer;
import pe.com.boox.cargo.model.SessionTransportista;
import pe.com.boox.cargo.model.UsuarioEmpresa;
import pe.com.boox.cargo.model.UsuarioTransporte;
import pe.com.boox.cargo.model.VehiculoTransporte;
import pe.com.boox.cargo.service.EmailService;
import pe.com.boox.cargo.service.SessionChoferService;
import pe.com.boox.cargo.service.SessionService;
import pe.com.boox.cargo.service.SessionTransportistaService;
import pe.com.boox.cargo.service.core.StringEncrypt;
import pe.com.boox.cargo.service.core.Util;
import scala.annotation.meta.setter;


@Service
public class SessionChoferServiceImpl implements SessionChoferService {
	
	@Autowired
	UsuarioTransporteRepository usuarioTransporteRepository;
	@Autowired
	VehiculoTransporteRepository vehiculoTransporteRepository;
	@Autowired
	ChoferRepository choferRepository;
	@Autowired
	SessionChoferRepository sessionChoferRepository;
	@Autowired
	GuiaRepository guiaRepository;
	@Autowired
	private Environment env;
	@Autowired 
	private EmailService emailService;

	private final Logger logger = LoggerFactory
			.getLogger(SessionChoferServiceImpl.class);

	@Override
	public SessionChofer validaSession(Chofer request, HeaderRq headerRq) throws Exception {
		
		
		int esNuevo = choferRepository.getChoferNew(request);
		Boolean indNuevo = false;
		Chofer bean = null;
		
		if(esNuevo>0 && request.getNumDocumento().equals(request.getPassword())) {
			indNuevo = true;
			bean = choferRepository.getChoferByDocumento(request);
		} else {
			bean = choferRepository.getLoginChofer(request);			
		}
		
		
		
		if(bean==null)
			throw new BooxException("5001","Error Usuario y/o Contraseña inválidos.");
		
		logger.info("Chofer====>"+bean.toString());
		
		VehiculoTransporte vehiculo = vehiculoTransporteRepository.getVehiculoByChofer(bean);
		vehiculo.setChofer(null);
		vehiculo.setUsuarioTransporte(null);
		vehiculo.setTipoCarga(null);
		vehiculo.setListaTipoCarga(null);
		vehiculo.setExttpropiedad(Util.getExtension(vehiculo.getTpropiedad()));
		vehiculo.setExtsoat(Util.getExtension(vehiculo.getSoat()));
		vehiculo.setTpropiedad(Util.getImageEncode64(vehiculo.getTpropiedad(),env));
		vehiculo.setSoat(Util.getImageEncode64(vehiculo.getSoat(),env));
		vehiculo.setExtfoto1(Util.getExtension(vehiculo.getFoto1()));
		vehiculo.setFoto1(Util.getImageEncode64(vehiculo.getFoto1(),env));
		
		
		SessionChofer session = new SessionChofer();
		session.setDevice(headerRq.getDevice());
		session.setDeviceType(headerRq.getDeviceType());
		
		session.setCodigo(bean.getCodigo());
		session.setApellido(bean.getApellido());
		session.setEmail(bean.getEmail());
		session.setNombre(bean.getNombre());
		session.setNumDocumento(bean.getNumDocumento());
		session.setTipoDocumento(bean.getTipoDocumento());
		session.setBrevete(bean.getBrevete());
		session.setTelefono(bean.getTelefono());
		session.setFoto(bean.getFoto());
		java.util.Date dt = new java.util.Date();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyyMMddHHmmss");
        String fechaHora = sdf.format(dt);
        
		String cadena = bean.getEmail().concat("-").concat(bean.getNumDocumento()).concat("-").concat(fechaHora);
		String token = StringEncrypt.encrypt(cadena);
		
		session.setToken(token);
		sessionChoferRepository.insertSession(session);

		SessionChofer cuenta = sessionChoferRepository.getSessionByToken(session);
		if(cuenta==null)
			throw new Exception("Error Cuenta not found");
		
		cuenta.setIdConductor(bean.getCodigo());
		cuenta.setIndNuevo(indNuevo);
		cuenta.setVehiculo(vehiculo);
		logger.info("geSession:"+cuenta.toString());
		return cuenta;
	}

	
	@Override
	public SessionChofer loginDirect(Chofer request, HeaderRq headerRq) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SessionChofer getSessionByToken(String token) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteSession(String token) throws Exception {
		SessionChofer session = new SessionChofer();
		session.setToken(token);
		logger.info("getSessionByToken.."+token);

		sessionChoferRepository.deleteSession(session);
	}

	@Override
	public String generaCodeEmail(Chofer request) throws Exception {
		/*
		int numeroAleatorio = (int) (Math.random()*999998+1);		
		String sms = Util.completar(numeroAleatorio);
		request.setOlvido(sms);
		choferRepository.updateOlvido(request);
		
		
		Mail mail = new Mail();
		mail.setTo(request.getEmail());
		mail.setSubject("Recuperar Contraseña");		
		
		Map<String, Object> contents = new HashMap<>();
		contents.put("nombreOperacion", "Olvido de Clave");
		contents.put("olvido", sms);
		
		mail.setContents(contents);
		try {
		logger.info("sendMmail");		
		emailService.sendMailTransporteOlvido(mail);
		} catch(Exception e) {
			logger.info("Error sendMail:::"+e.getMessage());
		}
		
		
		return sms;
		*/
		return null;
	}

	@Override
	public void validaCodeEmail(Chofer request) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void upddateClaveChofer(Chofer request) throws Exception {

		choferRepository.updatePassword(request);
		
	}

	
	
	/*

	@Override
	public SessionTransportista getSessionByToken(String token) throws Exception {
		SessionTransportista session = new SessionTransportista();
		session.setToken(token);
		logger.info("getSessionByToken.."+token);
		SessionTransportista bean = sessionTransportistaRepository.getSessionByToken(session);
		if(bean==null)
			throw new Exception("Error Session not found");
		
		return bean;
	}

	@Override
	public String generaCodeEmail(UsuarioTransporte request) throws Exception {

		int numeroAleatorio = (int) (Math.random()*999998+1);		
		String sms = Util.completar(numeroAleatorio);
		request.setOlvido(sms);
		usuarioTransporteRepository.updateOlvido(request);
		
		
		Mail mail = new Mail();
		mail.setTo(request.getEmail());
		mail.setSubject("Recuperar Contraseña");		
		
		Map<String, Object> contents = new HashMap<>();
		contents.put("nombreOperacion", "Olvido de Clave");
		contents.put("olvido", sms);
		
		mail.setContents(contents);
		try {
		logger.info("sendMmail");		
		emailService.sendMailTransporteOlvido(mail);
		} catch(Exception e) {
			logger.info("Error sendMail:::"+e.getMessage());
		}
		
		
		return sms;
	}


	@Override
	public void validaCodeEmail(UsuarioTransporte request) throws Exception {
		
		if(request.getEmail()==null){
			throw new Exception("Error not found");
		}
		
		List<UsuarioTransporte> lista = usuarioTransporteRepository.validaCodeEmail(request);

		if(lista==null || lista.size() <=0)
    		throw new Exception("Error not found");
		
	}


	@Override
	public void upddateClaveConductor(UsuarioTransporte request) throws Exception {

		if(request.getEmail()==null) {
			throw new Exception("Error Email inválido");
		}

		if(request.getPassword()==null || request.getPassword().isEmpty() || request.getPassword().length()<4) {
			throw new Exception("Error Password inválido");
		}
	
		List<UsuarioTransporte> lista = usuarioTransporteRepository.validaCodeEmail(request);
		
		if(lista==null || lista.size() <=0)
    		throw new Exception("Error codigo enviado no válido");
		
		usuarioTransporteRepository.updatePassword(request);
	}
*/
	
}
