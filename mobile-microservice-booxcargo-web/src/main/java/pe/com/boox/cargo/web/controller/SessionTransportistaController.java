package pe.com.boox.cargo.web.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import pe.com.boox.cargo.model.BeanResponse;
import pe.com.boox.cargo.model.HeaderRq;
import pe.com.boox.cargo.model.ImageResponse;
import pe.com.boox.cargo.model.Session;
import pe.com.boox.cargo.model.SessionTransportista;
import pe.com.boox.cargo.model.TransactionRs;
import pe.com.boox.cargo.model.UsuarioEmpresa;
import pe.com.boox.cargo.model.UsuarioTransporte;
import pe.com.boox.cargo.service.SessionTransportistaService;
import pe.com.boox.cargo.service.UsuarioEmpresaService;
import pe.com.boox.cargo.service.core.HeaderRqUtil;
import pe.com.boox.cargo.service.core.Util;


@RestController
@RequestMapping("/rest")
public class SessionTransportistaController {

	@Autowired
	private SessionTransportistaService sessionTransportistaService;

	@Autowired
	private HeaderRqUtil headerRqUtil;

	@Autowired
	private Environment env;

	private final Logger logger = LoggerFactory
			.getLogger(SessionTransportistaController.class);

		
		@RequestMapping(value ="/service/transporte/validaSession", 
				method = RequestMethod.POST, produces = { "application/json" })
		@ResponseBody
		public TransactionRs<SessionTransportista> validaSession(
				@RequestHeader HttpHeaders headers, @RequestBody UsuarioTransporte request) {
			
			logger.info("validaSession.");
			TransactionRs<SessionTransportista> response = new TransactionRs<SessionTransportista>();
			
			try {
				HeaderRq headerRq = headerRqUtil.getHttpHeader(headers);
				
				response.setRespuesta(sessionTransportistaService.validaSession(request,headerRq));
				
			} catch (Exception e) {
				logger.error("Error validaSession. ",e.getMessage());
				response.setCodigoError("5000");
				response.setDescripcion(e.getMessage());
			}
			
			return response;
		}


		@RequestMapping(value = "/service/transporte/getImage", method = RequestMethod.POST, produces = MediaType.IMAGE_PNG_VALUE)
		public @ResponseBody byte[] getImage(@RequestHeader HttpHeaders headers,
				@RequestBody UsuarioTransporte request) throws Exception,
				IOException {
			logger.info("Obteniendo imágenes.");

			HeaderRq headerRq = headerRqUtil.getHttpHeader(headers);
		
			InputStream in = null;
			byte[] imagen = null;

			try {
				String path = env.getProperty("path.transportista.foto");
				
			    String pathImg = Paths.get(path, request.getFoto()).toString();
			    
				logger.info("Obteniendo imágenes."+pathImg);
				//String pathImg = "C:/Apps/taxiboox/pasajero/"request.getFoto();
				if (pathImg != null && !pathImg.isEmpty()) {
					Path ruta = Paths.get(pathImg);
					in = Files.newInputStream(ruta);
					imagen = IOUtils.toByteArray(in);
				}
				
			} catch (Exception e) {
				logger.error("Error obteniendo imágenes", e.getMessage());
			} finally {
				if (in != null) {
					in.close();
				}
			}
			return imagen;
		}

