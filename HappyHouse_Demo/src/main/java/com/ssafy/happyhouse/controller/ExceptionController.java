package com.ssafy.happyhouse.controller;

import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ssafy.happyhouse.exception.ErrorResponse;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@RestControllerAdvice
public class ExceptionController {
	
	
	@ExceptionHandler(IllegalArgumentException.class)
    public JSONObject illegalArgumentException1(final IllegalArgumentException ex){
//        return ErrorResponse.JsonErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		return ErrorResponse.JsonErrorResponse(500, ex.getMessage());
    }
	
	@ExceptionHandler(RuntimeException.class)
    public JSONObject runtimeAllException(final RuntimeException ex){
		return ErrorResponse.JsonErrorResponse(500, ex.getMessage());
    }
}
