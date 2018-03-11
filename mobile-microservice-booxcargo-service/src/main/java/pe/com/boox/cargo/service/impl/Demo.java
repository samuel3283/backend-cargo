package pe.com.boox.cargo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import pe.com.boox.cargo.model.Guia;
import pe.com.boox.cargo.model.Ruta;
import pe.com.boox.cargo.model.TransactionRs;

public class Demo {

	//@Autowired	
	private RestTemplate restTemplate;
	
	public RestTemplate restTemplate() {
	    return restTemplate;
	}
	
	public static void main(String[] args) {
		System.out.println("-----");
		
		
		String urlList="https://k6tc5wolml.execute-api.us-east-1.amazonaws.com/DevCoordenadasAdd/coordenadas/list";
		HttpHeaders header = prepareHeader();
		
		Guia guia = new Guia();
		guia.setCodigo(new Integer("43"));
		
		HttpEntity<Guia> entity = new HttpEntity<Guia>(guia, header);
	
		try {

			ResponseEntity<TransactionRs> responseEntity = new RestTemplate()
					.exchange(urlList, HttpMethod.POST, entity,
							TransactionRs.class);

			TransactionRs<List<Ruta>> respuesta = responseEntity.getBody();
			
			if ("0000".equals(respuesta.getCodigoError())) {
				System.out.println("-----"+respuesta.getDescripcion());
				System.out.println("-----"+respuesta.getRespuesta());

				ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
				String cadena = ow.writeValueAsString(respuesta.getRespuesta());
				ObjectMapper mapper = new ObjectMapper();
				
				List<Ruta> respo = null;
				respo = mapper.readValue(cadena,new TypeReference<List<Ruta>>(){});
				System.out.println("-----"+respo.get(0).getLat());

			} else {
				System.out.println("ERROR:::"+respuesta.getCodigoError());

			}

		} catch (Exception e) {
			System.out.println("Error:::"+e.getMessage());
		}

		
		
		
		/*
		String urlList="https://tg7pesqy8d.execute-api.us-east-1.amazonaws.com/DEVCoordenadas/coordenada";
		HttpHeaders header = prepareHeader();
		
		Ruta ruta = new Ruta();
		ruta.setCodigoguia(new Integer("43"));
		ruta.setLat("12.777");
		ruta.setLng("7.123");
		
		HttpEntity<Ruta> entity = new HttpEntity<Ruta>(ruta, header);
	
		try {

			ResponseEntity<TransactionRs> responseEntity = new RestTemplate()
					.exchange(urlList, HttpMethod.POST, entity,
							TransactionRs.class);

			TransactionRs<String> respuesta = responseEntity.getBody();
			
			if ("000".equals(respuesta.getCodigoError())) {
				System.out.println("-----"+respuesta.getDescripcion());
				//ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
				//String cadena = ow.writeValueAsString(respuesta.getContent());
				//ObjectMapper mapper = new ObjectMapper();
				//respo = mapper.readValue(cadena,CIFrequentOperationRestRs.class);				
			} else {
				System.out.println("ERROR:::"+respuesta.getCodigoError());

			}

		} catch (Exception e) {
			System.out.println("Error:::"+e.getMessage());
		}
		*/
		
	}

	
	private static HttpHeaders prepareHeader() {

		System.out.println("Preparar Header.");

		HttpHeaders header = new HttpHeaders();
		header.setContentType(new MediaType("application", "json"));
		//header.add("appKey", "12345");
		return header;
	}
	
	
}
