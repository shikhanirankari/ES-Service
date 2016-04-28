
package com.mrll.javelin.search.service;

import static com.mrll.javelin.search.document.constant.SMDSearchProperties.INDEX_NAME;
import static com.mrll.javelin.search.document.constant.SMDSearchProperties.INDEX_TYPE_DOC;
import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;
import static org.elasticsearch.index.query.QueryBuilders.queryString;

import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.logging.ESLogger;
import org.elasticsearch.common.logging.Loggers;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mrll.javelin.search.modal.Hit;
import com.mrll.javelin.search.service.response.SearchResponse;

@Component
public class SearchService {

	@Autowired
	protected Client esClient;

	private ESLogger logger = Loggers.getLogger(getClass().getName());

	public SearchResponse searchIndex(String search, int first, int pageSize) {
		if (logger.isDebugEnabled())
			logger.debug("searchIndex('{}', {}, {})", search, first, pageSize);

		long totalHits = -1;
		long took = -1;

		SearchResponse searchResponse = null;

		QueryBuilder qb;
		if (search == null || search.trim().length() <= 0) {
			qb = matchAllQuery();
		} else {
			qb = queryString(search);
		}

		org.elasticsearch.action.search.SearchResponse searchHits = esClient
				.prepareSearch()
                .setIndices(getSearchableIndexes())
                .setTypes(getSearchableTypes())
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH).setQuery(qb)
				.setFrom(first).setSize(pageSize)
                .addHighlightedField("file.filename")
				.addHighlightedField("content")
                .addHighlightedField("meta.title")
                .addFields("*", "_source")
                .execute().actionGet();

		totalHits = searchHits.getHits().totalHits();
		took = searchHits.getTookInMillis();

		List<Hit> hits = new ArrayList<>();
		for (SearchHit searchHit : searchHits.getHits()) {
			Hit hit = new Hit();

			hit.setIndex(searchHit.getIndex());
			hit.setType(searchHit.getType());
			hit.setId(searchHit.getId());
		    hit.setSource(searchHit.getSourceAsString());

            if (searchHit.getFields() != null) {
                if (searchHit.getFields().get("file.content_type") != null) {
                    hit.setContentType((String) searchHit.getFields().get("file.content_type").getValue());
                }
            }



            if (searchHit.getHighlightFields() != null) {
                for (HighlightField highlightField : searchHit.getHighlightFields().values()) {
                    Text[] fragmentsBuilder = highlightField.getFragments();
                    for (Text fragment : fragmentsBuilder) {
                        hit.getHighlights().add(fragment.string());
                    }
                }
            }

			hits.add(hit);
		}

		searchResponse = new SearchResponse(took, totalHits, hits);

		if (logger.isDebugEnabled())
			logger.debug("/google({}) : {}", search, totalHits);

		return searchResponse;

	}



    public String[] getSearchableIndexes(){
        List<String> indexList = new ArrayList<String>();
        indexList.add(INDEX_NAME);
        String[] indexArr = new String[indexList.size()];
        indexArr = indexList.toArray(indexArr);
        return indexArr;
    }

    public String[] getSearchableTypes(){
        List<String> typeList = new ArrayList<String>();
        typeList.add(INDEX_TYPE_DOC);
        String[] typeArr = new String[typeList.size()];
        typeArr = typeList.toArray(typeArr);
        return typeArr;
    }

}
