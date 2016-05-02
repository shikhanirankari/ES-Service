
package com.mrll.javelin.search.service.document;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import org.apache.commons.lang.StringUtils;
import org.apache.tika.io.IOUtils;
import org.apache.tika.metadata.Metadata;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.Strings;
import org.elasticsearch.common.logging.ESLogger;
import org.elasticsearch.common.logging.Loggers;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.mrll.javelin.search.delegate.BlobDownloadServiceDelegate;
import com.mrll.javelin.search.document.constant.SMDSearchProperties;
import com.mrll.javelin.search.modal.Document;



@Component
public class DocumentService {

	private ESLogger logger = Loggers.getLogger(getClass().getName());

	@Autowired
	Client client;

	@SuppressWarnings("deprecation")
	public void push(Document document) throws Exception {

		BlobDownloadServiceDelegate blobDownloadServiceDelegate = new BlobDownloadServiceDelegate();
		ResponseEntity<byte[]> blobDownloadResponse = blobDownloadServiceDelegate.blobDownload("project1",
				"a4f79c98-2a98-4ea8-897f-c4245f59a371_OCR");

		if (StringUtils.isEmpty(document.getIndex())) {
			document.setIndex(SMDSearchProperties.INDEX_NAME);
		}

		if (StringUtils.isEmpty(document.getType())) {
			document.setType(SMDSearchProperties.INDEX_TYPE_DOC);
		}

		String parsedContents = IOUtils.toString(blobDownloadResponse.getBody());

		XContentBuilder contentBuilder = jsonBuilder().startObject().prettyPrint();

		contentBuilder.field("content", parsedContents);
		contentBuilder.field(SMDSearchProperties.FILENAME, document.getName());

		
		contentBuilder
         .startObject(SMDSearchProperties.META)
         .field(SMDSearchProperties.TITLE, "title")
         .endObject(); 


		contentBuilder.endObject();

	
			client.prepareIndex(document.getIndex(), document.getType(), document.getId()).setSource(contentBuilder)
					.execute().actionGet();


	}

}
