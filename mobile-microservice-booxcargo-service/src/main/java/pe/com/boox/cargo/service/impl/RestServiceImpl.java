package pe.com.boox.cargo.service.impl;

import java.util.List;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import pe.com.boox.cargo.model.Guia;
import pe.com.boox.cargo.model.Ruta;
import pe.com.boox.cargo.model.TransactionRs;
import pe.com.boox.cargo.service.RestService;

@Component
public class RestServiceImpl implements RestService {

	/*
    @Autowired
    private RestTemplate restTemplate;
    
	public RestTemplate restTemplate() {
	    return new RestTemplate();
	}*/

    @Override
    public TransactionRs addCoordenadas(String urlList, Ruta request) {
    	
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json"));
        headers.add("Content-Type", "application/json");
     
        HttpEntity<Ruta> requestEntity = new HttpEntity<>(request, headers);
        
        ResponseEntity<TransactionRs> responseEntity = 
        		new RestTemplate().exchange(urlList, HttpMethod.POST, requestEntity,TransactionRs.class);
		
        return responseEntity.getBody();       
    }
	
    @Override
    public List<Ruta> listCoordenadas(String urlList, Guia request) throws Exception {
    	
		List<Ruta> respo = null;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json"));
        headers.add("Content-Type", "application/json");
     
        HttpEntity<Guia> requestEntity = new HttpEntity<>(request, headers);
        
		ResponseEntity<TransactionRs> responseEntity = new RestTemplate()
				.exchange(urlList, HttpMethod.POST, requestEntity,
						TransactionRs.class);
		
		TransactionRs<List<Ruta>> respuesta = responseEntity.getBody();
		
		if ("0000".equals(respuesta.getCodigoError())) {

			ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
			String cadena = ow.writeValueAsString(respuesta.getRespuesta());
			ObjectMapper mapper = new ObjectMapper();
			
			respo = mapper.readValue(cadena,new TypeReference<List<Ruta>>(){});

		} else {
			System.out.println("ERROR:::"+respuesta.getCodigoError());

		}
        
        return respo;       
    }
    
}
