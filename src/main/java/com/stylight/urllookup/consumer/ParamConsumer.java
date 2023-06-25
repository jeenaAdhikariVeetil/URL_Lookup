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
public class ParamConsumer {

	static Map<String, String> paramUrlMap;

	public static Map<String, String> getParamMap() {
		return paramUrlMap;
	}

	@RabbitListener(queues = UrlConstant.PARAM_QUEUE)
	public void retriveUrlParams(List<String> parametrizedUrls) {

		Map<String, String> lookUpMap = UrlLookupServiceImpl.getLookupMap();
		paramUrlMap = UrlLookupUtility.lookup(parametrizedUrls, new ConcurrentHashMap<>(50000, 1), lookUpMap);

	}
}
