package pe.com.boox.cargo.web.filter;

import java.io.IOException;
import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties.Session;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import pe.com.boox.cargo.model.TransactionRs;
import pe.com.boox.cargo.web.util.Constants;



@WebFilter(urlPatterns = { "/rest/*" }, description = "Session Checker Filter")
public class AccessFilter implements Filter {
	private static final Logger logger = LoggerFactory.getLogger(AccessFilter.class);
	private static final String PARAM_TOKEN_ID = "token";
	private static final String PARAM_APP_KEY = "appKey";
	private final static String POST = "POST";
	private String appKey;
	private static final long MIN_TIMEOUT = 60000;
	private static final long MIN_INACTIVE_TIMEOUT = 60000;
	private static final String SESSION_TIMEOUT_KEY = "sessionTimeLimit";
	private static final String SESSION_INACTIVE_TIMEOUT_KEY = "sessionInactiveTimeLimit";
	private static long timeOut = 0;
	private static long timeOutInac = 0;
	private FilterConfig configure;
	Session session;
	
	//@Autowired
	//SessionService sessionService;
	
	/**
	 * Default constructor.
	 */
	public AccessFilter() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		String tokenId = null;
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		timeOut = Long.parseLong("7200000");	//SESSION_TIMEOUT_KEY  ==>Tiempo maximo de duracion de una sesion de usuario en la aplicacion 
		timeOutInac = Long.parseLong("300000"); //SESSION_INACTIVE_TIMEOUT_KEY ==>Tiempo maximo de inactividad permitido para un usuario en la aplicacion.
		// UTF-8
		req.setCharacterEncoding(Constants.ENCODING_UTF8);
		res.setCharacterEncoding(Constants.ENCODING_UTF8);
		
		tokenId = (req.getHeader(PARAM_TOKEN_ID) == null) ? StringUtils.EMPTY
				: req.getHeader(PARAM_TOKEN_ID);

