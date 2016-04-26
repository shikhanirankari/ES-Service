package com.mrll.javelin.search.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @description The Application class is basic configuration class for
 *              springboot application to set starting level of application.
 * 
 * @SpringBootApplication is a convenience annotation that adds all of the
 *                        following:
 */
@SpringBootApplication
public class Application {
	/**
	 * Method provides functionality to launch MicroService .
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}


}
