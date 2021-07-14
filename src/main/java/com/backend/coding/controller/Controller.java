package com.backend.coding.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.backend.coding.entity.TrendingRepos;
import com.backend.coding.service.Services;

@RestController
@RequestMapping("/challenge")
public class Controller {
	
	@Autowired
    private Services services;
	
	@GetMapping("/trending")
    public ResponseEntity<List<TrendingRepos>> getTrendingRepos() {

		
		List<TrendingRepos> trendingReposList = services.getTrendingRepos();
		
		
        return new ResponseEntity<>(trendingReposList, HttpStatus.OK);
    }
}
