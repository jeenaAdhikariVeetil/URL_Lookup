/**
 * 
 */
package com.stylight.urllookup.utility;

import java.util.List;
import java.util.Map;

import com.stylight.urllookup.constant.UrlConstant;
import com.stylight.urllookup.exception.InvalidInputException;

/**
 * @author Jeena A V
 *
 */
public class UrlLookupUtility {

	public static String REGEX = "[^?=:\\w\\s]";

	public static Map<String, String> lookup(List<String> urls, Map<String, String> urlMap,
			Map<String, String> lookUpMap) {

		for (String url : urls)

			if (url != null && !url.isEmpty()) {
				if (lookUpMap.containsKey(url)) {
					urlMap.put(url, lookUpMap.get(url));
				} else {
					String closeMatch = "";
					int closeMatchLen = 0;
					for (Map.Entry<String, String> entry : lookUpMap.entrySet()) {
						String closeKey = entry.getKey();
						if (url.startsWith(closeKey) && closeKey.length() > closeMatchLen) {
							closeMatch = entry.getValue();
							closeMatchLen = closeKey.length();
						}
					}
					if (!closeMatch.isEmpty()) {
						String remainingKey = url.substring(closeMatchLen);
						closeMatch = closeMatch + remainingKey;
						urlMap.put(url, closeMatch);
					} else {
						urlMap.put(url, url);
					}

				}

			}

		return urlMap;
	}

	public static void validate(List<String> urlList) {

		if (urlList == null) {
			throw new NullPointerException(UrlConstant.NULL);
		}
		if (urlList.isEmpty()) {
			throw new InvalidInputException(UrlConstant.INVALID_LIST);
		}

	}

}
