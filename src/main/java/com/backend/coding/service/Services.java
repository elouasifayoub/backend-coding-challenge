package com.backend.coding.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.backend.coding.entity.TrendingRepos;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Service
public class Services {
	
	final static String dateFormat = "yyyy-MM-dd";

	@Autowired
    private RestTemplate restTemplate;
	
	public List<TrendingRepos> getTrendingRepos() {
		LocalDate date = LocalDate.now().minusDays(30); 
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
		ObjectNode resource = restTemplate.getForObject("https://api.github.com/search/repositories?q=created:>" + date.format(formatter) + "&sort=stars&order=desc", ObjectNode.class);
		
		ObjectMapper mapper = new ObjectMapper();
		ArrayNode items = mapper.createArrayNode();
		if (resource.has("items")) {
			items = (ArrayNode) resource.get("items");
		}
		Iterator<JsonNode> itemsIterator = items.elements();
		List<TrendingRepos> trendingReposList = new ArrayList<>();
		while (itemsIterator.hasNext()) {
			String language = itemsIterator.next().get("language").asText();
			String reposName = itemsIterator.next().get("full_name").asText();
			if(!language.equals("null")) {
				int index = contains(trendingReposList, language);
				if (index != -1) {
					trendingReposList.get(index).setNumberLanguage(trendingReposList.get(index).getNumberLanguage() + 1);
					trendingReposList.get(index).getReposName().add(reposName);
		        } else {
		        	TrendingRepos trendingRepos = new TrendingRepos();
		        	trendingRepos.setLanguage(language);
		        	trendingRepos.setNumberLanguage(1);
		        	List<String> reposNameList = new ArrayList<>();
		        	reposNameList.add(reposName);
		        	trendingRepos.setReposName(reposNameList);
		        	trendingReposList.add(trendingRepos);
		        }
			}
		}
		return trendingReposList;
	}
	
	public int contains(List<TrendingRepos> trendingReposList, String language) {
		int index = trendingReposList.stream()
			    .map(t -> t.getLanguage())
			    .collect(Collectors.toList())
			    .indexOf(language);
		return index;
	}

}
