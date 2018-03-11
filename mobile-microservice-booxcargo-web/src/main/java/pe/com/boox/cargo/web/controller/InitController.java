package pe.com.boox.cargo.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import pe.com.boox.cargo.model.TransactionRs;


public class InitController {

	private final Logger logger = LoggerFactory
			.getLogger(InitController.class);

	 @RequestMapping("/")
	 @ResponseBody
	 String home() {
	 return "BooxCargo v1.0";
	 }
	 
		@RequestMapping(value ="/service/genera", 
				method = RequestMethod.POST, produces = { "application/json" })
		@ResponseBody
		public TransactionRs generaSms(
				@RequestHeader HttpHeaders headers, @RequestBody Usuario request) {
			
			logger.info("genera.............");
			TransactionRs response = new TransactionRs();
						
			return response;
		}

}
