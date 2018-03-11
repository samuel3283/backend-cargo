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

import pe.com.boox.cargo.dao.repository.SessionTransportistaRepository;
import pe.com.boox.cargo.dao.repository.UsuarioTransporteRepository;
import pe.com.boox.cargo.dao.repository.UtilRepository;
import pe.com.boox.cargo.dao.repository.VehiculoTransporteRepository;
import pe.com.boox.cargo.model.BeanUtil;
import pe.com.boox.cargo.model.Guia;
import pe.com.boox.cargo.model.HeaderRq;
import pe.com.boox.cargo.model.SessionTransportista;
import pe.com.boox.cargo.model.UsuarioTransporte;
import pe.com.boox.cargo.model.VehiculoTransporte;
import pe.com.boox.cargo.service.VehiculoTransporteService;
import pe.com.boox.cargo.service.core.Util;

@Service
public class VehiculoTransporteServiceImpl implements VehiculoTransporteService {

	private final Logger logger = LoggerFactory
			.getLogger(VehiculoTransporteServiceImpl.class);

	@Autowired
	VehiculoTransporteRepository vehiculoTransporteRepository;

	@Autowired
	UsuarioTransporteRepository usuarioTransporteRepository;
	@Autowired
	SessionTransportistaRepository sessionTransportistaRepository;
	@Autowired	    
	UtilRepository utilRepository;
	@Autowired
	private Environment env;
	
	@Override
	public Integer insertVehiculoTransporte(VehiculoTransporte request, String token) throws Exception {		
		
		SessionTransportista session = new SessionTransportista();
		session.setToken(token);
		SessionTransportista sessionTransportista = sessionTransportistaRepository.getSessionByToken(session);
		
		if(sessionTransportista==null)
			throw new Exception("Error Login not found");
		
		UsuarioTransporte obj = new UsuarioTransporte();
		obj.setEmail(sessionTransportista.getEmail());
		UsuarioTransporte usuarioTransporte = usuarioTransporteRepository.getUsuarioTransporte(obj);

		if(usuarioTransporte==null)
			throw new Exception("Error User not found");

		request.setUsuarioTransporte(usuarioTransporte);
		vehiculoTransporteRepository.insertVehiculoTransporte(request);
		Integer idVehiculo = vehiculoTransporteRepository.getMaxIdVehiculoTransporte(request);
		
		
		for(BeanUtil bean: request.getTipoCarga()) {
			if(bean.isCheck()) {
				logger.info("getTipoCarga:::"+bean.toString());
				utilRepository.insertTipoCarga(bean, idVehiculo);
			}	
		}
		
		return idVehiculo;
	}
	
	@Override
	public Integer updateVehiculoTransporte(VehiculoTransporte request, String token) throws Exception {		
		
		SessionTransportista session = new SessionTransportista();
		session.setToken(token);
		SessionTransportista sessionTransportista = sessionTransportistaRepository.getSessionByToken(session);
		
		if(sessionTransportista==null)
			throw new Exception("Error Login not found");
		
		vehiculoTransporteRepository.updateVehiculoTransporte(request);
		Integer idVehiculo = request.getCodigo();
		
		utilRepository.deleteTipoCarga(idVehiculo);
		
		for(BeanUtil bean: request.getTipoCarga()) {
			logger.info("getTipoCarga::"+bean.toString());
			if(bean.isCheck()) {
				logger.info("is check getTipoCarga:::"+bean.toString());
				utilRepository.insertTipoCarga(bean, idVehiculo);
			}	
		}
		
		return idVehiculo;
	}

	public VehiculoTransporte getVehiculoTransporte(VehiculoTransporte request) throws Exception {
		
		VehiculoTransporte bean = vehiculoTransporteRepository.getVehiculoTransporte(request);
		bean.setExttpropiedad(Util.getExtension(bean.getTpropiedad()));
		bean.setExtsoat(Util.getExtension(bean.getSoat()));
		bean.setTpropiedad(Util.getImageEncode64(bean.getTpropiedad(),env));
		bean.setSoat(Util.getImageEncode64(bean.getSoat(),env));
		bean.setExtfoto1(Util.getExtension(bean.getFoto1()));
		bean.setFoto1(Util.getImageEncode64(bean.getFoto1(),env));
		bean.setTipoDes(Util.getTipoCamion(bean.getTipo()));
		bean.setTipoCarga(utilRepository.listTipoCarga(bean.getCodigo()));
		
		bean.setListaTipoCarga(Util.setearListMultiple(bean.getTipoCarga()));
		
		return bean;
	}

