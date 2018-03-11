package pe.com.boox.cargo.service.core;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import pe.com.boox.cargo.model.HeaderRq;



@Component
public class HeaderRqUtil {
	
	private HttpHeaders headers;
	public static final String HEADER_PARAM_TOKEN = "token";
	public static final String HEADER_PARAM_DEVICE_TYPE = "deviceType";
	public static final String HEADER_PARAM_DEVICE_ID = "device";
	public static final String HEADER_PARAM_CODIGO = "codigo";

	public HeaderRq getHttpHeader(HttpHeaders headers) {
		this.headers = headers;
		HeaderRq properties = null;
		if (headers != null) {
			properties = new HeaderRq(getValue(HEADER_PARAM_TOKEN),
					getValue(HEADER_PARAM_DEVICE_TYPE), getValue(HEADER_PARAM_DEVICE_ID),
					getValue(HEADER_PARAM_CODIGO));
		}
		return properties;
	}

	private String getValue(String property) {
		List<String> values = this.headers.get(property);
		if (values != null) {
			return (String) values.get(0);
		}
		return null;
	}
	
}
