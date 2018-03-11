package pe.com.boox.cargo.web.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import pe.com.boox.cargo.model.BeanResponse;
import pe.com.boox.cargo.model.HeaderRq;
import pe.com.boox.cargo.model.TransactionRs;
import pe.com.boox.cargo.model.UsuarioEmpresa;
import pe.com.boox.cargo.model.VehiculoTransporte;
import pe.com.boox.cargo.service.UsuarioEmpresaService;
import pe.com.boox.cargo.service.VehiculoTransporteService;
import pe.com.boox.cargo.service.core.HeaderRqUtil;


@RestController
@RequestMapping("/rest")
public class VehiculoTransporteController {

	@Autowired
	private Environment env;

	@Autowired
	private HeaderRqUtil headerRqUtil;

	private final Logger logger = LoggerFactory
			.getLogger(VehiculoTransporteController.class);

	@Autowired
	VehiculoTransporteService vehiculoTransporteService;
	
		@RequestMapping(value ="/service/transporte/insertVehiculo", 
				method = RequestMethod.POST, produces = { "application/json" })
		@ResponseBody
		public TransactionRs<Map<String, Object>> insertVehiculo(
				@RequestHeader HttpHeaders headers, @RequestBody VehiculoTransporte request) {
			
			logger.info("insertVehiculo.");
			TransactionRs<Map<String, Object>> response = new TransactionRs<Map<String, Object>>();
			HeaderRq headerRq = headerRqUtil.getHttpHeader(headers);
			BeanResponse bean = new BeanResponse();
			Map<String, Object> mapa = new HashMap<>();
			
			try {
			logger.info("==>data:"+request.toString());	
			String idVehiculo = String.valueOf(vehiculoTransporteService.insertVehiculoTransporte(request, headerRq.getToken()));
			mapa.put("idVehiculo", idVehiculo);
			response.setRespuesta(mapa);
			} catch (Exception e) {
				logger.error("Error insertVehiculo. ",e.getMessage());
				response.setCodigoError("5000");
				response.setDescripcion(e.getMessage());
			}

			return response;
		}

		@RequestMapping(value ="/service/transporte/updateVehiculo", 
				method = RequestMethod.POST, produces = { "application/json" })
		@ResponseBody
		public TransactionRs<Map<String, Object>> updateVehiculo(
				@RequestHeader HttpHeaders headers, @RequestBody VehiculoTransporte request) {
			
			logger.info("insertVehiculo.");
			TransactionRs<Map<String, Object>> response = new TransactionRs<Map<String, Object>>();
			HeaderRq headerRq = headerRqUtil.getHttpHeader(headers);
			BeanResponse bean = new BeanResponse();
			Map<String, Object> mapa = new HashMap<>();
			
			try {
			logger.info("==>data:"+request.toString());	
			String idVehiculo = String.valueOf(vehiculoTransporteService.updateVehiculoTransporte(request, headerRq.getToken()));
			mapa.put("idVehiculo", idVehiculo);
			response.setRespuesta(mapa);
			} catch (Exception e) {
				logger.error("Error updateVehiculo. ",e.getMessage());
				response.setCodigoError("5000");
				response.setDescripcion(e.getMessage());
			}

			return response;
		}

		
		
		@RequestMapping(value ="/service/transporte/asignaChofer", 
				method = RequestMethod.POST, produces = { "application/json" })
		@ResponseBody
		public TransactionRs<Map<String, Object>> asignaChofer(
				@RequestHeader HttpHeaders headers, @RequestBody VehiculoTransporte request) {
			
			logger.info("asignaChofer.");
			TransactionRs<Map<String, Object>> response = new TransactionRs<Map<String, Object>>();
			HeaderRq headerRq = headerRqUtil.getHttpHeader(headers);
			BeanResponse bean = new BeanResponse();
			Map<String, Object> mapa = new HashMap<>();
			
			try {
			logger.info("==>data:"+request.toString());	
			vehiculoTransporteService.asignaChofer(request, headerRq.getToken());
			} catch (Exception e) {
				logger.error("Error asignaChofer. ",e.getMessage());
				response.setCodigoError("5000");
				response.setDescripcion(e.getMessage());
			}
			
			return response;
		}
		
		@RequestMapping(value ="/service/transporte/listVehiculo", 
				method = RequestMethod.POST, produces = { "application/json" })
		@ResponseBody
		public TransactionRs<List<VehiculoTransporte>> listVehiculo(
				@RequestHeader HttpHeaders headers, @RequestBody VehiculoTransporte request) {
			
			logger.info("listVehiculo.");
			TransactionRs<List<VehiculoTransporte>> response = new TransactionRs<List<VehiculoTransporte>>();
			HeaderRq headerRq = headerRqUtil.getHttpHeader(headers);
			
			try {
			response.setRespuesta(vehiculoTransporteService.listVehiculoTransporte(request,headerRq.getToken()));
			} catch (Exception e) {
				logger.error("Error listVehiculo. ",e.getMessage());
				response.setCodigoError("5000");
				response.setDescripcion(e.getMessage());
			}
			
			return response;
		}


