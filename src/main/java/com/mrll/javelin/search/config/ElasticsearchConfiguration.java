package com.mrll.javelin.search.config;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * @author nitish.k.rai
 *
 */
@Configuration
public class ElasticsearchConfiguration {
	@Bean
	public Client client() throws UnknownHostException {
		Settings settings = Settings.settingsBuilder().put("cluster.name", "elasticsearch").put("path.data", "D:\\elaticsearchData").build();
		Client client = TransportClient.builder().settings(settings).build()
				.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("13.75.40.132"), 9300));

		
		
		return client;
	}

}
