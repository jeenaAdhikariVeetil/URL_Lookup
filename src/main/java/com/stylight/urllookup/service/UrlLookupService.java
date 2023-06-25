/**
 * 
 */
package com.stylight.urllookup.service;

import java.util.List;
import java.util.Map;

/**
 * @author Jeena A V
 *
 */
public interface UrlLookupService {

	public Map<String, String> lookupParameterizedUrls(List<String> parameterizedUrls);

	public Map<String, String> lookupPrettyUrls(List<String> prettyUrls);

}
