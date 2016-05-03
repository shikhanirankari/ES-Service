
package com.mrll.javelin.search.service.document;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.lang.StringUtils;
import org.apache.tika.exception.TikaException;
import org.apache.tika.io.IOUtils;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.BodyContentHandler;
import org.elasticsearch.client.Client;
import org.apache.tika.parser.xml.XMLParser;

import org.elasticsearch.common.logging.ESLogger;
import org.elasticsearch.common.logging.Loggers;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import com.mrll.javelin.search.delegate.BlobDownloadServiceDelegate;
import com.mrll.javelin.search.document.constant.SMDSearchProperties;
import com.mrll.javelin.search.exception.RestAPIException;
import com.mrll.javelin.search.modal.Document;

@Component
public class DocumentService {

	private ESLogger logger = Loggers.getLogger(getClass().getName());

	@Autowired
	Client client;
	
	Metadata metadata;

	public void push(Document document) throws IOException, SAXException, RestAPIException, TikaException {

		BlobDownloadServiceDelegate blobDownloadServiceDelegate = new BlobDownloadServiceDelegate();
		ResponseEntity<byte[]> blobDownloadResponse = blobDownloadServiceDelegate.blobDownload("project1",
				document.getId());
		if (blobDownloadResponse.getBody() == null) {
			throw new RestAPIException("document not foind");
		}

		if (StringUtils.isEmpty(document.getIndex())) {
			document.setIndex(SMDSearchProperties.INDEX_NAME);
		}

		if (StringUtils.isEmpty(document.getType())) {
			document.setType(SMDSearchProperties.INDEX_TYPE_DOC);
		}

		XContentBuilder contentBuilder = jsonBuilder().startObject().prettyPrint();

		contentBuilder.field(SMDSearchProperties.CONTENT, getFileRawText(blobDownloadResponse.getBody()));

		contentBuilder.field(SMDSearchProperties.TITLE, "title-"+document.getName());

		contentBuilder.endObject();

		client.prepareIndex(document.getIndex(), document.getType(), document.getId()).setSource(contentBuilder)
				.execute().actionGet();

	}

	private String getFileRawText(byte[] parsedContents) throws IOException, SAXException, TikaException {
		// detecting the file type
		BodyContentHandler handler = new BodyContentHandler();
	    metadata = new Metadata();
		InputStream inputstream = new ByteArrayInputStream(parsedContents);
		ParseContext pcontext = new ParseContext();

		// Xml parser
		XMLParser xmlparser = new XMLParser();

		xmlparser.parse(inputstream, handler, metadata, pcontext);

		return handler.toString();
	     //String[] metadataNames = metadata.names();
	}

}
