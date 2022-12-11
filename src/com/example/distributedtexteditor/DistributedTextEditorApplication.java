package com.example.distributedtexteditor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@EnableJpaRepositories({"com.example.distributedtexteditor.repository"})
//@ComponentScan({ "com.example.distributedtexteditor.config", "com.example.distributedtexteditor.controller"})
//@EntityScan({"com.example.distributedtexteditor.model"})

//@EntityScan(basePackageClasses = {Employee.class})
//@Import({com.example.distributedtexteditor.EntityConfig.class}) 


//@ComponentScan({"com.example.distributedtexteditor.config", "com.example.distributedtexteditor.controller", "com.example.distributedtexteditor.model"})
//@EnableJpaRepositories//("com.example.distributedtexteditor.repository")
@SpringBootApplication
public class DistributedTextEditorApplication {

	
	
	public static void main(String[] args) {
		SpringApplication.run(DistributedTextEditorApplication.class, args);
	}
	
}