		@RequestMapping(value ="/service/transporte/listShortVehiculo", 
				method = RequestMethod.POST, produces = { "application/json" })
		@ResponseBody
		public TransactionRs<List<VehiculoTransporte>> listShortVehiculo(
				@RequestHeader HttpHeaders headers, @RequestBody VehiculoTransporte request) {
			
			logger.info("listShortVehiculo.");
			TransactionRs<List<VehiculoTransporte>> response = new TransactionRs<List<VehiculoTransporte>>();
			HeaderRq headerRq = headerRqUtil.getHttpHeader(headers);
			
			try {
			response.setRespuesta(vehiculoTransporteService.listShortVehiculoTransporte(request,headerRq.getToken()));
			} catch (Exception e) {
				logger.error("Error listShortVehiculo. ",e.getMessage());
				response.setCodigoError("5000");
				response.setDescripcion(e.getMessage());
			}
			
			return response;
		}

		
		@RequestMapping(value ="/service/transporte/getVehiculo", 
				method = RequestMethod.POST, produces = { "application/json" })
		@ResponseBody
		public TransactionRs<VehiculoTransporte> getVehiculo(
				@RequestHeader HttpHeaders headers, @RequestBody VehiculoTransporte request) {
			
			logger.info("getVehiculo.");
			TransactionRs<VehiculoTransporte> response = new TransactionRs<VehiculoTransporte>();

			try {
			logger.info("==>data:"+request.toString());	
			response.setRespuesta(vehiculoTransporteService.getVehiculoTransporte(request));
			} catch (Exception e) {
				logger.error("Error getVehiculo. ",e.getMessage());
				response.setCodigoError("5000");
				response.setDescripcion(e.getMessage());
			}

			return response;
		}
		

		@RequestMapping(value = "/service/transporte/uploadTarjeta", method = RequestMethod.POST)
		  @ResponseBody
		  public TransactionRs<BeanResponse> uploadTarjeta(
				  @RequestHeader HttpHeaders headers,
				  @RequestParam("tarjeta") MultipartFile tarjeta) {
			
			logger.info("uploadTarjeta.");
			TransactionRs<BeanResponse> response = new TransactionRs<BeanResponse>();
			HeaderRq headerRq = headerRqUtil.getHttpHeader(headers);
			
		    try {

		    	vehiculoTransporteService.uploadFile("TARJETA", headerRq, tarjeta);
		   /*
		    	// Get the filename and build the local file path
		      String filename = tarjeta.getOriginalFilename();
		      String directory = env.getProperty("path.transportista.foto");
		      String filepath = Paths.get(directory, filename).toString();
		      
		      // Save the file locally
		      BufferedOutputStream stream =
		          new BufferedOutputStream(new FileOutputStream(new File(filepath)));
		      stream.write(tarjeta.getBytes());
		      stream.close();
		    */
		    } catch (Exception e) {
				logger.error("Error uploadFile. ",e.getMessage());
				response.setCodigoError("5000");
				response.setDescripcion("Error al guardar. "+e.getMessage());
		    }
		    
		    return response;
		  }


		
		@RequestMapping(value = "/service/transporte/uploadSoat", method = RequestMethod.POST)
		  @ResponseBody
		  public TransactionRs<BeanResponse> uploadSoat(
				  @RequestHeader HttpHeaders headers,
				  @RequestParam("soat") MultipartFile soat
				  ) {
			
			logger.info("uploadSoap");
			TransactionRs<BeanResponse> response = new TransactionRs<BeanResponse>();
			HeaderRq headerRq = headerRqUtil.getHttpHeader(headers);
			logger.info("uploadSoap==>codigo:"+headerRq.getCodigo());
			
		    try {
		    	
		    	vehiculoTransporteService.uploadFile("SOAT", headerRq, soat);

		    } catch (Exception e) {
				logger.error("Error uploadSoap. ",e.getMessage());
				response.setCodigoError("5000");
				response.setDescripcion("Error al guardar. "+e.getMessage());
		    }
		    
		    return response;
		  }

		@RequestMapping(value = "/service/transporte/uploadFoto1", method = RequestMethod.POST)
		  @ResponseBody
		  public TransactionRs<BeanResponse> uploadFoto1(
				  @RequestHeader HttpHeaders headers,
				  @RequestParam("foto1") MultipartFile foto1
				  ) {
			
			logger.info("uploadFoto1");
			TransactionRs<BeanResponse> response = new TransactionRs<BeanResponse>();
			HeaderRq headerRq = headerRqUtil.getHttpHeader(headers);
			logger.info("uploadFoto1==>codigo:"+headerRq.getCodigo());
			
		    try {
		    	
		    	vehiculoTransporteService.uploadFile("FOTO1", headerRq, foto1);

		    } catch (Exception e) {
				logger.error("Error uploadFoto1. ",e.getMessage());
				response.setCodigoError("5000");
				response.setDescripcion("Error al guardar. "+e.getMessage());
		    }
		    
		    return response;
		  }

}
