package com.mrll.javelin.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import com.mrll.javelin.common.security.EnableJavelinCommonSecurity;
import com.mrll.javelin.common.web.EnableJavelinCommonWeb;

/**
 * @description The Application class is basic configuration class for
 *              springboot application to set starting level of application.
 * 
 * @SpringBootApplication is a convenience annotation that adds all of the
 *                        following:
 */
@SpringBootApplication
//@EnableDiscoveryClient
@EnableJavelinCommonWeb
//@EnableJavelinCommonSecurity
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
