package pe.com.boox.cargo.service.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import pe.com.boox.cargo.core.BooxException;
import pe.com.boox.cargo.dao.repository.EmpresaRepository;
import pe.com.boox.cargo.dao.repository.SessionTransportistaRepository;
import pe.com.boox.cargo.dao.repository.UsuarioTransporteRepository;
import pe.com.boox.cargo.model.Empresa;
import pe.com.boox.cargo.model.HeaderRq;
import pe.com.boox.cargo.model.Mail;
import pe.com.boox.cargo.model.SessionTransportista;
import pe.com.boox.cargo.model.UsuarioTransporte;
import pe.com.boox.cargo.model.VehiculoTransporte;
import pe.com.boox.cargo.service.EmailService;
import pe.com.boox.cargo.service.UsuarioTransporteService;

@Service
public class UsuarioTransporteServiceImpl implements UsuarioTransporteService {

	private final Logger logger = LoggerFactory
			.getLogger(UsuarioTransporteServiceImpl.class);

	@Autowired
	UsuarioTransporteRepository usuarioTransporteRepository;

	@Autowired
	EmpresaRepository empresaRepository;

	@Autowired
	SessionTransportistaRepository sessionTransportistaRepository;

	@Autowired 
	private EmailService emailService;	

	@Autowired
	private Environment env;

	@Override
	public void insertUsuarioTransporte(UsuarioTransporte request) throws Exception {
		
		if(request.getEmpresa()!=null) {
		
			if(request.getEmpresa().getRuc()!=null) {
			logger.info("insertUsuarioEmpresa:::"+request.getEmpresa().toString());
			Empresa bean = empresaRepository.getEmpresa(request.getEmpresa());

			if(bean==null)
				empresaRepository.insertEmpresa(request.getEmpresa());
			}
		}
		
		UsuarioTransporte ue = usuarioTransporteRepository.getUsuarioTransporte(request);
		if(ue!=null) {
			throw new BooxException("5001","Ya existe un usuario registrado con el Email ingresado");
		} else {
		
		usuarioTransporteRepository.insertUsuarioTransporte(request);

		Mail mail = new Mail();
		mail.setTo(request.getEmail());
		mail.setSubject(request.getNombre()+", Bienvenido a BooxCargo");		
		//mail.setCc("samuel3283@gmail.com");
		
		Map<String, Object> contents = new HashMap<>();
		contents.put("nombreOperacion", "Registro de Usuario");
		contents.put("nombreCliente", request.getNombre());
		
		mail.setContents(contents);
		try {
		logger.info("sendMmail");		
		emailService.sendMailTransporte(mail);
		} catch(Exception e) {
			logger.info("Error sendMail:::"+e.getMessage());
		}

		}
		
	}

	@Override
	public UsuarioTransporte getUsuarioTransporte(UsuarioTransporte request) throws Exception {
		
		return usuarioTransporteRepository.getUsuarioTransporte(request);
	}

	@Override
	public UsuarioTransporte getLoginUsuarioTransporte(UsuarioTransporte request) throws Exception {
		
		return usuarioTransporteRepository.getLoginUsuarioTransporte(request);
	}

	@Override
	public void uploadFile(String tipo, HeaderRq headerRq, MultipartFile uploadfile) throws Exception {
		logger.info("Service.uploadFile::"+headerRq.getToken());
		SessionTransportista session = new SessionTransportista();
		session.setToken(headerRq.getToken());
		SessionTransportista sessionTransportista = sessionTransportistaRepository.getSessionByToken(session);
		
		if(sessionTransportista==null)
			throw new Exception("Error Login not found");
		
		UsuarioTransporte obj = new UsuarioTransporte();
		obj.setEmail(sessionTransportista.getEmail());
		UsuarioTransporte usuarioTransporte = usuarioTransporteRepository.getUsuarioTransporte(obj);

		if(usuarioTransporte==null)
			throw new Exception("Error User not found");
				
		  long tamanio = uploadfile.getSize();
		  long tamanioMAX = 307200;  //300KB =  300x1024
		  
		  logger.info("==>tamanio:"+tamanio+"==>tamanioMAX:"+tamanioMAX);
		  if(tamanio>tamanioMAX) {
				throw new Exception("Error supera el limite de tamaño del archivo 300 KB.");			  
		  }
		  
	      // Get the filename and build the local file path
	      String filename = uploadfile.getOriginalFilename();
	      logger.info("==>filename:"+filename);
	      String ext = filename.substring(filename.lastIndexOf("."), filename.length());
	      logger.info("==>ext:"+ext);
	      
	      if(tipo.equals("FOTO"))
	    	  filename = String.valueOf(usuarioTransporte.getCodigo())+ext;
	   
	      logger.info("==>new filename:"+filename);
	      String directory = env.getProperty("path.transportista.foto");
	      
	      String newDirectory = directory +File.separator+String.valueOf(usuarioTransporte.getCodigo());
	      File mkdir = new File(newDirectory);
	      
	      String foto_bd = String.valueOf(usuarioTransporte.getCodigo()) + File.separator + filename;
	      logger.info("==>mkdir::"+mkdir.exists());

	      if(!mkdir.exists())
	    	  mkdir.mkdirs();
	      
	      String filepath = Paths.get(newDirectory, filename).toString();
	      
	      // Save the file locally
	      BufferedOutputStream stream =
	          new BufferedOutputStream(new FileOutputStream(new File(filepath)));
	      stream.write(uploadfile.getBytes());
	      stream.close();

      
	      logger.info("==>updateFotoUsuarioTransporte");
	      usuarioTransporte.setFoto(foto_bd);
	      usuarioTransporteRepository.updateFotoUsuarioTransporte(usuarioTransporte);
	}

}
