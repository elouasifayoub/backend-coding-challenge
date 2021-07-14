package com.backend.coding.entity;

import java.util.List;

public class TrendingRepos {
	
	private String language;
	private int numberLanguage;
	private List<String> reposName;
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public int getNumberLanguage() {
		return numberLanguage;
	}
	public void setNumberLanguage(int numberLanguage) {
		this.numberLanguage = numberLanguage;
	}
	public List<String> getReposName() {
		return reposName;
	}
	public void setReposName(List<String> reposName) {
		this.reposName = reposName;
	}
}
