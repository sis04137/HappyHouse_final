package com.ssafy.happyhouse.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@Controller
public class AuthController {
	
	private String KAKAO_AUTH_URL = "https://kauth.kakao.com";
	private String KAKAO_API_KEY = "44ddf9f04018e79ad5791e558f7dafb5";
	private String REDIRECT_URI = "http://localhost:8080/oauth2/code/kakao";
	//http://localhost:9999/vue/login/oauth2/code/google?state=p8tfJLD7p0iWkhn2fTCPAU7pBAQBdrjgdr91pZKNNQg%3D&code=4%2F0AX4XfWh1lN7u17bsb8YngUXe0b1jpNsjy9upamp3mOhBeaWK1YA_5eNJdsLUs3yCMZHk6g&scope=email+profile+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.profile+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=1&prompt=consent#
	//이거 redirect url인데 왜 못받음..?
	
	//https://kauth.kakao.com/oauth/authorize?client_id=44ddf9f04018e79ad5791e558f7dafb5&redirect_uri=http://localhost:8080/oauth2/code/kakao&response_type=code
//	@RequestMapping(value = "/login/getKakaoAuthUrl")
//	public @ResponseBody String getKakaoAuthUrl(HttpServletRequest request) throws Exception {
//		String reqUrl = KAKAO_AUTH_URL + "/oauth/authorize?client_id=" + KAKAO_API_KEY + "&redirect_uri="+ REDIRECT_URI + "&response_type=code";
//		return reqUrl;
//	}
	
	@PostMapping("/google")
	@ResponseBody
	public String googleOAuthRedirect(@RequestBody String code) {

	    RestTemplate rt = new RestTemplate();

	    HttpHeaders headers = new HttpHeaders();
	    headers.add("Content-Type", "application/x-www-form-urlencoded");

	    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
	    params.add("client_id", "986673395771-5hs9a0e3keolbsa9rvc0ologqrq21vlm.apps.googleusercontent.com");
	    params.add("client_secret", "BaTi_oegV5ts5yMkHUhGv-ZN");
	    params.add("code", code);
	    params.add("grant_type", "authorization_code");
	    params.add("redirect_uri", "http://localhost:8080/login/oauth2/code/google");

	    HttpEntity<MultiValueMap<String, String>> accessTokenRequest = new HttpEntity<>(params, headers);

	    ResponseEntity<String> accessTokenResponse = rt.exchange(
	    	"https://oauth2.googleapis.com/token",
	        HttpMethod.POST,
	        accessTokenRequest,
	        String.class
	    );
	    
	    return "response : " + accessTokenResponse.getBody();
	}
}
