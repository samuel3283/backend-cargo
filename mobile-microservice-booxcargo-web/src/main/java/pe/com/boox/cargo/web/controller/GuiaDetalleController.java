package pe.com.boox.cargo.web.controller;

import java.util.List;
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
import pe.com.boox.cargo.model.GuiaDetalle;
import pe.com.boox.cargo.model.TransactionRs;
import pe.com.boox.cargo.service.GuiaDetalleService;


@RestController
@RequestMapping("/rest")
public class GuiaDetalleController {

	private final Logger logger = LoggerFactory
			.getLogger(GuiaDetalleController.class);

	@Autowired
	GuiaDetalleService guiaDetalleService;
	
		@RequestMapping(value ="/service/guiaDetalle/insert", 
				method = RequestMethod.POST, produces = { "application/json" })
		@ResponseBody
		public TransactionRs<BeanResponse> insertGuiaDetalle(
				@RequestHeader HttpHeaders headers, @RequestBody GuiaDetalle request) {
			
			logger.info("insertGuia.");
			TransactionRs<BeanResponse> response = new TransactionRs<BeanResponse>();

			try {
				
			logger.info("==>data:"+request.toString());	
			guiaDetalleService.insertGuiaDetalle(request);
			
			} catch (Exception e) {
				logger.error("Error insertGuia. ",e.getMessage());
				response.setCodigoError("5000");
				response.setDescripcion(e.getMessage());
			}

			return response;
		}

		
		@RequestMapping(value ="/service/guiaDetalle/list", 
				method = RequestMethod.POST, produces = { "application/json" })
		@ResponseBody
		public TransactionRs<List<GuiaDetalle>> listGuiaDetalle(
				@RequestHeader HttpHeaders headers, @RequestBody GuiaDetalle request) {
			
			logger.info("listGuia.");
			TransactionRs<List<GuiaDetalle>> response = new TransactionRs<List<GuiaDetalle>>();

			try {
			logger.info("==>data:"+request.toString());	
			response.setRespuesta(guiaDetalleService.listGuiaDetalle(request));
			
			} catch (Exception e) {
				logger.error("Error listGuia. ",e.getMessage());
				response.setCodigoError("5000");
				response.setDescripcion(e.getMessage());
			}

			return response;
		}

		
}
