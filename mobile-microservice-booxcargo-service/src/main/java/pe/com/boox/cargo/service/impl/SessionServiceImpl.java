package pe.com.boox.cargo.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.com.boox.cargo.core.BooxException;
import pe.com.boox.cargo.dao.repository.GuiaRepository;
import pe.com.boox.cargo.dao.repository.SessionRepository;
import pe.com.boox.cargo.dao.repository.UsuarioEmpresaRepository;
import pe.com.boox.cargo.model.BeanUtil;
import pe.com.boox.cargo.model.Guia;
import pe.com.boox.cargo.model.HeaderRq;
import pe.com.boox.cargo.model.Session;
import pe.com.boox.cargo.model.UsuarioEmpresa;
import pe.com.boox.cargo.service.SessionService;
import pe.com.boox.cargo.service.core.StringEncrypt;
import pe.com.boox.cargo.service.core.Util;
import scala.annotation.meta.setter;


@Service
public class SessionServiceImpl implements SessionService {

	@Autowired
	UsuarioEmpresaRepository usuarioEmpresaRepository;
	@Autowired
	SessionRepository sessionRepository;
	@Autowired
	GuiaRepository guiaRepository;

	private final Logger logger = LoggerFactory
			.getLogger(SessionServiceImpl.class);

	@Override
	public Session validaSession(UsuarioEmpresa usuarioEmpresa,HeaderRq headerRq) throws Exception {
				
		UsuarioEmpresa bean = usuarioEmpresaRepository.getLoginUsuarioEmpresa(usuarioEmpresa);
		
		if(bean==null)
			throw new BooxException("5001","Error Usuario y/o Contraseña inválidos.");
		
		logger.info("UsuarioEmpresa:::"+bean.toString());
		
		Session session = new Session();
		
		session.setDevice(headerRq.getDevice());
		session.setDeviceType(headerRq.getDeviceType());

		session.setCodigo(bean.getCodigo());
		session.setApellido(bean.getApellido());
		session.setEmail(bean.getEmail());
		session.setNombre(bean.getNombre());
		session.setNumDocumento(bean.getNumDocumento());
		session.setTipoDocumento(bean.getTipoDocumento());
		session.setRuc(bean.getRuc());
		session.setEmpresa(bean.getEmpresa());
		session.setTelefono(bean.getTelefono());
		java.util.Date dt = new java.util.Date();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyyMMddHHmmss");
        String fechaHora = sdf.format(dt);
        
		String cadena = bean.getEmail().concat("-").concat(bean.getNumDocumento()).concat("-").concat(fechaHora);
		String token = StringEncrypt.encrypt(cadena);
		
		session.setToken(token);
		sessionRepository.insertSession(session);

		Session cuenta = sessionRepository.getSessionByToken(session);
		if(cuenta==null)
			throw new Exception("Error Cuenta not found");
		
		cuenta.setEmpresa(bean.getEmpresa());
		
		
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
		guia.setEstado(3);
		int guiaPostuladas = guiaRepository.getCantidadGuia(guia);
		guia.setEstado(5);
		int guiaEnRuta = guiaRepository.getCantidadGuia(guia);
		guia.setEstado(7);
		int guiaTerminadas = guiaRepository.getCantidadGuia(guia);

		mapa.put("guiaRegistradas", guiaRegistradas);
		mapa.put("guiaPostuladas", guiaPostuladas);
		mapa.put("guiaEnRuta", guiaEnRuta);
		mapa.put("guiaTerminadas", guiaTerminadas);
		*/
		
		cuenta.setMapa(mapa);
		logger.info("geSession:"+cuenta.toString());
		 
		return cuenta;
	}


