/**
 * 
 */
package com.stylight.urllookup.service.impl;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import com.stylight.urllookup.constant.UrlConstant;
import com.stylight.urllookup.consumer.ParamConsumer;
import com.stylight.urllookup.consumer.PrettyConsumer;

/**
 * @author Jeena A V
 *
 */
@ExtendWith(MockitoExtension.class)
public class UrlLookupServiceImplTest {

	@InjectMocks
	UrlLookupServiceImpl urlLookupServiceImpl;

	@Mock
	private RabbitTemplate rabbitTemplate;

	BidiMap<String, String> bidiMap;

	@BeforeEach
	void setUp() {

		bidiMap = new DualHashBidiMap<>();
		bidiMap.put("/products", "/Fashion/");
		bidiMap.put("/products?gender=female", "/Women/");
		bidiMap.put("/products?tag=5678", "/Boat-Shoes/");
		bidiMap.put("/products?gender=female&tag=123&tag=1234", "/Women/Shoes/");
		bidiMap.put("/products?brand=123", "/Adidas/");
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void lookupParameterizedUrlsTest() {
		List<String> paramList = Arrays.asList("/products", "/products?gender=female",
				"/products?gender=female&tag=123&tag=1234", "/products?tag=588");
		Map<String, String> expectedResult = new HashMap<>();
		expectedResult.put("/products?gender=female", "/Women/");
		expectedResult.put("/products?gender=female&tag=123&tag=1234", "/Women/Shoes/");
		expectedResult.put("/products", "/Fashion/");
		expectedResult.put("/products?tag=588", "/Fashion/?tag=588");

		mockStatic(ParamConsumer.class);
		doNothing().when(rabbitTemplate).convertAndSend(UrlConstant.PARAM_TOPIC, UrlConstant.PARAM_KEY, paramList);

		Mockito.when(ParamConsumer.getParamMap()).thenReturn(expectedResult);
		Map<String, String> resultMap = urlLookupServiceImpl.lookupParameterizedUrls(paramList);
		Assertions.assertEquals(expectedResult, resultMap);
	}

	@Test
	public void lookupPrettyUrlsTest() {

		List<String> prettyList = Arrays.asList("/Fashion/", "/Women/", "/Boat-Shoes/", "/Women/Shoes/", "/Adidas/");
		Map<String, String> expectedResult = new HashMap<>();
		expectedResult.put("/Fashion/", "/products");
		expectedResult.put("/Adidas", "/Adidas");
		expectedResult.put("/Women/", "/products?gender=female");
		expectedResult.put("/Women/Shoes/", "/products?gender=female&tag=123&tag=1234");
		expectedResult.put("/Boat-Shoes/", "/products?tag=5678");

		mockStatic(PrettyConsumer.class);
		doNothing().when(rabbitTemplate).convertAndSend(UrlConstant.PRETTY_TOPIC, UrlConstant.PRETTY_KEY, prettyList);
		Mockito.when(PrettyConsumer.getPrettyMap()).thenReturn(expectedResult);

		Map<String, String> resultMap = urlLookupServiceImpl.lookupPrettyUrls(prettyList);
		Assertions.assertEquals(expectedResult, resultMap);
	}
}
