/**
 * 
 */
package com.stylight.urllookup.consumer;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.stylight.urllookup.constant.UrlConstant;
import com.stylight.urllookup.service.impl.UrlLookupServiceImpl;
import com.stylight.urllookup.utility.UrlLookupUtility;

/**
 * @author Jeena A V
 *
 */

@Component
public class PrettyConsumer {

	static Map<String, String> prettyUrlMap;

	public static Map<String, String> getPrettyMap() {
		return prettyUrlMap;
	}

	@RabbitListener(queues = UrlConstant.PRETTY_QUEUE)
	public void retriveUrlParams(List<String> prettyUrls) {

		Map<String, String> lookUpMap = UrlLookupServiceImpl.getLookupMap().inverseBidiMap();
		prettyUrlMap = UrlLookupUtility.lookup(prettyUrls, new ConcurrentHashMap<>(50000, 1), lookUpMap);

	}
}
