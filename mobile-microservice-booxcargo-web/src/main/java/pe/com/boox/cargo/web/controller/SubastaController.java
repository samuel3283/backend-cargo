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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import pe.com.boox.cargo.model.BeanResponse;
import pe.com.boox.cargo.model.Guia;
import pe.com.boox.cargo.model.HeaderRq;
import pe.com.boox.cargo.model.Subasta;
import pe.com.boox.cargo.model.TransactionRs;
import pe.com.boox.cargo.service.GuiaService;
import pe.com.boox.cargo.service.SubastaService;
import pe.com.boox.cargo.service.core.HeaderRqUtil;


@RestController
@RequestMapping("/rest")
public class SubastaController {

	private final Logger logger = LoggerFactory
			.getLogger(SubastaController.class);

	@Autowired
	GuiaService gsuiaService;

	@Autowired
	SubastaService subastaService;
	
	@Autowired
	private HeaderRqUtil headerRqUtil;


		@RequestMapping(value ="/service/subasta/misSubastas", 
				method = RequestMethod.POST, produces = { "application/json" })
		@ResponseBody		
		public TransactionRs<List<Subasta>> misSubastas(
				@RequestHeader HttpHeaders headers, @RequestBody Subasta request) {
			
			logger.info("misSubastas.");
			TransactionRs<List<Subasta>> response = new TransactionRs<List<Subasta>>();
			HeaderRq headerRq = headerRqUtil.getHttpHeader(headers);

			try {
			logger.info("==>misSubastas==>TOKEN:::"+headerRq.getToken());	
			logger.info("==>data:"+request.toString());	
			response.setRespuesta(subastaService.lstSubasta(request,headerRq.getToken()));
			
			} catch (Exception e) {
				logger.error("Error misSubastas. ",e.getMessage());
				response.setCodigoError("5000");
				response.setDescripcion(e.getMessage());
			}

			return response;
		}


		@RequestMapping(value ="/service/subasta/asignaSubasta", 
				method = RequestMethod.POST, produces = { "application/json" })
		@ResponseBody		
		public TransactionRs<Subasta> asignaSubasta(
				@RequestHeader HttpHeaders headers, @RequestBody Subasta request) {
			
			logger.info("asignaSubasta.");
			TransactionRs<Subasta> response = new TransactionRs<Subasta>();
			HeaderRq headerRq = headerRqUtil.getHttpHeader(headers);
			
			try {
			logger.info("==>asignaSubasta==>TOKEN:::"+headerRq.getToken());	
			logger.info("==>data:"+request.toString());	
			subastaService.asignaSubasta(request, headerRq.getToken());
			
			} catch (Exception e) {
				logger.error("Error asignaSubasta. ",e.getMessage());
				response.setCodigoError("5000");
				response.setDescripcion(e.getMessage());
			}

			return response;
		}


		
		@RequestMapping(value ="/service/subasta/lstSubasta", 
				method = RequestMethod.POST, produces = { "application/json" })
		@ResponseBody		
		public TransactionRs<List<Subasta>> lstSubasta(
				@RequestHeader HttpHeaders headers, @RequestBody Subasta request) {
			
			logger.info("lstSubasta.");
			TransactionRs<List<Subasta>> response = new TransactionRs<List<Subasta>>();
			HeaderRq headerRq = headerRqUtil.getHttpHeader(headers);

			try {
			logger.info("==>lstMisSubasta==>TOKEN:::"+headerRq.getToken());	
			logger.info("==>data:"+request.toString());	
			response.setRespuesta(subastaService.lstMisSubasta(request,headerRq.getToken()));
			
			} catch (Exception e) {
				logger.error("Error lstMisSubasta. ",e.getMessage());
				response.setCodigoError("5000");
				response.setDescripcion(e.getMessage());
			}
			
			return response;
		}

}
