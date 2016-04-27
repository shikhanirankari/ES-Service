
package com.mrll.javelin.search.service.document;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.tika.Tika;
import org.apache.tika.io.IOUtils;
import org.apache.tika.metadata.Metadata;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.Base64;
import org.elasticsearch.common.io.stream.BytesStreamInput;
import org.elasticsearch.common.logging.ESLogger;
import org.elasticsearch.common.logging.Loggers;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import com.mrll.javelin.search.delegate.BlobDownloadServiceDelegate;
import com.mrll.javelin.search.document.constant.SMDSearchProperties;
import com.mrll.javelin.search.exception.RestAPIException;
import com.mrll.javelin.search.modal.Document;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

@Component
public class DocumentService {

	private ESLogger logger = Loggers.getLogger(getClass().getName());

	@Autowired
	Client client;

	public Document push(Document document) throws Exception {

		BlobDownloadServiceDelegate blobDownloadServiceDelegate = new BlobDownloadServiceDelegate();
		ResponseEntity<byte[]> blobDownloadResponse = blobDownloadServiceDelegate.blobDownload("project1",
				"a4f79c98-2a98-4ea8-897f-c4245f59a371_OCR");

		if (logger.isDebugEnabled())
			logger.debug("push({})", document);
		if (document == null)
			return null;

		if (document.getIndex() == null || document.getIndex().isEmpty()) {
			document.setIndex(SMDSearchProperties.INDEX_NAME);
		}
		if (document.getType() == null || document.getType().isEmpty()) {
			document.setType(SMDSearchProperties.INDEX_TYPE_DOC);
		}
		document.setContent(Base64.encodeBytes(blobDownloadResponse.getBody()));

		String parsedContents = IOUtils.toString(blobDownloadResponse.getBody());

		XContentBuilder contentBuilder = jsonBuilder().startObject().prettyPrint();

		contentBuilder.field("content", parsedContents);

		contentBuilder.endObject();

		try {
			IndexResponse response = client.prepareIndex(document.getIndex(), document.getType(), document.getId())
					.setSource(contentBuilder).execute().actionGet();

			document.setId(response.getId());
		} catch (Exception e) {
			logger.warn("Can not index document {}", document.getName());
			throw new RestAPIException("Can not index document : " + document.getName() + ": " + e.getMessage());
		}

		if (logger.isDebugEnabled())
			logger.debug("/push()={}", document);
		return document;
	}

}