		@RequestMapping(value = "/service/transporte/getImageProfile", method = RequestMethod.POST, produces = MediaType.IMAGE_PNG_VALUE)
		public @ResponseBody byte[] getImageProfile(@RequestHeader HttpHeaders headers) throws Exception,
				IOException {
			logger.info("Obteniendo imágenes.");

			HeaderRq headerRq = headerRqUtil.getHttpHeader(headers);
		
			InputStream in = null;
			byte[] imagen = null;

			try {
				String path = env.getProperty("path.transportista.foto");
				String foto = "";
				
				SessionTransportista session = new SessionTransportista();
				session.setToken(headerRq.getToken());
				SessionTransportista sessionTransportista = sessionTransportistaService.getSessionByToken(headerRq.getToken());
				
				if(sessionTransportista==null)
					throw new Exception("Error Login not found");

				logger.info("sessionTransportista.getFot..."+sessionTransportista.getFoto());
			    
				if(sessionTransportista.getFoto()!=null && sessionTransportista.getFoto()!="") {
					foto = sessionTransportista.getFoto();
				} else {
					foto = "profile.jpg";					
				}
				logger.info("==>foto:"+foto);
					
				String pathImg = Paths.get(path, foto).toString();
			    
				logger.info("Obteniendo imágenes."+pathImg);
				//String pathImg = "C:/Apps/taxiboox/pasajero/"request.getFoto();
				if (pathImg != null && !pathImg.isEmpty()) {
					Path ruta = Paths.get(pathImg);
					in = Files.newInputStream(ruta);
					imagen = IOUtils.toByteArray(in);
				}
				
			} catch (Exception e) {
				logger.error("Error obteniendo imágenes", e.getMessage());
			} finally {
				if (in != null) {
					in.close();
				}
			}
			return imagen;
		}

		
		@RequestMapping(value = "/service/transporte/getImageContent", method = RequestMethod.POST, produces = MediaType.IMAGE_PNG_VALUE)
		public @ResponseBody byte[] getImageContent(@RequestHeader HttpHeaders headers,
				@RequestBody UsuarioTransporte request) throws Exception,
				IOException {
			logger.info("Obteniendo imágenes.");

			HeaderRq headerRq = headerRqUtil.getHttpHeader(headers);
		
			InputStream in = null;
			byte[] imagen = null;

			try {
				String path = env.getProperty("path.transportista.foto");
				
			    String pathImg = Paths.get(path, request.getFoto()).toString();
			    
				logger.info("Obteniendo imágenes."+pathImg);
				//String pathImg = "C:/Apps/taxiboox/pasajero/"request.getFoto();
				if (pathImg != null && !pathImg.isEmpty()) {
					Path ruta = Paths.get(pathImg);
					in = Files.newInputStream(ruta);
					imagen = IOUtils.toByteArray(in);
				}
				
			} catch (Exception e) {
				logger.error("Error obteniendo imágenes", e.getMessage());
			} finally {
				if (in != null) {
					in.close();
				}
			}
			return imagen;
		}

		
		//@RequestMapping(value = "/service/transporte/image", method = RequestMethod.POST, produces = MediaType.IMAGE_PNG_VALUE)
		@RequestMapping(value = "/service/transporte/image", method = RequestMethod.POST)
		public @ResponseBody String getImageTransportist(@RequestHeader HttpHeaders headers,
				@RequestBody UsuarioTransporte request) throws Exception,
				IOException {
			logger.info("Obteniendo image.");

			HeaderRq headerRq = headerRqUtil.getHttpHeader(headers);
			String encodedImage = null;

			InputStream in = null;
			byte[] imagen = null;

			try {
				String path = env.getProperty("path.transportista.foto");
				
			    String pathImg = Paths.get(path, request.getFoto()).toString();
			    
				logger.info("Obteniendo imágenes."+pathImg);
				//String pathImg = "C:/Apps/taxiboox/pasajero/"request.getFoto();
				if (pathImg != null && !pathImg.isEmpty()) {
					Path ruta = Paths.get(pathImg);
					in = Files.newInputStream(ruta);
					imagen = IOUtils.toByteArray(in);
					encodedImage = Base64.encode(imagen);
				}
				
			} catch (Exception e) {
				logger.error("Error obteniendo imágenes", e.getMessage());
			} finally {
				if (in != null) {
					in.close();
				}
			}
			return encodedImage;
		}

		
		@RequestMapping(value ="/service/transporte/loginSession", 
				method = RequestMethod.POST, produces = { "application/json" })
		@ResponseBody
		public TransactionRs<SessionTransportista> loginSession(
				@RequestHeader HttpHeaders headers, @RequestBody UsuarioTransporte request) {
			
			logger.info("loginSession."+request.getEmail());
			TransactionRs<SessionTransportista> response = new TransactionRs<SessionTransportista>();
			
			try {
				HeaderRq headerRq = headerRqUtil.getHttpHeader(headers);
				
				response.setRespuesta(sessionTransportistaService.loginDirect(request,headerRq));
				
			} catch (Exception e) {
				logger.error("Error loginSession. ",e.getMessage());
				response.setCodigoError("5000");
				response.setDescripcion(e.getMessage());
			}
			
			return response;
		}

