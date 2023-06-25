/**
 * 
 */
package com.stylight.urllookup.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.stylight.urllookup.service.UrlLookupService;

/**
 * @author Jeena A V
 *
 */

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UrlLookupControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	UrlLookupService urlLookupService;

	@Test
	public void lookupParameterizedUrlsTest() throws Exception {
		List<String> paramList = Arrays.asList("/products", "/products?gender=female",
				"/products?gender=female&tag=123&tag=1234", "/products?tag=588");
		Map<String, String> expectedResult = new HashMap<>();
		expectedResult.put("/products?gender=female", "/Women/");
		expectedResult.put("/products?gender=female&tag=123&tag=1234", "/Women/Shoes/");
		expectedResult.put("/products", "/Fashion/");
		expectedResult.put("/products?tag=588", "/Fashion/?tag=588");

		Mockito.when(urlLookupService.lookupParameterizedUrls(paramList)).thenReturn(expectedResult);

		mockMvc.perform(MockMvcRequestBuilders.post("/url/lookup/params").contentType(MediaType.APPLICATION_JSON)
				.content("[\"/products\",\r\n" + "				\"/products?gender=female\",\r\n"
						+ "				\"/products?gender=female&tag=123&tag=1234\",\r\n"
						+ "				\"/products?tag=588\"]"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$./products?gender=female").value("/Women/"))
				.andExpect(MockMvcResultMatchers.jsonPath("$./products?gender=female&tag=123&tag=1234")
						.value("/Women/Shoes/"));
	}

	@Test
	public void lookupPrettyUrlsTest() throws Exception {
		List<String> prettyList = Arrays.asList("/Fashion/", "/Women/", "/Boat-Shoes/", 
				"/Women/Shoes/", "/Adidas/");
		Map<String, String> expectedResult = new HashMap<>();
		expectedResult.put("/Fashion/", "/products");
		expectedResult.put("/Adidas", "/Adidas");
		expectedResult.put("/Women/", "/products?gender=female");
		expectedResult.put("/Women/Shoes/", "/products?gender=female&tag=123&tag=1234");
		expectedResult.put("/Boat-Shoes/", "/products?tag=5678");

		Mockito.when(urlLookupService.lookupPrettyUrls(prettyList)).thenReturn(expectedResult);

		mockMvc.perform(MockMvcRequestBuilders.post("/url/lookup/pretty").contentType(MediaType.APPLICATION_JSON)
				.content("[\"/Fashion/\", \"/Women/\", \"/Boat-Shoes/\", \"/Women/Shoes/\", \"/Adidas/\"]"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$./Fashion/").value("/products"))
				.andExpect(MockMvcResultMatchers.jsonPath("$./Women/Shoes/")
						.value("/products?gender=female&tag=123&tag=1234"));
	}

}
