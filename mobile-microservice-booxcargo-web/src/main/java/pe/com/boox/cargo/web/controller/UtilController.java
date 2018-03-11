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
import pe.com.boox.cargo.model.BeanUtil;
import pe.com.boox.cargo.model.Guia;
import pe.com.boox.cargo.model.HeaderRq;
import pe.com.boox.cargo.model.Subasta;
import pe.com.boox.cargo.model.TransactionRs;
import pe.com.boox.cargo.service.GuiaService;
import pe.com.boox.cargo.service.SubastaService;
import pe.com.boox.cargo.service.UtilService;
import pe.com.boox.cargo.service.core.HeaderRqUtil;


@RestController
@RequestMapping("/rest")
public class UtilController {

	private final Logger logger = LoggerFactory
			.getLogger(UtilController.class);

	@Autowired
	UtilService utilService;
	
	@RequestMapping(value ="/service/util/lstDepartamento", 
			method = RequestMethod.POST, produces = { "application/json" })
	@ResponseBody		
	public TransactionRs<List<BeanUtil>> lstDepartamento(
			@RequestHeader HttpHeaders headers, @RequestBody BeanUtil request) {
		
		logger.info("lstDepartamento.");
		TransactionRs<List<BeanUtil>> response = new TransactionRs<List<BeanUtil>>();

		try {
		logger.info("==>data:"+request.toString());	
		response.setRespuesta(utilService.lstDepartamento());
		
		} catch (Exception e) {
			logger.error("Error lstDepartamento. ",e.getMessage());
			response.setCodigoError("5000");
			response.setDescripcion(e.getMessage());
		}

		return response;
	}

	@RequestMapping(value ="/service/util/lstProvincia", 
			method = RequestMethod.POST, produces = { "application/json" })
	@ResponseBody		
	public TransactionRs<List<BeanUtil>> lstProvincia(
			@RequestHeader HttpHeaders headers, @RequestBody BeanUtil request) {
		
		logger.info("lstProvincia.");
		TransactionRs<List<BeanUtil>> response = new TransactionRs<List<BeanUtil>>();

		try {
		logger.info("==>data:"+request.toString());	
		response.setRespuesta(utilService.lstProvincia(request.getCodigo()));
		
		} catch (Exception e) {
			logger.error("Error lstProvincia. ",e.getMessage());
			response.setCodigoError("5000");
			response.setDescripcion(e.getMessage());
		}

		return response;
	}

	@RequestMapping(value ="/service/util/lstDistrito", 
			method = RequestMethod.POST, produces = { "application/json" })
		@ResponseBody		
		public TransactionRs<List<BeanUtil>> lstDistrito(
				@RequestHeader HttpHeaders headers, @RequestBody BeanUtil request) {
			
			logger.info("lstDistrito.");
			TransactionRs<List<BeanUtil>> response = new TransactionRs<List<BeanUtil>>();

			try {
			logger.info("==>data:"+request.toString());	
			response.setRespuesta(utilService.lstDistrito(request.getCodigo(), request.getDescripcion()));
			
			} catch (Exception e) {
				logger.error("Error lstDistrito. ",e.getMessage());
				response.setCodigoError("5000");
				response.setDescripcion(e.getMessage());
			}

			return response;
		}


		
}
