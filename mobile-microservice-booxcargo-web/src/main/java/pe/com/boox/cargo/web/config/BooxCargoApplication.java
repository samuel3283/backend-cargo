package pe.com.boox.cargo.web.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.WebApplicationInitializer;

import de.codecentric.boot.admin.model.Application;


@Configuration
@ComponentScan("pe.com.boox.cargo.*")
@EnableAutoConfiguration
public class BooxCargoApplication extends SpringBootServletInitializer implements
WebApplicationInitializer {
	
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);			    
	}
	
	@Bean
	public DriverManagerDataSource dataSource() {
		
		DriverManagerDataSource datasource = new DriverManagerDataSource();
		String url = "jdbc:mysql://192.69.210.154:3306/booxagen_cargo";
		datasource.setDriverClassName("com.mysql.jdbc.Driver");
		datasource.setUsername("booxagen_ucargo");
		datasource.setPassword("1qa2ws3ed");

/*
		url = "jdbc:mysql://localhost:3306/booxagen_cargo";
		datasource.setUsername("root");
		datasource.setPassword("1235789");
 */
 
		datasource.setUrl(url);
		return datasource;
	}
		
	@Bean
	public JdbcTemplate jdbcTemplateMySql() {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource());
		//jdbcTemplate.setDataSource(dataSourceOrcl());
		return jdbcTemplate;
	}
	 

	
}