	@Override
	public Map<String, Object> getEstadistica(HeaderRq headerRq) throws Exception {
				
		Session session = getSessionByToken(headerRq.getToken());
				
		int guiaRegistradas = 0;
		int guiaPostuladas = 0;
		int guiaEnRuta = 0;
		int guiaTerminadas = 0;		
		Map<String, Object> mapa = new HashMap<String, Object>();		
		Guia guia = new Guia();
		guia.setRuc(session.getRuc());
		List<BeanUtil> lista = guiaRepository.lstCantidadGuia(guia); 
		
		if(lista.size()>0) {	
			for(BeanUtil bean: lista) {
				if(bean.getDescripcion().equals("1"))
					guiaRegistradas = Integer.valueOf(bean.getCodigo());
				if(bean.getDescripcion().equals("3"))
					guiaPostuladas = Integer.valueOf(bean.getCodigo());
				if(bean.getDescripcion().equals("5"))
					guiaEnRuta = Integer.valueOf(bean.getCodigo());
				if(bean.getDescripcion().equals("7"))
					guiaTerminadas = Integer.valueOf(bean.getCodigo());
			}			
		}
		mapa.put("guiaRegistradas", guiaRegistradas);
		mapa.put("guiaPostuladas", guiaPostuladas);
		mapa.put("guiaEnRuta", guiaEnRuta);
		mapa.put("guiaTerminadas", guiaTerminadas);

		logger.info("mapa:"+mapa);
		 
		return mapa;
	}


	@Override
	public Session loginDirect(UsuarioEmpresa usuarioEmpresa,HeaderRq headerRq) throws Exception {
				
		UsuarioEmpresa bean = usuarioEmpresaRepository.getUsuarioEmpresa(usuarioEmpresa);
		
		if(bean==null)
			throw new Exception("Error Login not found");

		Session session = new Session();
		
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
		sessionRepository.insertSession(session);
		
		Session cuenta = sessionRepository.getSessionByToken(session);
		if(cuenta==null)
			throw new Exception("Error Cuenta not found");
		
		String fechaMes = Util.getDateToFormat(cuenta.getFechaRegistro(),Util.PATTERN_DD_MM_YY_HH_MI_SS,Util.PATTERN_TIME_MM);
		String fechaAnio = Util.getDateToFormat(cuenta.getFechaRegistro(),Util.PATTERN_DD_MM_YY_HH_MI_SS,Util.PATTERN_TIME_YYYY);
		String mes = Util.getDescripcionMes(fechaMes);

		String miembro = mes+" "+fechaAnio;
		
		Map<String, Object> mapa = new HashMap<String, Object>();
		mapa.put("miembro", miembro);
		
		Guia guia = new Guia();
		guia.setRuc(cuenta.getRuc());
		guia.setEstado(1);
		int guiaRegistradas = guiaRepository.getCantidadGuia(guia);
		guia.setEstado(3);
		int guiaPostuladas = guiaRepository.getCantidadGuia(guia);
		guia.setEstado(5);
		int guiaEnRuta = guiaRepository.getCantidadGuia(guia);
		guia.setEstado(7);
		int guiaTerminadas = guiaRepository.getCantidadGuia(guia);

		mapa.put("guiaRegistradas", guiaRegistradas);
		mapa.put("guiaPostuladas", guiaPostuladas);
		mapa.put("guiaEnRuta", guiaEnRuta);
		mapa.put("guiaTerminadas", guiaTerminadas);
		cuenta.setMapa(mapa);
		cuenta.setEmpresa(bean.getEmpresa());

		logger.info("geSession:"+cuenta.toString());

		return cuenta;
	}

	@Override
	public Session getSessionByToken(String token) throws Exception {
		Session session = new Session();
		session.setToken(token);
		Session bean = sessionRepository.getSessionByToken(session);
		if(bean==null)
			throw new Exception("Error Session not found");
		
		return bean;
	}

	@Override
	public void deleteSession(String token) throws Exception {		
		
		Session session = new Session();
		session.setToken(token);
		logger.info("getSessionByToken.."+token);

		sessionRepository.deleteSession(session);
	}

	
}