	@Override
	public List<VehiculoTransporte> listVehiculoTransporte(VehiculoTransporte request, String token) throws Exception {

		SessionTransportista session = new SessionTransportista();
		session.setToken(token);
		SessionTransportista sessionTransportista = sessionTransportistaRepository.getSessionByToken(session);
		
		if(sessionTransportista==null)
			throw new Exception("Error Login not found");
		
		UsuarioTransporte obj = new UsuarioTransporte();
		obj.setEmail(sessionTransportista.getEmail());
		UsuarioTransporte usuarioTransporte = usuarioTransporteRepository.getUsuarioTransporte(obj);

		if(usuarioTransporte==null)
			throw new Exception("Error User not found");
		
		
		VehiculoTransporte vehiculoTransporte = new VehiculoTransporte();
		vehiculoTransporte.setUsuarioTransporte(new UsuarioTransporte());
		vehiculoTransporte.getUsuarioTransporte().setCodigo(usuarioTransporte.getCodigo());
		
		List<VehiculoTransporte> lista = vehiculoTransporteRepository.listVehiculoTransporte(vehiculoTransporte);
		List <VehiculoTransporte> listaFinal = null;
		
		if(lista!=null && lista.size()>0) {
    		listaFinal = new ArrayList<VehiculoTransporte>();
    		for(VehiculoTransporte bean: lista) {
    			//bean.setExttpropiedad(Util.getExtension(bean.getTpropiedad()));
    			//bean.setExtsoat(Util.getExtension(bean.getSoat()));
    			//bean.setTpropiedad(Util.getImageEncode64(bean.getTpropiedad(),env));
    			//bean.setSoat(Util.getImageEncode64(bean.getSoat(),env));
    			bean.setExtfoto1(Util.getExtension(bean.getFoto1()));
    			bean.setFoto1(Util.getImageEncode64(bean.getFoto1(),env));
    			bean.setTipoDes(Util.getTipoCamion(bean.getTipo()));
    			bean.setTipoCarga(utilRepository.listTipoCarga(bean.getCodigo()));
    			listaFinal.add(bean);
    		}
    	}
		
		return listaFinal;
	}

	@Override
	public List<VehiculoTransporte> listShortVehiculoTransporte(VehiculoTransporte request, String token) throws Exception {

		SessionTransportista session = new SessionTransportista();
		session.setToken(token);
		SessionTransportista sessionTransportista = sessionTransportistaRepository.getSessionByToken(session);
		
		if(sessionTransportista==null)
			throw new Exception("Error Login not found");
		
		UsuarioTransporte obj = new UsuarioTransporte();
		obj.setEmail(sessionTransportista.getEmail());
		UsuarioTransporte usuarioTransporte = usuarioTransporteRepository.getUsuarioTransporte(obj);

		if(usuarioTransporte==null)
			throw new Exception("Error User not found");
		
		
		VehiculoTransporte vehiculoTransporte = new VehiculoTransporte();
		vehiculoTransporte.setUsuarioTransporte(new UsuarioTransporte());
		vehiculoTransporte.getUsuarioTransporte().setCodigo(usuarioTransporte.getCodigo());
		
		List<VehiculoTransporte> lista = vehiculoTransporteRepository.listVehiculoTransporte(vehiculoTransporte);
		
		return lista;
	}

	
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
	      
	      if(tipo.equals("TARJETA"))
	    	  filename = String.valueOf(usuarioTransporte.getCodigo())+'_'+headerRq.getCodigo()+"_tarjeta"+ext;
	      if(tipo.equals("SOAT"))
	    	  filename = String.valueOf(usuarioTransporte.getCodigo())+'_'+headerRq.getCodigo()+"_soat"+ext;
	      if(tipo.equals("FOTO1"))
	    	  filename = String.valueOf(usuarioTransporte.getCodigo())+'_'+headerRq.getCodigo()+"_foto1"+ext;
	      
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

	      VehiculoTransporte bean = new VehiculoTransporte();
	      bean.setCodigo(Integer.valueOf(headerRq.getCodigo()));
	      UsuarioTransporte user = new UsuarioTransporte();
	      user.setCodigo(usuarioTransporte.getCodigo());
	      bean.setUsuarioTransporte(user);

	      vehiculoTransporteRepository.updateAdjuntoVehiculoTransporte(bean, foto_bd, tipo);
	}
	
	@Override
	public void asignaChofer(VehiculoTransporte request,String token) throws Exception {
		
		vehiculoTransporteRepository.asignaChofer(request);
	}

	
	public VehiculoTransporte getVehiculoByGuia(Guia request) throws Exception {
		
		VehiculoTransporte bean = vehiculoTransporteRepository.getVehiculoByGuia(request);
		bean.setExttpropiedad(Util.getExtension(bean.getTpropiedad()));
		bean.setExtsoat(Util.getExtension(bean.getSoat()));
		bean.setTpropiedad(Util.getImageEncode64(bean.getTpropiedad(),env));
		bean.setSoat(Util.getImageEncode64(bean.getSoat(),env));
		bean.setExtfoto1(Util.getExtension(bean.getFoto1()));
		bean.setFoto1(Util.getImageEncode64(bean.getFoto1(),env));
		bean.setTipoDes(Util.getTipoCamion(bean.getTipo()));
		
		//bean.setTipoCarga(utilRepository.listTipoCarga(bean.getCodigo()));
		//bean.setListaTipoCarga(Util.setearListMultiple(bean.getTipoCarga()));
		bean.setTipoCarga(null);
		bean.setListaTipoCarga(null);
		
		return bean;
	}

}
