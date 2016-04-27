package com.mrll.javelin.search.delegate;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * 
 * @author nitish.k.rai BlobDownloadServiceDelegate provides functionality to
 *         download a document from blob.
 */
@Component
public class BlobDownloadServiceDelegate {

	private RestTemplate restTemplate;
	private final String blobDownloadUrl;
	private static final Logger LOG = LoggerFactory.getLogger(BlobDownloadServiceDelegate.class);

	/**
	 * 
	 * @param blobDownloadUrl
	 * @param restTemplate
	 */
	@Autowired
	public BlobDownloadServiceDelegate(@Value("${serviceUrl.blobDownloadUrl}") String blobDownloadUrl,
			final RestTemplate restTemplate) {
		this.blobDownloadUrl = blobDownloadUrl;
		//this.restTemplate = restTemplate;
	}

	
	public BlobDownloadServiceDelegate() {
		this.blobDownloadUrl = "http://azureblobdownload-service.apps.javelinmc.com/javelin/api/blob/download/{containerName}/{docName:.+}";
		this.restTemplate = new RestTemplate();
	}
	
	/**
	 * Method provides functionality for Single blob download.
	 * 
	 * @param conatinerName
	 * @param metadataResponseBean
	 * @param response
	 * @throws IOException
	 */
	public ResponseEntity<byte[]> blobDownload(final String conatinerName, final String docId) throws IOException {

		LOG.info("azureblobdownload-service called with ContainerName.."+conatinerName);
		ResponseEntity<byte[]> blobDownloadResponse = restTemplate.exchange(blobDownloadUrl, HttpMethod.GET, null,
				byte[].class, conatinerName, docId);
		LOG.info("Document downloaded");

		return blobDownloadResponse;

	}

}
