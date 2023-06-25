/**
 * 
 */
package com.stylight.urllookup.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stylight.urllookup.service.UrlLookupService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Jeena A V
 *
 */

@RestController
@Slf4j
@RequestMapping("/url/lookup")
public class UrlLookupController {

	private UrlLookupService urlLookupService;

	public UrlLookupController(UrlLookupService urlLookupService) {
		this.urlLookupService = urlLookupService;
	}

	@PostMapping(path = "/params", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, String>> lookupParameterizedUrls(@RequestBody List<String> parameterizedUrls) {
		
		log.info("Executing endpoint | parameterized urls with parameters: {}",  parameterizedUrls);
		
		Map<String, String> paramUrlMap = urlLookupService.lookupParameterizedUrls(parameterizedUrls);
		return new ResponseEntity<Map<String, String>>(paramUrlMap, HttpStatus.OK);
	}

	@PostMapping(path = "/pretty", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, String>> lookupPrettyUrls(@RequestBody List<String> prettyUrls) {

		log.info("Executing endpoint | pretty urls with parameters: {}",  prettyUrls);
		
		Map<String, String> prettyUrlMap = urlLookupService.lookupPrettyUrls(prettyUrls);
		return new ResponseEntity<Map<String, String>>(prettyUrlMap, HttpStatus.OK);
	}
}
