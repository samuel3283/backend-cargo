package pe.com.boox.cargo.service.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import pe.com.boox.cargo.core.BooxException;
import pe.com.boox.cargo.dao.repository.ChoferRepository;
import pe.com.boox.cargo.dao.repository.EmpresaRepository;
import pe.com.boox.cargo.dao.repository.SessionTransportistaRepository;
import pe.com.boox.cargo.dao.repository.UsuarioTransporteRepository;
import pe.com.boox.cargo.dao.repository.UtilRepository;
import pe.com.boox.cargo.model.Chofer;
import pe.com.boox.cargo.model.Empresa;
import pe.com.boox.cargo.model.HeaderRq;
import pe.com.boox.cargo.model.SessionTransportista;
import pe.com.boox.cargo.model.UsuarioTransporte;
import pe.com.boox.cargo.model.VehiculoTransporte;
import pe.com.boox.cargo.service.ChoferService;
import pe.com.boox.cargo.service.core.Util;

@Service
public class ChoferServiceImpl implements ChoferService {

	private final Logger logger = LoggerFactory.getLogger(ChoferService.class);

	@Autowired
	ChoferRepository choferRepository;

	@Autowired
	SessionTransportistaRepository sessionTransportistaRepository;

	@Autowired
	UsuarioTransporteRepository usuarioTransporteRepository;

	@Autowired
	private Environment env;

	@Autowired	    
	UtilRepository utilRepository;
	
	@Override
	public int insertaChofer(Chofer request,String token) throws Exception {
		
		logger.info("==>insertChofer==>"+request.toString());

		SessionTransportista session = new SessionTransportista();
		session.setToken(token);
		session = sessionTransportistaRepository.getSessionByToken(session);
		
		if(session==null) 
			throw new BooxException("5000","Error al obtener sessión de usuario");

		UsuarioTransporte usuarioTransporte = new UsuarioTransporte();
		usuarioTransporte.setEmail(session.getEmail());
		usuarioTransporte = usuarioTransporteRepository.getUsuarioTransporte(usuarioTransporte);	
			
		if(usuarioTransporte==null) 
			throw new BooxException("5000","Error al obtener datos de usuario");
		
		logger.info("usuarioTransporte::"+usuarioTransporte.getCodigo());
		
		request.setUsuarioTransporte(usuarioTransporte);
		choferRepository.insertChofer(request);
		int idChofer = choferRepository.getMaxIdChofer(request);
		
		return idChofer;
	}

	@Override
	public void insertChofer(Chofer request,String token) throws Exception {
		
		logger.info("==>insertChofer==>"+request.toString());

		SessionTransportista session = new SessionTransportista();
		session.setToken(token);
		session = sessionTransportistaRepository.getSessionByToken(session);
		
		if(session==null) 
			throw new BooxException("5000","Error al obtener sessión de usuario");

		UsuarioTransporte usuarioTransporte = new UsuarioTransporte();
		usuarioTransporte.setEmail(session.getEmail());
		usuarioTransporte = usuarioTransporteRepository.getUsuarioTransporte(usuarioTransporte);	
			
		if(usuarioTransporte==null) 
			throw new BooxException("5000","Error al obtener datos de usuario");
		
		logger.info("usuarioTransporte::"+usuarioTransporte.getCodigo());
		
		request.setUsuarioTransporte(usuarioTransporte);
		choferRepository.insertChofer(request);
		
	}


	@Override
	public Chofer getChofer(Chofer request,String token) throws Exception {
		
		Chofer response = choferRepository.getChofer(request);
		response.setExtBrevete(Util.getExtension(response.getFotoBrevete()));
		response.setFotoBrevete(Util.getImageEncode64(response.getFotoBrevete(),env));
		
		return response;
	}

	@Override
	public List<Chofer> listChofer(Chofer request,String token) throws Exception {

		logger.info("==>listChofer==>"+request.toString());

		SessionTransportista session = new SessionTransportista();
		session.setToken(token);
		session = sessionTransportistaRepository.getSessionByToken(session);
		
		if(session==null) 
			throw new BooxException("5000","Error al obtener sessión de usuario");

		UsuarioTransporte usuarioTransporte = new UsuarioTransporte();
		usuarioTransporte.setEmail(session.getEmail());
		usuarioTransporte = usuarioTransporteRepository.getUsuarioTransporte(usuarioTransporte);	
			
		if(usuarioTransporte==null) 
			throw new BooxException("5000","Error al obtener datos de usuario");
		
		logger.info("usuarioTransporte::"+usuarioTransporte.getCodigo());
		
		request.setCodigoUsuarioTransporte(usuarioTransporte.getCodigo());
		List<Chofer> response = choferRepository.listChofer(request);
		List<Chofer> lista = new ArrayList();
		for(Chofer bean: response) {
			bean.setExtBrevete(Util.getExtension(bean.getFotoBrevete()));
	 		bean.setFotoBrevete(Util.getImageEncode64(bean.getFotoBrevete(),env));
			
			lista.add(bean);
		}
		
		return lista;
	}

	@Override
	public int updateChofer(Chofer request, String token) throws Exception {
		logger.info("==>insertChofer==>"+request.toString());

		SessionTransportista session = new SessionTransportista();
		session.setToken(token);
		session = sessionTransportistaRepository.getSessionByToken(session);
		
		if(session==null) 
			throw new BooxException("5000","Error al obtener sessión de usuario");

		UsuarioTransporte usuarioTransporte = new UsuarioTransporte();
		usuarioTransporte.setEmail(session.getEmail());
		usuarioTransporte = usuarioTransporteRepository.getUsuarioTransporte(usuarioTransporte);	
			
		if(usuarioTransporte==null) 
			throw new BooxException("5000","Error al obtener datos de usuario");
		
		logger.info("usuarioTransporte::"+usuarioTransporte.getCodigo());
		
		request.setCodigoUsuarioTransporte(usuarioTransporte.getCodigo());
		choferRepository.updateChofer(request);
		
		return request.getCodigo();
	}

	@Override
	public void uploadFile(HeaderRq headerRq, MultipartFile uploadfile) throws Exception {
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
				
			Chofer chofer = new Chofer();
			chofer.setCodigo(Integer.valueOf(headerRq.getCodigo()));

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
	      
    	  filename = String.valueOf(usuarioTransporte.getCodigo())+'_'+headerRq.getCodigo()+"_brevete"+ext;
	      
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
	
	      choferRepository.updateAdjunto(chofer, foto_bd);
	}

	
	
}
