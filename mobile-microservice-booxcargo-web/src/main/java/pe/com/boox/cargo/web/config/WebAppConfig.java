package pe.com.boox.cargo.web.config;

import java.util.Properties;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.planetj.servlet.filter.compression.CompressingFilter;

import pe.com.boox.cargo.web.filter.AccessFilter;
import pe.com.boox.cargo.web.filter.LoggingFilter;


@Configuration
@ComponentScan({ "pe.com.boox.cargo.web" })
@EnableWebMvc
public class WebAppConfig extends WebMvcConfigurerAdapter {

    @Value("${mail.protocol}")  // this is to read variable from application.properties
    private String mailProtocol;

    @Value("${mail.smtp.host}")
    private String host;

    @Value("${mail.smtp.port}")
    private Integer port;

    @Value("${mail.support.username}")
    private String userName;

    @Value("${mail.support.password}")
    private String password;

    @Value("${mail.smtp.auth}")
    private String smtpAuth;

    @Value("${mail.debug}")
    private String mailDebug;

	/**
	 * Método configureContentNegotiation
	 * @param configurer
	 */
	@Override
	public void configureContentNegotiation(
			ContentNegotiationConfigurer configurer) {
		super.configureContentNegotiation(configurer);
		configurer.favorParameter(true);
	}
	
	
	/**
	 * Método configureContentNegotiation
	 * @return filterRegistrationBean
	 */
	@Bean
	public FilterRegistrationBean filterRegistrationBean() {
		FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding("UTF-8");
		registrationBean.setFilter(characterEncodingFilter);
		return registrationBean;
	}

	
	/**
	 * Método compressingFilter
	 * @return Filter
	 */
	@Bean
	public Filter compressingFilter() {
		return new CompressingFilter();
	}

	
	/**
	 * Método accessFilterRegistration
	 * @return FilterRegistrationBean
	 * registration.addInitParameter("appKey", "taxiboox");
	 */
	@Bean
	public FilterRegistrationBean accessFilterRegistration() {

		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(accessFilter());
		registration.addInitParameter("appKey", "booxagency");
		registration.addUrlPatterns("/rest/*");
		registration.setName("accessFilter");
		return registration;
	}

	/**
	 * Método accessFilter
	 * @return SecurityAccessFilter
	 */
	@Bean(name = "accessfilter")
 	public AccessFilter accessFilter() {
		return new AccessFilter();
 
	}
	
	
	@Bean(name = "loggingFilter")
 	public LoggingFilter loggingFilter() { 
		return new LoggingFilter();
	}
	

	/*  */
    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        
        javaMailSender.setProtocol(mailProtocol);
        javaMailSender.setHost(host);
        javaMailSender.setPort(port);
        javaMailSender.setUsername(userName);
        javaMailSender.setPassword(password);
        javaMailSender.setJavaMailProperties(getMailProperties());

        return javaMailSender;
    }
 
    private Properties getMailProperties() {
    	/*
    	smtpAuth="true";
    	mailDebug="true";
    	 */
    	Properties properties = new Properties();
        properties.setProperty("mail.smtp.auth", smtpAuth);
        properties.setProperty("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.debug", mailDebug);
        return properties;
    }


}