		// se verifica el acceso
		if (isValidRequest(req)) {
			//RequestWrapper requestWrapper = new RequestWrapper((HttpServletRequest) req);
			String appValue = (null == req.getHeader(PARAM_APP_KEY)) ? StringUtils.EMPTY
					: req.getHeader(PARAM_APP_KEY);
			logger.info("Ingreso a la siguiente URL " + req.getRequestURL());
			logger.info("Header:");
			logger.info("appKey: " + appValue);
			logger.info("tokenSeguridad:" + tokenId);

			
			
			
			logger.info("VALID_SESSION_OK");
			MDC.put("sessionId", tokenId);
			
			 res.setHeader("Access-Control-Allow-Origin", "*");
			 res.setHeader("Access-Control-Allow-Methods", "POST");
			 //res.setHeader("Access-Control-Max-Age", "3600");
			 res.setHeader("Access-Control-Allow-Headers", "Content-Type, enctype, token, codigo, appKey, device, deviceType, Access-Control-Allow-Headers, Authorization, X-Requested-With");
			 res.setHeader("Content-Type", "application/json, multipart/form-data");
			 
			 chain.doFilter(request, response);

			/*
			if (!tokenId.equals(StringUtils.EMPTY)) {
				logger.info("con tokenId");
	
				try {
					session = sessionService.getSessionByToken(tokenId);
					if (session != null) {
						logger.info("session not null");

						logger.info("VALID_SESSION_OK");
						MDC.put("sessionId", tokenId);
						chain.doFilter(request, response);
						
					} else {
						logger.info(ErrorCodesEnum.ERR_INVALID_TOKEN.getMessage());
						handleError(ErrorCodesEnum.ERR_INVALID_TOKEN.getCode(),
								ErrorCodesEnum.ERR_INVALID_TOKEN.getMessage(), res);
					}
				} catch (Exception e) {					
					logger.error(ErrorCodesEnum.ERROR_VALIDATION_SERVICE.getMessage() + e.getMessage());					 
					handleError(ErrorCodesEnum.ERROR_VALIDATION_SERVICE.getCode(),
							ErrorCodesEnum.ERROR_VALIDATION_SERVICE.getMessage(), res);
				}
			} else {
					logger.info("Usuario sin token no autenticado"); 
					chain.doFilter(request, response);
			}
			*/
		} else {
			logger.info("ERR_REQUEST_INVALID.getMessage()");
			handleError("ErrorCodesEnum.ERR_REQUEST_INVALID.getCode()",
					"ErrorCodesEnum.ERR_REQUEST_INVALID.getMessage()", res);			
		}

	}

	public void handleError(String errorCode, String mje, HttpServletResponse res) {
		try {
			TransactionRs<String> error = new TransactionRs<String>();
			error.setCodigoError(errorCode);
			error.setDescripcion(mje);
			error.setRespuesta(null);
			
			// create ObjectMapper instance
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
			StringWriter stringEmp = new StringWriter();
			objectMapper.writeValue(stringEmp, error);
			res.setStatus(200);
			res.setContentType(Constants.CONTENT_TYPE_JSON);
			res.setCharacterEncoding(Constants.ENCODING_UTF8);
			res.getWriter().write(stringEmp.toString());
		} catch (Exception ex) {
			logger.error("ERROR:" + ex.getMessage());
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		this.configure = fConfig;
		/*
		sessionService = (SessionService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(configure.getServletContext())
				.getBean(SessionService.class);
*/
		appKey = configure.getServletContext().getInitParameter(PARAM_APP_KEY);		
	}

	/**
	 * Valida el origen de una solicitud. Por defecto la aplicaciÃ³n sÃ³lo debe
	 * recibir mÃ©todos POST y provenientes de un dispositivo movil.
	 * 
	 * @param request
	 * @return
	 */
	private boolean isValidRequest(HttpServletRequest request) {
		String url = request.getRequestURL().toString();		
		boolean valid = true;
		String method = request.getMethod();
		/*
		if (url.contains("/rest")){
		
			String appValue = (null == request.getHeader(PARAM_APP_KEY)) ? StringUtils.EMPTY
				: request.getHeader(PARAM_APP_KEY);

			
		if (!POST.equalsIgnoreCase(method) 
				|| !appValue.equalsIgnoreCase("booxagency")) {
			valid = false;
		}
		}
		*/
		return valid;
	}

	/**
	 * Determina si la sesiÃ³n es vÃ¡lida, o sea, si no se ha vencido el tiempo
	 * total o de inactividad.
	 * 
	 * @param userInfo
	 *            objeto de informaciÃ³n de sesiÃ³n del usuario.
	 * @param redirectOnError
	 *            parÃ¡metro de salida donde se guarda el URL de
	 *            redireccionamiento en caso que la sesiÃ³n no sea vÃ¡lida.
	 * @return <code>true</code> si la sesiÃ³n es vÃ¡lida.
	 * @throws ParseException 
	 */
	
	/*
	private static boolean isValidSession(Session userInfo) throws ParseException {
		boolean isValid = false;
		logger.info("isValidSession ::"+userInfo.toString());
		// obtiene los parÃ¡metros de timeout y el tiempo actual.
		//long sessionTimeout = getSessionTimeout();
		long inactiveTimeout = getSessionInactiveTimeout();
		long now = System.currentTimeMillis();
		
		logger.info("Expira: ==>now:"+ now);			
		
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = format.parse(userInfo.getFecModifica()); // mysql datetime format		
		long ultimaConexion = date.getTime();
		//Login 1:00  Modifica 1:05  Expira 1:10
		// 1:10 - 1:05
		logger.info("Expira: ==>ultimaConexion:"+ultimaConexion+"==>inactiveTimeout:"+inactiveTimeout);			
		
		if ((now - ultimaConexion) >= inactiveTimeout) {			
			logger.info("Sesion expirada, por tiempo de inactividad límite al usuario: "+ userInfo.getEmail());			
		} else {
			isValid = true;
		}		
			 
		return isValid;
	}
	*/
	
}
