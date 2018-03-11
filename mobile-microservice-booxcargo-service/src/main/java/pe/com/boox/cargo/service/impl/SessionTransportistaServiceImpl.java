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
import pe.com.boox.cargo.model.SessionTransportista;
import pe.com.boox.cargo.model.UsuarioEmpresa;
import pe.com.boox.cargo.model.UsuarioTransporte;
import pe.com.boox.cargo.model.VehiculoTransporte;
import pe.com.boox.cargo.service.EmailService;
import pe.com.boox.cargo.service.SessionService;
import pe.com.boox.cargo.service.SessionTransportistaService;
import pe.com.boox.cargo.service.core.StringEncrypt;
import pe.com.boox.cargo.service.core.Util;
import scala.annotation.meta.setter;


@Service
public class SessionTransportistaServiceImpl implements SessionTransportistaService {

	@Autowired
	UsuarioTransporteRepository usuarioTransporteRepository;
	@Autowired
	VehiculoTransporteRepository vehiculoTransporteRepository;
	@Autowired
	ChoferRepository choferRepository;
	@Autowired
	SessionTransportistaRepository sessionTransportistaRepository;
	@Autowired
	GuiaRepository guiaRepository;
	@Autowired
	private Environment env;
	@Autowired 
	private EmailService emailService;

	private final Logger logger = LoggerFactory
			.getLogger(SessionTransportistaServiceImpl.class);

	@Override
	public SessionTransportista validaSession(UsuarioTransporte request,HeaderRq headerRq) throws Exception {
				
		UsuarioTransporte bean = usuarioTransporteRepository.getLoginUsuarioTransporte(request);
		
		if(bean==null)
			throw new BooxException("5001","Error Usuario y/o Contraseña inválidos.");
		
		logger.info("UsuarioTransporte====>"+bean.toString());
		
		SessionTransportista session = new SessionTransportista();
		session.setDevice(headerRq.getDevice());
		session.setDeviceType(headerRq.getDeviceType());
		
		session.setCodigo(bean.getCodigo());
		session.setApellido(bean.getApellido());
		session.setEmail(bean.getEmail());
		session.setNombre(bean.getNombre());
		session.setNumDocumento(bean.getNumDocumento());
		session.setTipoDocumento(bean.getTipoDocumento());
		session.setRuc(bean.getRuc());
		session.setTelefono(bean.getTelefono());
		session.setFoto(bean.getFoto());
		java.util.Date dt = new java.util.Date();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyyMMddHHmmss");
        String fechaHora = sdf.format(dt);
        
		String cadena = bean.getEmail().concat("-").concat(bean.getNumDocumento()).concat("-").concat(fechaHora);
		String token = StringEncrypt.encrypt(cadena);
		
		session.setToken(token);
		sessionTransportistaRepository.insertSession(session);

		SessionTransportista cuenta = sessionTransportistaRepository.getSessionByToken(session);
		if(cuenta==null)
			throw new Exception("Error Cuenta not found");
		
		VehiculoTransporte vehiculoTransporte = new VehiculoTransporte();
		vehiculoTransporte.setUsuarioTransporte(bean);
		//cuenta.setListaVehiculo(vehiculoTransporteRepository.listVehiculoTransporte(vehiculoTransporte));
		
		Chofer chofer = new Chofer();
		chofer.setCodigoUsuarioTransporte(bean.getCodigo());
		//cuenta.setListaChofer(choferRepository.listChofer(chofer));
		
		String fechaMes = Util.getDateToFormat(cuenta.getFechaRegistro(),Util.PATTERN_DD_MM_YY_HH_MI_SS,Util.PATTERN_TIME_MM);
		String fechaAnio = Util.getDateToFormat(cuenta.getFechaRegistro(),Util.PATTERN_DD_MM_YY_HH_MI_SS,Util.PATTERN_TIME_YYYY);
		String mes = Util.getDescripcionMes(fechaMes);

		String miembro = mes+" "+fechaAnio;
		
		Map<String, Object> mapa = new HashMap<String, Object>();
		mapa.put("miembro", miembro);
		
		/*
		Guia guia = new Guia();
		guia.setRuc(cuenta.getRuc());
		guia.setEstado(1);
		int guiaRegistradas = guiaRepository.getCantidadGuia(guia);
		guia.setEstado(0);
		int guiaAtendidas = guiaRepository.getCantidadGuia(guia);
		
		mapa.put("guiaRegistradas", guiaRegistradas);
		mapa.put("guiaAtendidas", guiaAtendidas);
		*/
		
		cuenta.setMapa(mapa);
		cuenta.setExtension(Util.getExtensionProfile(bean.getFoto()));
		//cuenta.setImagen(Util.getImageProfileEncode64(bean.getFoto(),env));
		
		logger.info("geSession:"+cuenta.toString());

		return cuenta;
	}


