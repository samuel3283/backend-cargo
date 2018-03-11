package pe.com.boox.cargo.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import pe.com.boox.cargo.model.BeanResponse;
import pe.com.boox.cargo.model.Chofer;
import pe.com.boox.cargo.model.Guia;
import pe.com.boox.cargo.model.HeaderRq;
import pe.com.boox.cargo.model.Subasta;
import pe.com.boox.cargo.model.TransactionRs;
import pe.com.boox.cargo.model.VehiculoTransporte;
import pe.com.boox.cargo.service.ChoferService;
import pe.com.boox.cargo.service.GuiaService;
import pe.com.boox.cargo.service.SubastaService;
import pe.com.boox.cargo.service.core.HeaderRqUtil;


@RestController
@RequestMapping("/rest")
public class ChoferController {

	private final Logger logger = LoggerFactory
			.getLogger(ChoferController.class);

	@Autowired
	ChoferService choferService;

	@Autowired
	SubastaService subastaService;
	
	@Autowired
	private HeaderRqUtil headerRqUtil;

	
	@RequestMapping(value ="/service/chofer/insert", 
			method = RequestMethod.POST, produces = { "application/json" })
	@ResponseBody
	public TransactionRs<Map<String, Object>> insertChofer(
			@RequestHeader HttpHeaders headers, @RequestBody Chofer request) {
		
		logger.info("insert chofer.");
		TransactionRs<Map<String, Object>> response = new TransactionRs<Map<String, Object>>();
		HeaderRq headerRq = headerRqUtil.getHttpHeader(headers);
		Map<String, Object> mapa = new HashMap<>();
		
		try {
		logger.info("==>data:"+request.toString());	
		String idChofer = String.valueOf(choferService.insertaChofer(request, headerRq.getToken()));
		mapa.put("idChofer", idChofer);
		response.setRespuesta(mapa);
		} catch (Exception e) {
			logger.error("Error insertaChofer. ",e.getMessage());
			response.setCodigoError("5000");
			response.setDescripcion(e.getMessage());
		}

		return response;

	}

	/*
		@RequestMapping(value ="/service/chofer/insertChofer", 
				method = RequestMethod.POST, produces = { "application/json" })
		@ResponseBody
		public TransactionRs<Map<String, Object>> insertChofer(
				@RequestHeader HttpHeaders headers, @RequestBody Chofer request) {
			
			logger.info("insertChofer.");
			TransactionRs<Map<String, Object>> response = new TransactionRs<Map<String, Object>>();
			HeaderRq headerRq = headerRqUtil.getHttpHeader(headers);
			
			try {
			logger.info("==>data:"+request.toString());	
			choferService.insertChofer(request, headerRq.getToken());			
			} catch (Exception e) {
				logger.error("Error insertChofer. ",e.getMessage());
				response.setCodigoError("5000");
				response.setDescripcion(e.getMessage());
			}

			return response;
		}
*/
		
		@RequestMapping(value ="/service/chofer/update", 
				method = RequestMethod.POST, produces = { "application/json" })
		@ResponseBody
		public TransactionRs<Map<String, Object>> updateChofer(
				@RequestHeader HttpHeaders headers, @RequestBody Chofer request) {
			
			logger.info("updateChofer.");
			TransactionRs<Map<String, Object>> response = new TransactionRs<Map<String, Object>>();
			HeaderRq headerRq = headerRqUtil.getHttpHeader(headers);
			Map<String, Object> mapa = new HashMap<>();
			
			try {
			logger.info("==>data:"+request.toString());	
			int idChofer= choferService.updateChofer(request,headerRq.getToken());
			mapa.put("idChofer", idChofer);
			response.setRespuesta(mapa);
			
			} catch (Exception e) {
				logger.error("Error updateChofer. ",e.getMessage());
				response.setCodigoError("5000");
				response.setDescripcion(e.getMessage());
			}

			return response;
		}

	
		
		@RequestMapping(value ="/service/chofer/list", 
				method = RequestMethod.POST, produces = { "application/json" })
		@ResponseBody		
		public TransactionRs<List<Chofer>> listChofer(
				@RequestHeader HttpHeaders headers, @RequestBody Chofer request) {
			
			logger.info("listChofer.");
			TransactionRs<List<Chofer>> response = new TransactionRs<List<Chofer>>();
			HeaderRq headerRq = headerRqUtil.getHttpHeader(headers);

			try {
			logger.info("==>listChofer==>TOKEN:::"+headerRq.getToken());	
			logger.info("==>data:"+request.toString());	
			response.setRespuesta(choferService.listChofer(request,headerRq.getToken()));
			
			} catch (Exception e) {
				logger.error("Error listChofer. ",e.getMessage());
				response.setCodigoError("5000");
				response.setDescripcion(e.getMessage());
			}

			return response;
		}
	

		
		
		@RequestMapping(value ="/service/chofer/get", 
				method = RequestMethod.POST, produces = { "application/json" })
		@ResponseBody		
		public TransactionRs<Chofer> getChofer(
				@RequestHeader HttpHeaders headers, @RequestBody Chofer request) {
			
			logger.info("getChofer.");
			TransactionRs<Chofer> response = new TransactionRs<Chofer>();
			HeaderRq headerRq = headerRqUtil.getHttpHeader(headers);

			try {
			logger.info("==>getChofer==>TOKEN:::"+headerRq.getToken());	
			logger.info("==>data:"+request.toString());	
			response.setRespuesta(choferService.getChofer(request,headerRq.getToken()));
			
			} catch (Exception e) {
				logger.error("Error getChofer. ",e.getMessage());
				response.setCodigoError("5000");
				response.setDescripcion(e.getMessage());
			}

			return response;
		}

		
		@RequestMapping(value = "/service/chofer/uploadBrevete", method = RequestMethod.POST)
		  @ResponseBody
		  public TransactionRs<BeanResponse> uploadBrevete(
				  @RequestHeader HttpHeaders headers,
				  @RequestParam("licencia") MultipartFile licencia) {
			
			logger.info("uploadBrevete.");
			TransactionRs<BeanResponse> response = new TransactionRs<BeanResponse>();
			HeaderRq headerRq = headerRqUtil.getHttpHeader(headers);
			
		    try {
		    	choferService.uploadFile(headerRq, licencia);
		    } catch (Exception e) {
				logger.error("Error uploadBrevete. ",e.getMessage());
				response.setCodigoError("5000");
				response.setDescripcion("Error al guardar. "+e.getMessage());
		    }
		    
		    return response;
		  }



}