		@RequestMapping(value ="/service/transporte/exitSession", 
				method = RequestMethod.POST, produces = { "application/json" })
		@ResponseBody
		public TransactionRs<SessionTransportista> exitSession(
				@RequestHeader HttpHeaders headers) {
			
			logger.info("exitSession.");
			TransactionRs<SessionTransportista> response = new TransactionRs<SessionTransportista>();
			
			try {
				HeaderRq headerRq = headerRqUtil.getHttpHeader(headers);
				sessionTransportistaService.deleteSession(headerRq.getToken());
				
			} catch (Exception e) {
				logger.error("Error validaSession. ",e.getMessage());
				response.setCodigoError("5000");
				response.setDescripcion("Error Session not found");
			}
			
			return response;
		}

		
		
		@RequestMapping(value ="/service/transporte/imagePerfil", 
				method = RequestMethod.POST, produces = { "application/json" })
		@ResponseBody
		public TransactionRs<ImageResponse> imagePerfil(
				@RequestHeader HttpHeaders headers, @RequestBody UsuarioTransporte request) {
			
			TransactionRs<ImageResponse> response = new TransactionRs<ImageResponse>();
			logger.info("imagePerfil.");
			
			try {
			HeaderRq headerRq = headerRqUtil.getHttpHeader(headers);
			logger.info("imagePerfil:"+request.toString());
			//HeaderRq headerRq = headerRqUtil.getHttpHeader(headers);
			ImageResponse bean = new ImageResponse();
			
			String imagen = Util.getImageProfileEncode64(request.getFoto(), env);
			String extension = Util.getExtensionProfile(request.getFoto());
			logger.info("extension:"+extension);
			//logger.info("imageProfile:"+imageProfile);
			bean.setImagen(imagen);
			bean.setExtension(extension);
			response.setRespuesta(bean);
				
			} catch (Exception e) {
				logger.error("Error validaSession. ",e.getMessage());
				response.setCodigoError("5000");
				response.setDescripcion(e.getMessage());
			}
			
			return response;
		}


		
		@RequestMapping(value ="/service/tranposrtista/sendOlvido", 
				method = RequestMethod.POST, produces = { "application/json" })
		@ResponseBody
		public TransactionRs<BeanResponse> sendOlvido(
				@RequestHeader HttpHeaders headers, @RequestBody UsuarioTransporte request) {
			
			logger.info("sendOlvido....");
			TransactionRs<BeanResponse> response = new TransactionRs<BeanResponse>();
			
			try {
				HeaderRq headerRq = headerRqUtil.getHttpHeader(headers);
				
				if(request.getEmail()==null){
					throw new Exception("Error not found");
				}
				
				BeanResponse respuesta = new BeanResponse();
				respuesta.setValor(sessionTransportistaService.generaCodeEmail(request));
				response.setRespuesta(respuesta);
				
			} catch (Exception e) {
				logger.error("Error sendOlvido. ", e.getMessage());
				response.setCodigoError("5000");
				response.setDescripcion(e.getMessage());
			}
			
			return response;
		}
		
		
		@RequestMapping(value ="/service/tranposrtista/updatePassword", 
				method = RequestMethod.POST, produces = { "application/json" })
		@ResponseBody
		public TransactionRs updatePassword(
				@RequestHeader HttpHeaders headers, @RequestBody UsuarioTransporte request) {
			
			logger.info("updatePassword...."+request.toString());
			TransactionRs response = new TransactionRs();
			
			try {
				HeaderRq headerRq = headerRqUtil.getHttpHeader(headers);
							
				sessionTransportistaService.upddateClaveConductor(request);
				
			} catch (Exception e) {
				logger.error("Error updatePassword. ", e.getMessage());
				response.setCodigoError("5000");
				response.setDescripcion(e.getMessage());
			}
			
			return response;
		}

}
