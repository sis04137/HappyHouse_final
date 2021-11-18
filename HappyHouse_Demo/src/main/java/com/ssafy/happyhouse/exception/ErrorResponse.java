package com.ssafy.happyhouse.exception;

import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;

public class ErrorResponse {
	public static JSONObject JsonErrorResponse(HttpStatus status, String errorMessage) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("errorCode", status);
        jsonObject.put("errorMessage", errorMessage);
        return jsonObject;
    }
	
	public static JSONObject JsonErrorResponse(int status, String errorMessage) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("errorCode", status);
        jsonObject.put("errorMessage", errorMessage);
        return jsonObject;
    }
}
