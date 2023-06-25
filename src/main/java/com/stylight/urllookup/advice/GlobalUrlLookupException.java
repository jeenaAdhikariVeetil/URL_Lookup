/**
 * 
 */
package com.stylight.urllookup.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.stylight.urllookup.constant.UrlConstant;
import com.stylight.urllookup.exception.InvalidInputException;

/**
 * @author Jeena A V
 *
 */

@ControllerAdvice
public class GlobalUrlLookupException extends ResponseEntityExceptionHandler {

	@ExceptionHandler(InvalidInputException.class)
	public ResponseEntity<String> handleException(InvalidInputException ex) {
		return new ResponseEntity<String>(UrlConstant.INVALID_LIST, HttpStatus.NO_CONTENT);
	}

	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<String> handleException(NullPointerException ex) {
		return new ResponseEntity<String>(UrlConstant.NULL, HttpStatus.NO_CONTENT);
	}

}
