/**
 * 
 */
package com.stylight.urllookup.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stylight.urllookup.constant.UrlConstant;
import com.stylight.urllookup.consumer.ParamConsumer;
import com.stylight.urllookup.consumer.PrettyConsumer;
import com.stylight.urllookup.service.UrlLookupService;
import com.stylight.urllookup.utility.UrlLookupUtility;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Jeena A V
 *
 */
@Service
@Slf4j
public class UrlLookupServiceImpl implements UrlLookupService {
	
	static BidiMap<String, String> bidiMap;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;

	public UrlLookupServiceImpl() {

		bidiMap = new DualHashBidiMap<>();
		bidiMap.put("/products", "/Fashion/");
		bidiMap.put("/products?gender=female", "/Women/");
		bidiMap.put("/products?tag=5678", "/Boat-Shoes/");
		bidiMap.put("/products?gender=female&tag=123&tag=1234", "/Women/Shoes/");
		bidiMap.put("/products?brand=123", "/Adidas/");
	}

	@Override
	public Map<String, String> lookupParameterizedUrls(List<String> parameterizedUrls) {
		
		log.info("Processing task | parameterized urls with parameters: {}",  parameterizedUrls);
		
		processParamUrls(parameterizedUrls);
		return ParamConsumer.getParamMap();
	}

	@Override
	public Map<String, String> lookupPrettyUrls(List<String> prettyUrls) {
		
		log.info("Processing task | pretty urls with parameters: {}",  prettyUrls);

		processPrettyUrls(prettyUrls);
		return PrettyConsumer.getPrettyMap();
	}

	/**
	 * @param parameterizedUrls
	 */
	private void processParamUrls(List<String> parameterizedUrls) {
		
		UrlLookupUtility.validate(parameterizedUrls);
		rabbitTemplate.convertAndSend(UrlConstant.PARAM_TOPIC, UrlConstant.PARAM_KEY, parameterizedUrls);

	}

	/**
	 * @param prettyUrls
	 */
	private void processPrettyUrls(List<String> prettyUrls) {

		UrlLookupUtility.validate(prettyUrls);
		rabbitTemplate.convertAndSend(UrlConstant.PRETTY_TOPIC, UrlConstant.PRETTY_KEY, prettyUrls);
	}

	public static BidiMap<String, String> getLookupMap() {
		return bidiMap;
	}
}