	@Override
	public SessionTransportista loginDirect(UsuarioTransporte request,HeaderRq headerRq) throws Exception {
				
		UsuarioTransporte bean = usuarioTransporteRepository.getUsuarioTransporte(request);
		
		if(bean==null)
			throw new Exception("Error Login not found");

		SessionTransportista session = new SessionTransportista();
		
		session.setDevice(headerRq.getDevice());
		session.setDeviceType(headerRq.getDeviceType());

		session.setCodigo(bean.getCodigo());
		session.setApellido(bean.getApellido());
		session.setEmail(bean.getEmail());
		session.setNombre(bean.getNombre());
		session.setNumDocumento(bean.getNumDocumento());
		session.setTipoDocumento(bean.getTipoDocumento());
		session.setRuc(bean.getRuc());
		session.setTelefono(bean.getTelefono());
		java.util.Date dt = new java.util.Date();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyyMMddHHmmss");
        String fechaHora = sdf.format(dt);
        
		String cadena = bean.getEmail().concat("-").concat(bean.getNumDocumento()).concat("-").concat(fechaHora);
		String token = StringEncrypt.encrypt(cadena);
		
		session.setToken(token);
		sessionTransportistaRepository.insertSession(session);
		
		SessionTransportista cuenta = sessionTransportistaRepository.getSessionByToken(session);
		if(cuenta==null)
			throw new Exception("Error Cuenta not found");
		
		
		VehiculoTransporte vehiculoTransporte = new VehiculoTransporte();
		vehiculoTransporte.setUsuarioTransporte(bean);
		cuenta.setListaVehiculo(vehiculoTransporteRepository.listVehiculoTransporte(vehiculoTransporte));

		String fechaMes = Util.getDateToFormat(cuenta.getFechaRegistro(),Util.PATTERN_DD_MM_YY_HH_MI_SS,Util.PATTERN_TIME_MM);
		String fechaAnio = Util.getDateToFormat(cuenta.getFechaRegistro(),Util.PATTERN_DD_MM_YY_HH_MI_SS,Util.PATTERN_TIME_YYYY);
		String mes = Util.getDescripcionMes(fechaMes);

		String miembro = mes+" "+fechaAnio;
		
		Map<String, Object> mapa = new HashMap<String, Object>();
		mapa.put("miembro", miembro);
		/*
		Guia guia = new Guia();
		guia.setRuc(cuenta.getRuc());
		guia.setEstado(1);
		int guiaRegistradas = guiaRepository.getCantidadGuia(guia);
		guia.setEstado(0);
		int guiaAtendidas = guiaRepository.getCantidadGuia(guia);
		
		mapa.put("guiaRegistradas", guiaRegistradas);
		mapa.put("guiaAtendidas", guiaAtendidas);
		*/
		cuenta.setMapa(mapa);

		logger.info("geSession:"+cuenta.toString());

		return cuenta;
	}

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
	public void deleteSession(String token) throws Exception {		
		
		SessionTransportista session = new SessionTransportista();
		session.setToken(token);
		logger.info("getSessionByToken.."+token);

		sessionTransportistaRepository.deleteSession(session);
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
		/*
		if(request.getOlvido()==null || request.getOlvido().isEmpty() || request.getOlvido().length()<4) {
			throw new Exception("Error Password inválido");
		}*/
		
		List<UsuarioTransporte> lista = usuarioTransporteRepository.validaCodeEmail(request);
		
		if(lista==null || lista.size() <=0)
    		throw new Exception("Error codigo enviado no válido");
		
		usuarioTransporteRepository.updatePassword(request);
	}

	
}
